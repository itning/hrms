package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.employee.EmploymentFormDao;
import top.itning.hrms.entity.employment.EmploymentForm;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.EmploymentService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 用工形式服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class EmploymentServiceImpl implements EmploymentService {
    private static final Logger logger = LoggerFactory.getLogger(EmploymentServiceImpl.class);

    private final EmploymentFormDao employmentFormDao;

    @Autowired
    public EmploymentServiceImpl(EmploymentFormDao employmentFormDao) {
        this.employmentFormDao = employmentFormDao;
    }

    @Override
    @Cacheable(cacheNames = "EmploymentList", key = "#key")
    public List<EmploymentForm> getAllEmploymentFormList(String key) {
        logger.debug("getAllEmploymentFormList::获取所有用工形式信息");
        return employmentFormDao.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "EmploymentList", key = "#key")
    public void delEmploymentFormByID(String id, String key) throws NoSuchIdException {
        if (!employmentFormDao.exists(id)) {
            logger.warn("delEmploymentFormByID::ID->" + id + "的用工形式信息不存在");
            throw new NoSuchIdException("ID为" + id + "的用工形式信息不存在");
        }
        employmentFormDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "EmploymentList", key = "#key")
    public void addOrModifyEmploymentFormInfo(EmploymentForm employmentForm, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(employmentForm.getId(), employmentForm.getName())) {
            logger.warn("addOrModifyEmploymentFormInfo::参数为空->" + employmentForm);
            throw new NullParameterException("参数为空");
        }
        employmentFormDao.saveAndFlush(employmentForm);
    }
}
