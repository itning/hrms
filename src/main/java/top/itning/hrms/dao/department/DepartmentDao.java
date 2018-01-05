package top.itning.hrms.dao.department;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.department.Department;

import java.util.List;

/**
 * Department Dao
 *
 * @author Ning
 */
public interface DepartmentDao extends JpaRepository<Department, String> {
    /**
     * 根据部门名查找部门
     *
     * @param name 部门名
     * @return 部门集合
     */
    List<Department> findByName(String name);
}
