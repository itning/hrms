package top.itning.hrms.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.StaffDao;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.StaffService;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
    public void addStaffInfo(Staff staff) {
        staff.setId(UUID.randomUUID().toString().replace("-", ""));
        String nid = staff.getNid();
        //判断是否为数字
        if (!StringUtils.isNumeric(nid)) {
            logger.warn("addStaff::nid不是纯数字->" + nid);
            throw new NumberFormatException("身份证号码不是纯数字,请检查!");
        }
        //判断身份证号长度
        if (nid.length() != ID_NUM_LENGTH) {
            logger.warn("addStaff::nid位数为" + nid.length() + "与" + ID_NUM_LENGTH + "不匹配");
            throw new NumberFormatException("您输入的身份证号码位数为" + nid.length() + "位,不是" + ID_NUM_LENGTH + "位,请检查");
        }
        try {
            //根据身份证号设置出生日期
            staff.setBirthday(new SimpleDateFormat("yyyyMMdd").parse(nid.substring(6, 14)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //根据身份证号设置性别
        staff.setSex(Integer.parseInt(nid.substring(16, 17)) % 2 != 0);
        //根据身份证号设置年龄
        staff.setAge(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(nid.substring(6, 10))));
        //检查必填字段为空
        if (StringUtils.isAnyEmpty(staff.getName(), staff.getBankID(), staff.getEmail(), staff.getNid(), staff.getAge(), staff.getAddress(), staff.getNaddress(), staff.getTel()) && !ObjectUtils.allNotNull(staff.getEthnic(), staff.getPs(), staff.getDepartment(), staff.getGrassroot(), staff.getPositionTitle(), staff.getPositionCategory(), staff.getBirthday())) {
            logger.warn("addStaffInfo::必填参数为空");
            throw new NullParameterException("必填参数为空");
        }
        System.out.println(staff);
    }
}
