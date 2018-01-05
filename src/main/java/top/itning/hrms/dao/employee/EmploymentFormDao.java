package top.itning.hrms.dao.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.employment.EmploymentForm;

import java.util.List;

/**
 * EmploymentForm DAO
 *
 * @author Ning
 */
public interface EmploymentFormDao extends JpaRepository<EmploymentForm, String> {
    /**
     * 根据用工形式名查找用工形式
     *
     * @param name 用工形式名
     * @return 用工形式集合
     */
    List<EmploymentForm> findByName(String name);
}
