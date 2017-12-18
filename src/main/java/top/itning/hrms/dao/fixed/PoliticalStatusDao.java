package top.itning.hrms.dao.fixed;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.fixed.PoliticalStatus;

/**
 * PoliticalStatus DAO
 *
 * @author Ning
 */
public interface PoliticalStatusDao extends JpaRepository<PoliticalStatus, String> {
}
