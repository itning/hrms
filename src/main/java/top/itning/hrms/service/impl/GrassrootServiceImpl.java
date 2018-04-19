package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.dao.department.GrassrootDao;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.GrassrootService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 基层单位服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class GrassrootServiceImpl implements GrassrootService {
    private static final Logger logger = LoggerFactory.getLogger(GrassrootServiceImpl.class);

    private final DepartmentDao departmentDao;

    private final GrassrootDao grassrootDao;

    @Autowired
    public GrassrootServiceImpl(DepartmentDao departmentDao, GrassrootDao grassrootDao) {
        this.departmentDao = departmentDao;
        this.grassrootDao = grassrootDao;
    }

    @Override
    @Cacheable(cacheNames = "GrassrootListByDepartment", key = "#id")
    public List<Grassroot> getGrassrootListByDepartment(String id) throws NoSuchIdException {
        if (StringUtils.isBlank(id) || !departmentDao.exists(id)) {
            logger.warn("getGrassrootListByDepartment::ID不存在,ID->" + id);
            throw new NoSuchIdException("ID:" + id + "不存在");
        }
        return departmentDao.getOne(id).getGrassroots();
    }

    @Override
    @CacheEvict(cacheNames = "GrassrootListByDepartment", key = "#did")
    public void modifyGrassroot(Grassroot grassroot, String did) throws NullParameterException {
        if (StringUtils.isAnyBlank(grassroot.getId(), grassroot.getName())) {
            logger.warn("modifyGrassrootByDepartment::参数为空->" + grassroot);
            throw new NullParameterException("参数为空");
        }
        grassrootDao.modifyGrassrootInfo(grassroot.getId(), grassroot.getName());
    }

    @Override
    @CacheEvict(cacheNames = "GrassrootListByDepartment", key = "#id")
    public void addGrassrootByDepartmentID(String id, Grassroot grassroot) throws NoSuchIdException, NullParameterException {
        if (StringUtils.isAnyBlank(id, grassroot.getName(), grassroot.getId())) {
            logger.warn("addGrassrootByDepartmentID::参数为空,ID->" + id + "Grassroot->" + grassroot);
            throw new NullParameterException("参数为空");
        }
        if (!departmentDao.exists(id)) {
            logger.warn("addGrassrootByDepartmentID::ID->" + id + "不存在");
            throw new NoSuchIdException("部门ID:" + id + "不存在");
        }
        Department department = departmentDao.getOne(id);
        List<Grassroot> grassroots = department.getGrassroots();
        grassroots.add(grassroot);
        department.setGrassroots(grassroots);
        departmentDao.saveAndFlush(department);
    }

    @Override
    @CacheEvict(cacheNames = "GrassrootListByDepartment", key = "#did")
    public void delGrassrootByID(String id, String did) throws NoSuchIdException {
        if (StringUtils.isBlank(id) && !grassrootDao.exists(id)) {
            logger.warn("delGrassrootByID::ID不存在或为空->" + id);
            throw new NoSuchIdException("ID:" + id + "不存在或为空");
        }
        grassrootDao.delete(id);
    }
}
