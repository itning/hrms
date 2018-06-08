package top.itning.hrms.controller;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.itning.hrms.entity.User;
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

    private final DepartmentService departmentService;

    @Autowired
    public FrameController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 根路径
     *
     * @return 重定向到主页/搜索页
     */
    @GetMapping("/")
    public String root() {
        if (!ClassUtils.isAssignable(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass(), User.class)) {
            return "redirect:/index";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GrantedAuthority authority : user.getAuthorities()) {
            if ("USER".equals(authority.getAuthority())) {
                logger.debug("root::重定向到工资搜索页");
                return "redirect:/staffWage/search";
            }
        }
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String index(Model model) {
        logger.debug("index::开始获取部门信息集合");
        List<Department> departmentList = departmentService.getAllDepartmentInfoList("getAllDepartmentInfoList");
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String manage(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfoList"));
        return "defaultManage";
    }

    /**
     * 基层单位控制器
     *
     * @param model 模型
     * @return grassroot.html
     */
    @GetMapping("/grassroot")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String grassroot(Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartmentInfoList("getAllDepartmentInfoList"));
        return "grassroot";
    }

    /**
     * 登陆控制器
     *
     * @return login.html
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
