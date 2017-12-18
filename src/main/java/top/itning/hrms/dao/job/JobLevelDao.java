package top.itning.hrms.dao.job;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.job.JobLevel;

/**
 * JobLevel DAO
 *
 * @author Ning
 */
public interface JobLevelDao extends JpaRepository<JobLevel, String> {
}
