package top.itning.hrms.service;

import top.itning.hrms.entity.post.PositionCategory;
import top.itning.hrms.entity.post.PositionTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;

import java.util.List;

/**
 * 岗位服务
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

    /**
     * 获取所有岗位类别信息集合
     *
     * @param key 默认:getAllPositionCategoryInfoList  该参数用于缓存的KEY
     * @return 岗位类别信息集合
     */
    List<PositionCategory> getAllPositionCategoryInfoList(String key);

    /**
     * 根据岗位名称ID删除岗位名称信息
     *
     * @param id 岗位名称ID
     */
    void delPositionTitleInfoByID(String id) throws NoSuchIdException;

    /**
     * 根据岗位类别ID删除岗位类别信息
     *
     * @param id 岗位类别ID
     */
    void delPositionCategoryInfoByID(String id) throws NoSuchIdException;
}
