package top.itning.hrms.controller.staff.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.*;

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

    @GetMapping("/add/{id}")
    public String addStaffByWeb(Model model, @PathVariable("id") String id) {
        logger.debug("addStaffByWeb::获取到要添加的职工所在部门ID->" + id);
        List<Department> departmentList = departmentService.getAllDepartmentInfoList("getAllDepartmentInfo");
        logger.debug("addStaffByWeb::已获取到的部门信息集合数量->" + departmentList.size());
        //检查部门中是否有这个ID
        if (departmentList.stream().noneMatch(department -> department.getId().equals(id))) {
            logger.warn("addStaffByWeb::没有ID为" + id + "的部门");
            throw new NoSuchIdException("没有ID为" + id + "的部门");
        }
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("selectDepartmentID" + id);
        model.addAttribute("employmentFormList", employmentService.getAllEmploymentFormList("getAllEmploymentFormList"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("positionTitleInfoList", postService.getAllPositionTitleInfoList("getAllPositionTitleInfoList"));
        model.addAttribute("EthnicInfoList", fixedService.getAllEthnicInfoList("getAllEthnicInfoList"));
        model.addAttribute("PoliticalStatusInfoList", fixedService.getAllPoliticalStatusInfoList("getAllPoliticalStatusInfoList"));
        return "addStaff";
    }

    @PostMapping("/add")
    public String addStaff(Staff staff) {
        System.out.println("post");
        return "";
    }
}
