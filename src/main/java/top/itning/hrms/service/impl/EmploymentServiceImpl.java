package top.itning.hrms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.employee.EmploymentFormDao;
import top.itning.hrms.entity.employment.EmploymentForm;
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

    @Autowired
    private EmploymentFormDao employmentFormDao;

    @Override
    @Cacheable(cacheNames = "EmploymentList", key = "#key")
    public List<EmploymentForm> getAllEmploymentFormList(String key) {
        logger.debug("getAllEmploymentFormList::获取所有用工形式信息");
        return employmentFormDao.findAll();
    }
}
