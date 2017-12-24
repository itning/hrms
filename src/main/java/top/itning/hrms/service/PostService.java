package top.itning.hrms.service;

import top.itning.hrms.entity.post.PositionCategory;
import top.itning.hrms.entity.post.PositionTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

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
     * @param id  岗位名称ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delPositionTitleInfoByID(String id, String key) throws NoSuchIdException;

    /**
     * 根据岗位类别ID删除岗位类别信息
     *
     * @param id  岗位类别ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delPositionCategoryInfoByID(String id, String key) throws NoSuchIdException;

    /**
     * 添加或修改岗位名称
     *
     * @param positionTitle 岗位名称
     * @param key           该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyPositionTitleInfo(PositionTitle positionTitle, String key) throws NullParameterException;

    /**
     * 添加或修改岗位类别
     *
     * @param positionCategory 岗位类别
     * @param key              该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyCategoryTitleInfo(PositionCategory positionCategory, String key) throws NullParameterException;
}
