package top.itning.hrms.dao.fixed;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.fixed.PoliticalStatus;

import java.util.List;

/**
 * PoliticalStatus DAO
 *
 * @author Ning
 */
public interface PoliticalStatusDao extends JpaRepository<PoliticalStatus, String> {
    /**
     * 根据name查询政治面貌信息
     *
     * @param name 政治面貌名
     * @return 政治面貌集合
     */
    List<PoliticalStatus> findByName(String name);
}
