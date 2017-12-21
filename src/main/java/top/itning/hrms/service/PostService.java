package top.itning.hrms.service;

import top.itning.hrms.entity.post.PositionTitle;

import java.util.List;

/**
 * 岗位名称服务
 *
 * @author Ning
 */
public interface PostService {
    /**
     * 获取所有岗位名称信息集合
     *
     * @param key 默认:getAllPositionTitleInfoList  该参数用于缓存的KEY
     * @return 岗位名称信息集合
     */
    List<PositionTitle> getAllPositionTitleInfoList(String key);
}
