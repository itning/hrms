package top.itning.hrms.controller.staff.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.StaffService;

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
            throw new JsonException(e.getExceptionMessage());
        }
    }
}
