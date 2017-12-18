package top.itning.hrms.service.impl;

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
import java.util.List;

/**
 * 职工信息服务实线类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class StaffServiceImpl implements StaffService {
    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

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
}
