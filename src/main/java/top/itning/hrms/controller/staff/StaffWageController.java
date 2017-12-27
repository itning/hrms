package top.itning.hrms.controller.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.hrms.entity.Wage;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.service.DepartmentService;
import top.itning.hrms.service.JobService;
import top.itning.hrms.service.WageService;

import java.util.List;

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

    @Autowired
    private JobService jobService;

    @Autowired
    private WageService wageService;

    @GetMapping("/add")
    public String addWage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        return "addWage";
    }

    @GetMapping("/search")
    public String searchWage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("wageYears", wageService.getWageYear());
        return "searchWage";
    }

    @GetMapping("/searchWage")
    @ResponseBody
    public List<Wage> searchWage(SearchWage searchWage) {
        logger.debug("searchWage::搜索条件->" + searchWage);
        return wageService.searchWage(searchWage);
    }
}
