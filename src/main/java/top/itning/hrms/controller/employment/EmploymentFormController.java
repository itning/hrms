package top.itning.hrms.controller.employment;

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
import top.itning.hrms.entity.employment.EmploymentForm;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.EmploymentService;

import java.util.List;
import java.util.UUID;

/**
 * 用工形式控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/employmentForm")
public class EmploymentFormController {
    private static final Logger logger = LoggerFactory.getLogger(EmploymentFormController.class);

    private final EmploymentService employmentService;

    @Autowired
    public EmploymentFormController(EmploymentService employmentService) {
        this.employmentService = employmentService;
    }

    /**
     * 获取用工形式信息集合
     *
     * @return 用工形式信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<EmploymentForm> getEmploymentFormList() {
        logger.debug("getEmploymentFormList::获取用工形式集合");
        return employmentService.getAllEmploymentFormList("getAllEmploymentFormList");
    }

    /**
     * 根据用工形式ID删除用工形式
     *
     * @param id 用工形式ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delEmploymentFormInfoByID(@PathVariable("id") String id) {
        logger.debug("delEmploymentFormInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的用工形式");
        serverMessage.setUrl("/employmentForm/del/" + id);
        try {
            employmentService.delEmploymentFormByID(id, "getAllEmploymentFormList");
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 添加用工形式
     *
     * @param name 用工形式
     * @return JSON服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addEmploymentFormInfo(@PathVariable("name") String name) {
        EmploymentForm employmentForm = new EmploymentForm(UUID.randomUUID().toString().replace("-", ""), name);
        logger.debug("addEmploymentFormInfo::要添加的用工形式->" + employmentForm);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/employmentForm/add/" + name);
        try {
            employmentService.addOrModifyEmploymentFormInfo(employmentForm, "getAllEmploymentFormList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改用工形式
     *
     * @param employmentForm 用工形式
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage modifyEmploymentFormInfo(EmploymentForm employmentForm) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/employmentForm/modify");
        if (StringUtils.isBlank(employmentForm.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyEmploymentFormInfo::要修改的ID为" + employmentForm.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            employmentService.addOrModifyEmploymentFormInfo(employmentForm, "getAllEmploymentFormList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }
}
