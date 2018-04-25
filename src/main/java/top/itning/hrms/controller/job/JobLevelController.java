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
import top.itning.hrms.entity.job.JobLevel;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.JobService;

import java.util.List;
import java.util.UUID;

/**
 * 职称级别控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/jobLevel")
public class JobLevelController {
    private static final Logger logger = LoggerFactory.getLogger(JobLevelController.class);

    private final JobService jobService;

    @Autowired
    public JobLevelController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 获取职称级别信息集合
     *
     * @return 职称级别信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<JobLevel> getJobLevelInfoList() {
        logger.debug("getJobLevelInfoList::获取职称级别集合");
        return jobService.getAllJobLevelInfoList("getAllJobLevelInfoList");
    }

    /**
     * 根据职称级别ID删除职称级别
     *
     * @param id 职称级别ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delJobLevelInfoByID(@PathVariable("id") String id) {
        logger.debug("delJobTitleInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的职称级别");
        serverMessage.setUrl("/jobLevel/del/" + id);
        try {
            jobService.delJobLevelByID(id, "getAllJobLevelInfoList");
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 添加职称级别
     *
     * @param name 职称级别
     * @return JSON服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addJobLevelInfo(@PathVariable("name") String name) {
        JobLevel jobLevel = new JobLevel(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addJobLevelInfo::要添加的职称级别->" + jobLevel);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/jobLevel/add/" + name);
        try {
            jobService.addOrModifyJobLevelInfo(jobLevel, "getAllJobLevelInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改职称级别
     *
     * @param jobLevel 职称级别
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage modifyJobLevelInfo(JobLevel jobLevel) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/jobLevel/modify");
        if (StringUtils.isBlank(jobLevel.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyJobLevelInfo::要修改的ID为" + jobLevel.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            jobService.addOrModifyJobLevelInfo(jobLevel, "getAllJobLevelInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }

}
