package top.itning.hrms.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.StaffDao;
import top.itning.hrms.dao.WageDao;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.search.SearchStaff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.StaffService;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * 职工信息服务实线类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class StaffServiceImpl implements StaffService {
    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    /**
     * 身份证号位数
     */
    private static final int ID_NUM_LENGTH = 18;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private WageDao wageDao;

    @Override
    @Cacheable(cacheNames = "StaffInfoList", key = "#id")
    public List<Staff> getStaffInfoListByDepartmentID(String id) throws NullParameterException, NoSuchIdException {
        if (StringUtils.isEmpty(id)) {
            logger.warn("getStaffInfoListByDepartmentID::参数ID为空");
            throw new NullParameterException("参数ID为空");
        } else if (!departmentDao.exists(id)) {
            logger.warn("getStaffInfoListByDepartmentID::ID->" + id + "不存在");
            throw new NoSuchIdException("ID->" + id + "不存在");
        } else {
            logger.info("getStaffInfoListByDepartmentID::查找并返回职工集合");
            return staffDao.findByDepartment(departmentDao.findOne(id));
        }
    }

    @Override
    @CacheEvict(cacheNames = "StaffInfoList", key = "#staff.department.id")
    @CachePut(cacheNames = "StaffInfoByID", key = "#staff.id")
    public Staff addOrModifyStaffInfo(Staff staff) throws NumberFormatException, NullParameterException, DataFormatException {
        String nid = staff.getNid();
        //判断是否为数字
        if (!StringUtils.isNumeric(nid.substring(0, nid.length() - 1))) {
            logger.warn("addOrModifyStaffInfo::nid不是纯数字->" + nid);
            throw new NumberFormatException("身份证号码不是纯数字,请检查!");
        }
        //判断身份证号长度
        if (nid.length() != ID_NUM_LENGTH) {
            logger.warn("addOrModifyStaffInfo::nid位数为" + nid.length() + "与" + ID_NUM_LENGTH + "不匹配");
            throw new NumberFormatException("您输入的身份证号码位数为" + nid.length() + "位,不是" + ID_NUM_LENGTH + "位,请检查");
        }
        try {
            //根据身份证号设置出生日期
            staff.setBirthday(new SimpleDateFormat("yyyyMMdd").parse(nid.substring(6, 14)));
        } catch (ParseException e) {
            logger.warn("addOrModifyStaffInfo::出生日期格式化出错,日期->" + nid.substring(6, 14) + "异常信息->" + e.getMessage());
            throw new DataFormatException("出生日期格式化出错,检查日期是否有误");
        }
        //根据身份证号设置性别
        staff.setSex(Integer.parseInt(nid.substring(16, 17)) % 2 != 0);
        //根据身份证号设置年龄
        staff.setAge(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(nid.substring(6, 10))));
        //检查必填字段为空
        if (StringUtils.isAnyEmpty(staff.getName(), staff.getBankID(), staff.getEmail(), staff.getNid(), staff.getAge(), staff.getAddress(), staff.getNaddress(), staff.getTel()) && !ObjectUtils.allNotNull(staff.getEthnic(), staff.getPs(), staff.getDepartment(), staff.getGrassroot(), staff.getPositionTitle(), staff.getPositionCategory(), staff.getBirthday())) {
            logger.warn("addOrModifyStaffInfo::必填参数为空");
            throw new NullParameterException("必填参数为空");
        }
        logger.debug("addOrModifyStaffInfo::职工信息->" + staff);
        return staffDao.saveAndFlush(staff);
    }

    @Override
    @Cacheable(cacheNames = "StaffInfoByID", key = "#id")
    public Staff getStaffInfoByID(String id) throws NoSuchIdException, NullParameterException {
        logger.debug("getStaffInfoByID::要查找的职工ID为->" + id);
        if (StringUtils.isBlank(id)) {
            logger.warn("getStaffInfoByID::参数ID为空");
            throw new NullParameterException("参数ID为空");
        }
        if (!staffDao.exists(id)) {
            logger.warn("getStaffInfoByID::职工ID:" + id + "不存在");
            throw new NoSuchIdException("职工ID:" + id + "不存在");
        }
        return staffDao.findOne(id);
    }

    @Override
    @CacheEvict(cacheNames = {"StaffInfoList"}, key = "#staff.department.id")
    public void delStaffInfoByID(Staff staff) {
        logger.debug("delStaffInfoByID::删除员工ID" + staff.getId() + "的工资信息");
        wageDao.deleteByStaff(staff);
        logger.debug("delStaffInfoByID::删除员工ID" + staff.getId() + "信息");
        staffDao.delete(staff);
    }

    @Override
    public List<Staff> searchStaff(SearchStaff searchStaff) {
        logger.debug("searchStaff::开始搜索职工");
        logger.info("searchStaff::搜索实体信息->" + searchStaff);
        return staffDao.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNoneBlank(searchStaff.getName())) {
                logger.info("searchStaff::查询条件 Name(模糊查询)->" + searchStaff.getName());
                list.add(cb.like(root.get("name"), "%" + searchStaff.getName() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        });
    }

}
