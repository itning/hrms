package top.itning.hrms.controller.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.itning.hrms.service.DepartmentService;

/**
 * 职工工资控制器
 *
 * @author Ning
 */
@Controller
@RequestMapping("/staffWage")
public class StaffWageController {
    private static final Logger logger = LoggerFactory.getLogger(StaffWageController.class);

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/add")
    public String addWage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        return "addWage";
    }
}
