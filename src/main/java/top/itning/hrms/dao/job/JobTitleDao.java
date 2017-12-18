package top.itning.hrms.dao.job;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.job.JobTitle;

/**
 * JobTitle DAO
 *
 * @author Ning
 */
public interface JobTitleDao extends JpaRepository<JobTitle, String> {
}
