package top.itning.hrms.controller.staff;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.search.SearchStaff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;

/**
 * 员工信息控制器
 *
 * @author Ning
 */
@Controller
@RequestMapping("/staffInfo")
public class StaffInfoController {
    private static final Logger logger = LoggerFactory.getLogger(StaffInfoController.class);

    private final StaffService staffService;

    private final DepartmentService departmentService;

    private final EmploymentService employmentService;

    private final JobService jobService;

    private final PostService postService;

    private final FixedService fixedService;

    @Autowired
    public StaffInfoController(StaffService staffService, DepartmentService departmentService, EmploymentService employmentService, JobService jobService, PostService postService, FixedService fixedService) {
        this.staffService = staffService;
        this.departmentService = departmentService;
        this.employmentService = employmentService;
        this.jobService = jobService;
        this.postService = postService;
        this.fixedService = fixedService;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(
                Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 根据部门ID获取该部门下所有职工
     *
     * @param id 部门ID
     * @return Json格式职工集合
     * @throws JsonException 如果部门ID不存在则抛出该异常
     */
    @GetMapping("/show/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public List<Staff> getStaffsByDepartmentID(@PathVariable("id") String id) throws JsonException {
        logger.debug("getStaffsByDepartmentID::获取到的ID->" + id);
        try {
            return staffService.getStaffInfoListByDepartmentID(id);
        } catch (NoSuchIdException e) {
            throw new JsonException(e.getExceptionMessage(), ServerMessage.NOT_FIND);
        } catch (NullParameterException e) {
            throw new JsonException(e.getExceptionMessage(), ServerMessage.NOT_FIND);
        }
    }

    /**
     * 添加职工信息页面
     *
     * @param model 模型
     * @return addStaff.html
     */
    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addStaffByWeb(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        model.addAttribute("employmentFormList", employmentService.getAllEmploymentFormList("getAllEmploymentFormList"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("jobLevelInfoList", jobService.getAllJobLevelInfoList("getAllJobLevelInfoList"));
        model.addAttribute("positionTitleInfoList", postService.getAllPositionTitleInfoList("getAllPositionTitleInfoList"));
        model.addAttribute("positionCategoryInfoList", postService.getAllPositionCategoryInfoList("getAllPositionCategoryInfoList"));
        model.addAttribute("ethnicInfoList", fixedService.getAllEthnicInfoList("getAllEthnicInfoList"));
        model.addAttribute("politicalStatusInfoList", fixedService.getAllPoliticalStatusInfoList("getAllPoliticalStatusInfoList"));
        return "addStaff";
    }

    /**
     * 添加职工信息
     *
     * @param staff 职工实体
     * @return 重定向到主页
     * @throws DataFormatException    出生日期格式化出现问题则抛出该异常
     * @throws NullParameterException 如果Staff实体必填参数为空则抛出该异常
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addStaff(Staff staff) throws DataFormatException, NullParameterException {
        staff.setId(UUID.randomUUID().toString().replace("-", ""));
        logger.debug("addStaff::要添加的职工信息->" + staff);
        staffService.addOrModifyStaffInfo(staff);
        return "redirect:/index/#/" + staff.getDepartment().getId();
    }

    /**
     * 根据职工ID显示职工详细信息
     *
     * @param model 模型
     * @param id    职工ID
     * @return showStaff.html
     * @throws NoSuchIdException      该职工ID不存在时抛出该异常
     * @throws NullParameterException 该职工ID为空时抛出该异常
     */
    @GetMapping("/showDetails/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String showStaffDetails(Model model, @PathVariable("id") String id) throws NoSuchIdException, NullParameterException {
        logger.debug("showStaffDetails::要显示的职工ID->" + id);
        model.addAttribute("staffInfo", staffService.getStaffInfoByID(id));
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        model.addAttribute("employmentFormList", employmentService.getAllEmploymentFormList("getAllEmploymentFormList"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("jobLevelInfoList", jobService.getAllJobLevelInfoList("getAllJobLevelInfoList"));
        model.addAttribute("positionTitleInfoList", postService.getAllPositionTitleInfoList("getAllPositionTitleInfoList"));
        model.addAttribute("positionCategoryInfoList", postService.getAllPositionCategoryInfoList("getAllPositionCategoryInfoList"));
        model.addAttribute("ethnicInfoList", fixedService.getAllEthnicInfoList("getAllEthnicInfoList"));
        model.addAttribute("politicalStatusInfoList", fixedService.getAllPoliticalStatusInfoList("getAllPoliticalStatusInfoList"));
        return "showStaff";
    }

    /**
     * 修改职工信息
     *
     * @param staff 职工实体
     * @return 重定向到修改后的职工详情页面
     * @throws NullParameterException 如果职工实体必填参数为空则抛出该异常
     * @throws DataFormatException    如果日期格式化出错则抛出该异常
     */
    @PostMapping("/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String modifyStaffInfo(Staff staff) throws NullParameterException, DataFormatException {
        if (StringUtils.isBlank(staff.getId())) {
            logger.warn("modifyStaffInfo::要修改的职工ID为空");
            throw new NullParameterException("职工ID为空");
        }
        logger.debug("modifyStaffInfo::要修改的职工->" + staff);
        return "redirect:/staffInfo/showDetails/" + staffService.addOrModifyStaffInfo(staff).getId();
    }

    /**
     * 根据职工ID删除职工信息
     *
     * @param id 职工ID
     * @return JSON格式服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage delStaffInfoByID(@PathVariable("id") String id) {
        logger.debug("delStaffInfoByID::要删除的职工ID为->" + id);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("成功删除ID为->" + id + "的职工");
        serverMessage.setUrl("/staff/del/" + id);
        try {
            staffService.delStaffInfoByID(staffService.getStaffInfoByID(id));
        } catch (NoSuchIdException | NullParameterException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("删除失败! " + e.getMessage());
        }
        return serverMessage;
    }

    /**
     * 职工搜索页面
     *
     * @param model ,模型
     * @return searchStaff.html
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String searchStaff(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        model.addAttribute("positionTitleInfoList", postService.getAllPositionTitleInfoList("getAllPositionTitleInfoList"));
        model.addAttribute("positionCategoryInfoList", postService.getAllPositionCategoryInfoList("getAllPositionCategoryInfoList"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("jobLevelInfoList", jobService.getAllJobLevelInfoList("getAllJobLevelInfoList"));
        return "searchStaff";
    }

    /**
     * 职工搜索
     *
     * @param searchStaff 职工搜索实体
     * @return 搜索到的职工信息集合
     */
    @GetMapping("/searchStaff")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public List<Staff> searchStaff(SearchStaff searchStaff) {
        logger.debug("searchStaff::搜索条件->" + searchStaff);
        return staffService.searchStaff(searchStaff);
    }

    @GetMapping("/down")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void downStaffInfoByID(String id, HttpServletResponse response) throws IOException, NoSuchIdException {
        logger.info("downStaffInfoByID::要下载的职工ID->" + id);
        String[] idArray = StringUtils.split(id, "-");
        String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("职工信息" + nowTime + ".xlsx").getBytes(), "ISO-8859-1"));
        ServletOutputStream outputStream = response.getOutputStream();
        logger.debug("downStaffInfoByID::outputStream.isReady->" + outputStream.isReady());
        staffService.downStaffInfoByID(outputStream, idArray);
        outputStream.flush();
        outputStream.close();
        logger.debug("downStaffInfoByID::outputStream close");
    }

    /**
     * 通过Excel文件添加职工信息
     *
     * @param file 文件
     * @return 重定向到首页
     */
    @PostMapping("/upExcelFile")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage addStaffByFile(@RequestParam("file") MultipartFile file) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setUrl("/upExcelFile");
        if (file.isEmpty()) {
            logger.warn("addStaffByFile::参数为空");
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg("file 参数为空");
            return serverMessage;
        }
        try {
            ServerMessage s = staffService.addStaffInfoByFile(file);
            if (s.getMsg() != null) {
                serverMessage.setCode(ServerMessage.SERVICE_ERROR);
                serverMessage.setMsg(s.getMsg());
            }else {
                serverMessage.setCode(ServerMessage.SUCCESS_CODE);
                serverMessage.setMsg("服务器消息:文件完全添加!");
            }
        } catch (NullParameterException | IOException e) {
            if (serverMessage.getMsg() != null) {
                serverMessage.setCode(ServerMessage.NOT_FIND);
                serverMessage.setMsg(e.getMessage());
            }
        }
        return serverMessage;
    }
}