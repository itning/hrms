package top.itning.hrms.controller.job;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.job.JobTitle;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.JobService;

import java.util.List;
import java.util.UUID;

/**
 * 社会职称名控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/jobTitle")
public class JobTitleController {
    private static final Logger logger = LoggerFactory.getLogger(JobTitleController.class);

    @Autowired
    private JobService jobService;
    /**
     * 获取岗位类别信息集合
     *
     * @return 岗位类别信息集合
     */
    @GetMapping("")
    public List<JobTitle> getJobTitleInfoList() {
        logger.debug("getJobTitleInfoList::获取岗位名称集合");
        return jobService.getAllJobTitleInfoList("getAllJobTitleInfoList");
    }

    /**
     * 根据岗位类别ID删除岗位类别
     *
     * @param id 岗位类别ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    public ServerMessage delJobTitleInfoByID(@PathVariable("id") String id) {
        logger.debug("delJobTitleInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的岗位类别");
        serverMessage.setUrl("/jobTitle/del/" + id);
        try {
            jobService.delJobTitleInfoByID(id, "getAllJobTitleInfoList");
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
    public ServerMessage addJobTitleInfo(@PathVariable("name") String name) {
        JobTitle jobTitle = new JobTitle(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addJobTitleInfo::要添加的岗位实体->" + jobTitle);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/jobTitle/add/" + name);
        try {
            jobService.addOrModifyJobTitleInfo(jobTitle, "getAllJobTitleInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改岗位类别
     *
     * @param jobTitle 岗位类别
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    public ServerMessage modifyJobTitleInfo(JobTitle jobTitle) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/jobTitle/modify");
        if (StringUtils.isBlank(jobTitle.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyJobTitleInfo::要修改的ID为" + jobTitle.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            jobService.addOrModifyJobTitleInfo(jobTitle, "getAllJobTitleInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }
}