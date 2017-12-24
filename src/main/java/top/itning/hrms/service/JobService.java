package top.itning.hrms.service;

import top.itning.hrms.entity.job.JobLevel;
import top.itning.hrms.entity.job.JobTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

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

    /**
     * 根据社会职称ID删除社会职称信息
     *
     * @param id  社会职称ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delJobTitleInfoByID(String id, String key) throws NoSuchIdException;

    /**
     * 根据职称级别ID删除职称级别信息
     *
     * @param id  职称级别ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delJobLevelByID(String id, String key) throws NoSuchIdException;

    /**
     * 添加或修改社会职称
     *
     * @param jobTitle 社会职称
     * @param key      该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyJobTitleInfo(JobTitle jobTitle, String key) throws NullParameterException;

    /**
     * 添加或修改职称级别
     *
     * @param jobLevel 职称级别
     * @param key      该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyJobLevelInfo(JobLevel jobLevel, String key) throws NullParameterException;
}
