package top.itning.hrms.dao.job;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.job.JobTitle;

import java.util.List;

/**
 * JobTitle DAO
 *
 * @author Ning
 */
public interface JobTitleDao extends JpaRepository<JobTitle, String> {
    /**
     * 根据社会职称名查找社会职称
     *
     * @param name 社会职称名
     * @return 社会职称集合
     */
    List<JobTitle> findByName(String name);
}
