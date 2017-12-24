package top.itning.hrms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.service.DepartmentService;

import java.util.List;

/**
 * 框架控制器
 *
 * @author Ning
 */
@Controller
public class FrameController {
    private static final Logger logger = LoggerFactory.getLogger(FrameController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根路径
     *
     * @return 重定向到主页
     */
    @GetMapping("/")
    public String root() {
        logger.debug("root::重定向到主页");
        return "redirect:/index";
    }

    /**
     * 主页控制器
     *
     * @param model 模型
     * @return index.html
     */
    @GetMapping("/index")
    public String index(Model model) {
        logger.debug("index::开始获取部门信息集合");
        List<Department> departmentList = departmentService.getAllDepartmentInfoList("getAllDepartmentInfo");
        logger.debug("index::获取部门信息集合完成,大小->" + departmentList.size());
        model.addAttribute("departmentList", departmentList);
        return "index";
    }

    /**
     * 管理控制器
     *
     * @param model 模型
     * @return defaultManage.html
     */
    @GetMapping("/manage")
    public String manage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        return "defaultManage";
    }

    /**
     * 基层单位控制器
     *
     * @param model 模型
     * @return grassroot.html
     */
    @GetMapping("/grassroot")
    public String grassroot(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfo"));
        return "grassroot";
    }
}
