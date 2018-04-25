package top.itning.hrms.controller.post;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.post.PositionCategory;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.PostService;

import java.util.List;
import java.util.UUID;

/**
 * 岗位类别控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/postCategory")
public class PostCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(PostCategoryController.class);

    private final PostService postService;

    @Autowired
    public PostCategoryController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 获取岗位类别信息集合
     *
     * @return 岗位类别信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<PositionCategory> getPostCategoryInfoList() {
        logger.debug("getPostCategoryInfoList::获取岗位名称集合");
        return postService.getAllPositionCategoryInfoList("getAllPositionCategoryInfoList");
    }

    /**
     * 根据岗位类别ID删除岗位类别
     *
     * @param id 岗位类别ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delPostCategoryInfoByID(@PathVariable("id") String id) {
        logger.debug("delPostCategoryInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的岗位类别");
        serverMessage.setUrl("/postCategory/del/" + id);
        try {
            postService.delPositionCategoryInfoByID(id, "getAllPositionCategoryInfoList");
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 添加岗位类别
     *
     * @param name 岗位类别名
     * @return JSON服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addPostCategoryInfo(@PathVariable("name") String name) {
        PositionCategory positionCategory = new PositionCategory(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addPostTitleInfo::要添加的岗位实体->" + positionCategory);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/postCategory/add/" + name);
        try {
            postService.addOrModifyCategoryTitleInfo(positionCategory, "getAllPositionCategoryInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改岗位类别
     *
     * @param positionCategory 岗位类别
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage modifyPostCategoryInfo(PositionCategory positionCategory) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/postCategory/modify");
        if (StringUtils.isBlank(positionCategory.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyPostCategoryInfo::要修改的ID为" + positionCategory.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            postService.addOrModifyCategoryTitleInfo(positionCategory, "getAllPositionCategoryInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }
}
