package top.itning.hrms.service;

import top.itning.hrms.entity.fixed.Ethnic;
import top.itning.hrms.entity.fixed.PoliticalStatus;

import java.util.List;

/**
 * 固定信息服务
 *
 * @author Ning
 */
public interface FixedService {
    /**
     * 获取所有民族信息集合
     *
     * @param key 默认:getAllEthnicInfoList  该参数用于缓存的KEY
     * @return 所有民族信息集合
     */
    List<Ethnic> getAllEthnicInfoList(String key);

    /**
     * 获取所有政治面貌信息集合
     *
     * @param key 默认:getAllPoliticalStatusInfoList  该参数用于缓存的KEY
     * @return 所有政治面貌信息集合
     */
    List<PoliticalStatus> getAllPoliticalStatusInfoList(String key);
}
