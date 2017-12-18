package top.itning.hrms.dao.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.employment.EmploymentForm;

/**
 * EmploymentForm DAO
 *
 * @author Ning
 */
public interface EmploymentFormDao extends JpaRepository<EmploymentForm, String> {
}
