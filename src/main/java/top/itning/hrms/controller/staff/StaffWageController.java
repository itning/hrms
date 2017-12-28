package top.itning.hrms.controller.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.DepartmentService;
import top.itning.hrms.service.JobService;
import top.itning.hrms.service.WageService;

import java.util.Map;

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

    /**
     * 搜索页面
     *
     * @param model 模型
     * @return searchWage.html
     */
    @GetMapping("/search")
    public String searchWage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        model.addAttribute("jobTitleInfoList", jobService.getAllJobTitleInfoList("getAllJobTitleInfoList"));
        model.addAttribute("wageYears", wageService.getWageYear());
        return "searchWage";
    }

    /**
     * 搜索职工工资信息
     *
     * @param searchWage 搜索工资实体
     * @return Map集合
     * @throws JsonException JsonException
     */
    @GetMapping("/searchWage")
    @ResponseBody
    public Map<String, Object> searchWage(SearchWage searchWage) throws JsonException {
        logger.debug("searchWage::搜索条件->" + searchWage);
        return wageService.searchWage(searchWage);
    }
}