package top.itning.hrms.dao.job;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.job.JobLevel;

import java.util.List;

/**
 * JobLevel DAO
 *
 * @author Ning
 */
public interface JobLevelDao extends JpaRepository<JobLevel, String> {
    /**
     * 根据职称级别名查找职称级别
     *
     * @param name 职称级别名
     * @return 职称级别集合
     */
    List<JobLevel> findByName(String name);
}
