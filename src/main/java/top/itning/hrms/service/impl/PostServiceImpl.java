package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.post.PositionCategoryDao;
import top.itning.hrms.dao.post.PositionTitleDao;
import top.itning.hrms.entity.post.PositionCategory;
import top.itning.hrms.entity.post.PositionTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.PostService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 岗位服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PositionTitleDao positionTitleDao;

    private final PositionCategoryDao positionCategoryDao;

    @Autowired
    public PostServiceImpl(PositionTitleDao positionTitleDao, PositionCategoryDao positionCategoryDao) {
        this.positionTitleDao = positionTitleDao;
        this.positionCategoryDao = positionCategoryDao;
    }

    @Override
    @Cacheable(cacheNames = "PositionTitleInfoList", key = "#key")
    public List<PositionTitle> getAllPositionTitleInfoList(String key) {
        logger.debug("getAllPositionTitleInfoList::开始获取所有岗位名称信息集合");
        return positionTitleDao.findAll();
    }

    @Override
    @Cacheable(cacheNames = "PositionCategoryInfoList", key = "#key")
    public List<PositionCategory> getAllPositionCategoryInfoList(String key) {
        logger.debug("getAllPositionCategoryInfoList::开始获取所有岗位类别信息集合");
        return positionCategoryDao.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "PositionTitleInfoList", key = "#key")
    public void delPositionTitleInfoByID(String id, String key) throws NoSuchIdException {
        if (!positionTitleDao.exists(id)) {
            logger.warn("delPositionTitleInfoByID::ID->" + id + "的岗位名称信息不存在");
            throw new NoSuchIdException("ID为" + id + "的岗位名称信息不存在");
        }
        //TODO 检查外键关联 当有关联时给提示,还是直接删除关联
        positionTitleDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "PositionCategoryInfoList", key = "#key")
    public void delPositionCategoryInfoByID(String id, String key) throws NoSuchIdException {
        if (!positionCategoryDao.exists(id)) {
            logger.warn("delPositionCategoryInfoByID::ID->" + id + "的岗位类别信息不存在");
            throw new NoSuchIdException("ID为" + id + "的岗位类别信息不存在");
        }
        positionCategoryDao.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "PositionTitleInfoList", key = "#key")
    public void addOrModifyPositionTitleInfo(PositionTitle positionTitle, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(positionTitle.getId(), positionTitle.getName())) {
            logger.warn("addOrModifyPositionTitleInfo::参数为空->" + positionTitle);
            throw new NullParameterException("参数为空");
        }
        positionTitleDao.saveAndFlush(positionTitle);
    }

    @Override
    @CacheEvict(cacheNames = "PositionCategoryInfoList", key = "#key")
    public void addOrModifyCategoryTitleInfo(PositionCategory positionCategory, String key) throws NullParameterException {
        if (StringUtils.isAnyBlank(positionCategory.getId(), positionCategory.getName())) {
            logger.warn("addOrModifyCategoryTitleInfo::参数为空->" + positionCategory);
            throw new NullParameterException("参数为空");
        }
        positionCategoryDao.saveAndFlush(positionCategory);
    }
}
