package top.itning.hrms.dao.department;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.department.Department;

/**
 * Department Dao
 *
 * @author Ning
 */
public interface DepartmentDao extends JpaRepository<Department, String> {
}
