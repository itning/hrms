package top.itning.hrms.service;

import top.itning.hrms.entity.job.JobLevel;
import top.itning.hrms.entity.job.JobTitle;

import java.util.List;

/**
 * 社会职称/级别服务
 *
 * @author Ning
 */
public interface JobService {
    /**
     * 获取所有社会职称信息
     *
     * @param key 默认:getAllJobTitleInfoList  该参数用于缓存的KEY
     * @return 社会职称信息集合
     */
    List<JobTitle> getAllJobTitleInfoList(String key);

    /**
     * 获取所有职称级别信息
     *
     * @param key 默认:getAllJobLevelInfoList  该参数用于缓存的KEY
     * @return 职称级别信息集合
     */
    List<JobLevel> getAllJobLevelInfoList(String key);
}
