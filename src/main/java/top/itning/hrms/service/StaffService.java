package top.itning.hrms.service;

import top.itning.hrms.entity.Staff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import java.util.List;

/**
 * 职工信息服务
 *
 * @author Ning
 */
public interface StaffService {
    /**
     * 根据部门ID获取该部门的所有职工信息集合
     *
     * @param id 部门ID
     * @return 职工信息集合
     * @throws NullParameterException ID为空时抛出该异常
     * @throws NoSuchIdException      ID不存在时抛出该异常
     */
    List<Staff> getStaffInfoListByDepartmentID(String id) throws NullParameterException, NoSuchIdException;

    /**
     * 添加职工信息
     *
     * @param staff 职工实体类
     */
    void addStaffInfo(Staff staff);
}
