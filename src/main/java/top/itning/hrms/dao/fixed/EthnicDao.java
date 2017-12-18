package top.itning.hrms.dao.fixed;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.fixed.Ethnic;

/**
 * Ethnic DAO
 *
 * @author Ning
 */
public interface EthnicDao extends JpaRepository<Ethnic, String> {
}
