package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.department.Department;

import java.util.List;

/**
 * Staff DAO
 *
 * @author Ning
 */
public interface StaffDao extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff> {
    /**
     * 根据部门查找职工
     *
     * @param department 部门
     * @return 职工信息集合
     */
    List<Staff> findByDepartment(Department department);
}
