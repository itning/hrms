package top.itning.hrms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.post.PositionTitleDao;
import top.itning.hrms.entity.post.PositionTitle;
import top.itning.hrms.service.PostService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 岗位名称服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PositionTitleDao positionTitleDao;

    @Override
    @Cacheable(cacheNames = "PositionTitleInfoList", key = "#key")
    public List<PositionTitle> getAllPositionTitleInfoList(String key) {
        logger.debug("getAllPositionTitleInfoList::开始获取所有岗位名称信息集合");
        return positionTitleDao.findAll();
    }
}
