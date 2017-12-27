package top.itning.hrms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.WageDao;
import top.itning.hrms.service.WageService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 职工工资服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class WageServiceImpl implements WageService {
    private static final Logger logger = LoggerFactory.getLogger(WageServiceImpl.class);

    @Autowired
    private WageDao wageDao;

    @Override
    public String[] getWageYear() {
        return wageDao.findYear();
    }
}
