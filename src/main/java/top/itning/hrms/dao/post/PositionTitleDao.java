package top.itning.hrms.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.post.PositionTitle;

/**
 * PositionTitle DAO
 *
 * @author Ning
 */
public interface PositionTitleDao extends JpaRepository<PositionTitle, String> {
}
