package top.itning.hrms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.service.DepartmentService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 部门管理服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> getAllDepartmentInfo() {
        return departmentDao.findAll();
    }
}
