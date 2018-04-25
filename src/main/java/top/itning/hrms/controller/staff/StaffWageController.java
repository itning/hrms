package top.itning.hrms.controller.staff;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.DepartmentService;
import top.itning.hrms.service.JobService;
import top.itning.hrms.service.WageService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private final DepartmentService departmentService;

    private final JobService jobService;

    private final WageService wageService;

    @Autowired
    public StaffWageController(DepartmentService departmentService, JobService jobService, WageService wageService) {
        this.departmentService = departmentService;
        this.jobService = jobService;
        this.wageService = wageService;
    }

    /**
     * 添加职工工资信息页面
     * @param model 模型
     * @return addWage.html
     */
    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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

    /**
     * 根据ID下载职工工资
     *
     * @param id       ID
     * @param response HttpServletResponse
     * @throws IllegalAccessException IllegalAccessException
     * @throws NoSuchIdException      如果工资ID不存在则抛出该异常
     * @throws IOException            IOException
     * @throws InstantiationException InstantiationException
     */
    @GetMapping("/down")
    public void downStaffWageInfoByID(String id, HttpServletResponse response) throws IllegalAccessException, NoSuchIdException, IOException, InstantiationException {
        logger.debug("downStaffWageInfoByID::要下载的工资ID->" + id);
        String[] idArray = StringUtils.split(id, "-");
        String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("职工工资信息" + nowTime + ".xlsx").getBytes(), "ISO-8859-1"));
        ServletOutputStream outputStream = response.getOutputStream();
        logger.debug("downStaffWageInfoByID::outputStream.isReady->" + outputStream.isReady());
        wageService.downWageInfoByID(outputStream, idArray);
        outputStream.flush();
        outputStream.close();
        logger.debug("downStaffWageInfoByID::outputStream close");
    }

    /**
     * 根据工资ID删除职工信息
     *
     * @param id 工资ID
     * @return JSON服务器消息
     */
    @GetMapping("/del/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ServerMessage delStaffWageInfoByID(@PathVariable("id") String id) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.SUCCESS_CODE);
        serverMessage.setMsg("删除ID为" + id + "的工资信息成功");
        serverMessage.setUrl("/staffWage/del/" + id);
        try {
            wageService.delWageInfoByID(id);
        } catch (NoSuchIdException e) {
            serverMessage.setCode(ServerMessage.NOT_FIND);
            serverMessage.setMsg(e.getMessage());
        }
        return serverMessage;
    }
}