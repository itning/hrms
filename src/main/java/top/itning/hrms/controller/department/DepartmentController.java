package top.itning.hrms.controller.department;

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
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.service.DepartmentService;

import java.util.List;
import java.util.UUID;

/**
 * 部门控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 获取部门信息集合
     *
     * @return 部门信息集合
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<Department> getDepartmentList() {
        logger.debug("getEmploymentFormList::获取用工形式集合");
        return departmentService.getAllDepartmentInfoList("getAllDepartmentInfoList");
    }

    /**
     * 根据部门ID删除部门
     *
     * @param id 部门ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage delDepartmentInfoByID(@PathVariable("id") String id) {
        logger.debug("delDepartmentInfoByID::要删除的ID->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为" + id + "的部门");
        serverMessage.setUrl("/department/del/" + id);
        try {
            departmentService.delDepartmentByID(id, "getAllDepartmentInfoList");
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 添加部门
     *
     * @param name 部门
     * @return JSON服务器消息
     */
    @GetMapping("/add/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage addDepartmentInfo(@PathVariable("name") String name) {
        Department department = new Department(UUID.randomUUID().toString().replace("-", ""), name, null);
        logger.debug("addEmploymentFormInfo::要添加的部门->" + department);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/department/add/" + name);
        try {
            departmentService.addOrModifyDepartmentInfo(department, "getAllDepartmentInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("添加失败->" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 修改部门
     *
     * @param department 部门
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ServerMessage modifyDepartmentInfo(Department department) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/department/modify");
        if (StringUtils.isBlank(department.getId())) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败->ID为空");
            return serverMessage;
        }
        logger.debug("modifyDepartmentInfo::要修改的ID为" + department.getId());
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        try {
            departmentService.addOrModifyDepartmentInfo(department, "getAllDepartmentInfoList");
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.SERVICE_ERROR);
            serverMessage.setMsg("修改失败->" + e.getMessage());
        }
        return serverMessage;
    }
}
