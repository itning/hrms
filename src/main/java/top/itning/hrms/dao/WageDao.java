package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.Wage;

/**
 * Wage DAO
 *
 * @author Ning
 */
public interface WageDao extends JpaRepository<Wage, String> {
}
