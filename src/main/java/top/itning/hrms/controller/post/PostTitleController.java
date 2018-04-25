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
import top.itning.hrms.entity.post.PositionTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.PostService;

import java.util.List;
import java.util.UUID;

/**
 * 岗位名称控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/postTitle")
public class PostTitleController {
    private static final Logger logger = LoggerFactory.getLogger(PostTitleController.class);

    private final PostService postService;

    @Autowired
    public PostTitleController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 获取岗位名称信息集合
     *
     * @return 岗位名称信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<PositionTitle> getPostTitleInfoList() {
        logger.debug("getPostTitleInfoList::获取岗位名称集合");
        return postService.getAllPositionTitleInfoList("getAllPositionTitleInfoList");
    }

    /**
     * 根据岗位名称ID删除岗位名称
     *
     * @param id 岗位名称ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delPostTitleInfoByID(@PathVariable("id") String id) {
        logger.debug("delPostTitleInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的岗位名称");
        serverMessage.setUrl("/postTitle/del/" + id);
        try {
            postService.delPositionTitleInfoByID(id, "getAllPositionTitleInfoList");
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 添加岗位名称信息
     *
     * @param name 岗位名称
     * @return JSON格式服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addPostTitleInfo(@PathVariable("name") String name) {
        PositionTitle positionTitle = new PositionTitle(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addPostTitleInfo::要添加的岗位实体->" + positionTitle);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/postTitle/add/" + name);
        try {
            postService.addOrModifyPositionTitleInfo(positionTitle, "getAllPositionTitleInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改岗位名称
     *
     * @param positionTitle 岗位名称
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage modifyPostTitleInfo(PositionTitle positionTitle) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/postTitle/modify");
        if (StringUtils.isBlank(positionTitle.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyPostTitleInfo::要修改的ID为" + positionTitle.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            postService.addOrModifyPositionTitleInfo(positionTitle, "getAllPositionTitleInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }
}
