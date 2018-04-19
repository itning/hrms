package top.itning.hrms.service;

import top.itning.hrms.entity.department.Department;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import java.util.List;

/**
 * 部门管理服务
 *
 * @author Ning
 */
public interface DepartmentService {
    /**
     * 获取所有部门信息
     *
     * @param key 默认:getAllDepartmentInfo  该参数用于缓存的KEY
     * @return 部门信息集合
     */
    List<Department> getAllDepartmentInfoList(String key);

    /**
     * 根据部门ID删除部门信息
     *
     * @param id  部门ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delDepartmentByID(String id, String key) throws NoSuchIdException;

    /**
     * 添加或修改部门
     *
     * @param department 部门
     * @param key        该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyDepartmentInfo(Department department, String key) throws NullParameterException;
}
