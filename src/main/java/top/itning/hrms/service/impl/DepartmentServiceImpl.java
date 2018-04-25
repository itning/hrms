package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
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

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    @Cacheable(cacheNames = "DepartmentList", key = "#key")
    public List<Department> getAllDepartmentInfoList(String key) {
        logger.debug("getAllDepartmentInfo::获取部门信息集合");
        return departmentDao.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "DepartmentList", key = "#key")
    public void delDepartmentByID(String id, String key) throws NoSuchIdException {
        if (!departmentDao.exists(id)) {
            logger.warn("delDepartmentByID::ID->" + id + "的部门不存在");
            throw new NoSuchIdException("ID为" + id + "的部门信息不存在");
        }
        //逻辑:检查基层单位是否存在,存在提示,不存在删除
        List<Grassroot> grassrootList = departmentDao.getOne(id).getGrassroots();
        if (grassrootList != null && grassrootList.size() != 0) {
            throw new NoSuchIdException("请先检查该部门下是否存在基层单位!");
        }
        departmentDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "DepartmentList", key = "#key")
    public void addOrModifyDepartmentInfo(Department department, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(department.getId(), department.getName())) {
            logger.warn("addOrModifyDepartmentInfo::参数为空->" + department);
            throw new NullParameterException("参数为空");
        }
        //如果基层单位不存在且有这个部门(修改操作)
        if (department.getGrassroots() == null && departmentDao.exists(department.getId())) {
            Department wantUpDepartment = departmentDao.findOne(department.getId());
            department.setGrassroots(wantUpDepartment.getGrassroots());
        }
        departmentDao.saveAndFlush(department);
    }
}
