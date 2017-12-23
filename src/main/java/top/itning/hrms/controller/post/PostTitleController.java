package top.itning.hrms.controller.post;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.post.PositionTitle;
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

    @Autowired
    private PostService postService;

    /**
     * 获取岗位名称信息集合
     *
     * @return 岗位名称信息集合
     */
    @GetMapping("")
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
    public ServerMessage delPostTitleInfoByID(@PathVariable("id") String id) {
        logger.debug("delPostTitleInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的岗位名称");
        serverMessage.setUrl("/postTitle/del/" + id);
        return serverMessage;
    }

    @GetMapping("/add/{name}")
    public ServerMessage addPostTitleInfo(@PathVariable("name") String name) {
        PositionTitle positionTitle = new PositionTitle(UUID.randomUUID().toString().replace("-", ""), name);

        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/postTitle/add/" + name);
        return serverMessage;
    }

    @GetMapping("/modify")
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
        return serverMessage;
    }
}
