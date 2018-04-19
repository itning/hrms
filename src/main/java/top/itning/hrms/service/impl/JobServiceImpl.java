package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.job.JobLevelDao;
import top.itning.hrms.dao.job.JobTitleDao;
import top.itning.hrms.entity.job.JobLevel;
import top.itning.hrms.entity.job.JobTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.JobService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 社会职称/级别服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class JobServiceImpl implements JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobTitleDao jobTitleDao;

    private final JobLevelDao jobLevelDao;

    @Autowired
    public JobServiceImpl(JobTitleDao jobTitleDao, JobLevelDao jobLevelDao) {
        this.jobTitleDao = jobTitleDao;
        this.jobLevelDao = jobLevelDao;
    }

    @Override
    @Cacheable(cacheNames = "JobTitleInfoList", key = "#key")
    public List<JobTitle> getAllJobTitleInfoList(String key) {
        logger.debug("getAllJobTitleInfoList::开始获取所有社会职称信息集合");
        return jobTitleDao.findAll();
    }

    @Override
    @Cacheable(cacheNames = "JobLevelInfoList", key = "#key")
    public List<JobLevel> getAllJobLevelInfoList(String key) {
        logger.debug("getAllJobLevelInfoList::开始获取所有职称级别信息集合");
        return jobLevelDao.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "JobTitleInfoList", key = "#key")
    public void delJobTitleInfoByID(String id, String key) throws NoSuchIdException {
        if (!jobTitleDao.exists(id)) {
            logger.warn("delJobTitleInfoByID::ID->" + id + "的社会职称信息不存在");
            throw new NoSuchIdException("ID为" + id + "的社会职称信息不存在");
        }
        jobTitleDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "JobLevelInfoList", key = "#key")
    public void delJobLevelByID(String id, String key) throws NoSuchIdException {
        if (!jobLevelDao.exists(id)) {
            logger.warn("delJobLevelByID::ID->" + id + "的职称级别信息不存在");
            throw new NoSuchIdException("ID为" + id + "的职称级别信息不存在");
        }
        jobLevelDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "JobTitleInfoList", key = "#key")
    public void addOrModifyJobTitleInfo(JobTitle jobTitle, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(jobTitle.getId(), jobTitle.getName())) {
            logger.warn("addOrModifyJobTitleInfo::参数为空->" + jobTitle);
            throw new NullParameterException("参数为空");
        }
        jobTitleDao.saveAndFlush(jobTitle);
    }

    @Override
    @CacheEvict(cacheNames = "JobLevelInfoList", key = "#key")
    public void addOrModifyJobLevelInfo(JobLevel jobLevel, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(jobLevel.getId(), jobLevel.getName())) {
            logger.warn("addOrModifyJobLevelInfo::参数为空->" + jobLevel);
            throw new NullParameterException("参数为空");
        }
        jobLevelDao.saveAndFlush(jobLevel);
    }
}
