package top.itning.hrms.controller.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.GrassrootService;

import java.util.List;
import java.util.UUID;

/**
 * 基层单位控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/grassroot")
public class GrassrootController {
    private static final Logger logger = LoggerFactory.getLogger(GrassrootController.class);

    private final GrassrootService grassrootService;

    @Autowired
    public GrassrootController(GrassrootService grassrootService) {
        this.grassrootService = grassrootService;
    }

    /**
     * 根据部门ID获取基层单位信息集合
     *
     * @param id 部门ID
     * @return 基层单位信息集合
     * @throws JsonException 如果该部门ID不存在则抛出该异常
     */
    @GetMapping("/show/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public List<Grassroot> getGrassrootListByDepartmentID(@PathVariable("id") String id) throws JsonException {
        logger.debug("getGrassrootListByDepartmentID::部门ID为->" + id);
        try {
            return grassrootService.getGrassrootListByDepartment(id);
        } catch (NoSuchIdException e) {
            throw new JsonException(e.getMessage(), ServerMessage.NOT_FIND);
        }
    }

    /**
     * 修改基层单位信息
     *
     * @param grassroot 基层单位
     * @param did 部门ID,用于删除缓存
     * @return JSON服务器消息
     */
    @GetMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage modifyGrassroot(Grassroot grassroot, String did) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("修改成功");
        serverMessage.setUrl("/grassroot/modify?id=" + grassroot.getId() + "&name=" + grassroot.getName());
        logger.debug("modifyGrassrootByDepartmentID::要修改的基层单位->" + grassroot);
        try {
            grassrootService.modifyGrassroot(grassroot, did);
        } catch (NullParameterException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("修改失败:" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 根据部门ID添加基层单位信息
     *
     * @param did       部门ID
     * @param grassroot 基层单位
     * @return JSON服务器消息
     */
    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage addmodifyGrassrootByDepartmentID(String did, Grassroot grassroot) {
        grassroot.setId(UUID.randomUUID().toString().replace("-", ""));
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("添加成功");
        serverMessage.setUrl("/grassroot/add?did=" + did + "&name=" + grassroot.getName());
        logger.debug("addmodifyGrassrootByDepartmentID::部门ID->" + did + " 基层单位信息" + grassroot);
        try {
            grassrootService.addGrassrootByDepartmentID(did, grassroot);
        } catch (NoSuchIdException | NullParameterException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("添加失败:" + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 根据基层单位ID删除基层单位信息
     *
     * @param id 基层单位ID
     * @param did 部门ID,用于删除缓存
     * @return JSON服务器消息
     */
    @GetMapping("/del")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage delGrassrootByID(String id, String did) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("删除成功");
        serverMessage.setUrl("/grassroot/del/" + id);
        logger.debug("delGrassrootByID::要删除的ID->" + id);
        try {
            grassrootService.delGrassrootByID(id, did);
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败:" + e.getMessage());
        }
        return serverMessage;
    }

}
