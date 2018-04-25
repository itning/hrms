package top.itning.hrms.controller.job;

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

    private final JobService jobService;

    @Autowired
    public JobTitleController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 获取社会职称信息集合
     *
     * @return 社会职称信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<JobTitle> getJobTitleInfoList() {
        logger.debug("getJobTitleInfoList::获取社会职称信息集合");
        return jobService.getAllJobTitleInfoList("getAllJobTitleInfoList");
    }

    /**
     * 根据社会职称ID删除社会职称
     *
     * @param id 社会职称ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delJobTitleInfoByID(@PathVariable("id") String id) {
        logger.debug("delJobTitleInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的社会职称");
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
     * 添加社会职称
     *
     * @param name 社会职称名
     * @return JSON服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addJobTitleInfo(@PathVariable("name") String name) {
        JobTitle jobTitle = new JobTitle(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addJobTitleInfo::要添加的社会职称->" + jobTitle);
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
     * 修改社会职称
     *
     * @param jobTitle 社会职称
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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
