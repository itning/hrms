package top.itning.hrms.service;

import top.itning.hrms.entity.department.Department;

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
     * @param key 该参数用于缓存的KEY
     * @return 部门信息集合
     */
    List<Department> getAllDepartmentInfo(String key);
}
