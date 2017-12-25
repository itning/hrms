package top.itning.hrms.service;

import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.search.SearchStaff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import java.util.List;
import java.util.zip.DataFormatException;

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
     * 添加或修改职工信息
     *
     * @param staff 职工实体类
     * @return 添加获取修改的职工信息
     * @throws NumberFormatException  身份证ID不正确则抛出该异常
     * @throws NullParameterException 实体信息必填字段有空则抛出该异常
     * @throws DataFormatException    出生日期格式化出现问题则抛出该异常
     */
    Staff addOrModifyStaffInfo(Staff staff) throws NumberFormatException, NullParameterException, DataFormatException;

    /**
     * 根据职工ID查找该职工实体信息
     *
     * @param id 职工ID
     * @return 职工实体信息
     * @throws NoSuchIdException      如果该职工ID不存在则抛出该异常
     * @throws NullParameterException 如果ID为空则抛出该异常
     */
    Staff getStaffInfoByID(String id) throws NoSuchIdException, NullParameterException;

    /**
     * 根据职工实体删除职工信息
     *
     * @param staff 职工
     */
    void delStaffInfoByID(Staff staff);

    List<Staff> searchStaff(SearchStaff searchStaff);
}
