package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.dao.department.GrassrootDao;
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

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private GrassrootDao grassrootDao;

    @Override
    public List<Grassroot> getGrassrootListByDepartment(String id) throws NoSuchIdException {
        if (StringUtils.isBlank(id) || !departmentDao.exists(id)) {
            logger.warn("getGrassrootListByDepartment::ID不存在,ID->" + id);
            throw new NoSuchIdException("ID:" + id + "不存在");
        }
        return departmentDao.getOne(id).getGrassroots();
    }

    @Override
    public void modifyGrassroot(Grassroot grassroot) throws NullParameterException {
        if (StringUtils.isAnyBlank(grassroot.getId(), grassroot.getName())) {
            logger.warn("modifyGrassrootByDepartment::参数为空->" + grassroot);
            throw new NullParameterException("参数为空");
        }
        grassrootDao.modifyGrassrootInfo(grassroot.getId(), grassroot.getName());
    }
}
