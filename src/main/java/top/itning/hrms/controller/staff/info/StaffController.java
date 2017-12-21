package top.itning.hrms.controller.staff.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 员工信息控制器
 *
 * @author Ning
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private JobService jobService;

    @Autowired
    private PostService postService;

    @Autowired
    private FixedService fixedService;

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
    @ResponseBody
    public List<Staff> getStaffsByDepartmentID(@PathVariable("id") String id) throws JsonException {
        logger.debug("getStaffsByDepartmentID::获取到的ID->" + id);
        try {
            return staffService.getStaffInfoListByDepartmentID(id);
        } catch (NoSuchIdException e) {
            throw new JsonException(e.getExceptionMessage(), ServerMessage.NOT_FIND);
        }
    }

    @GetMapping("/add")
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

    @PostMapping("/add")
    public String addStaff(Staff staff) {
        staffService.addStaffInfo(staff);
        return "redirect:/staff/add";
    }
}
