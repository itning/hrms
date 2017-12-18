package top.itning.hrms.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.post.PositionCategory;

/**
 * PositionCategory DAO
 *
 * @author Ning
 */
public interface PositionCategoryDao extends JpaRepository<PositionCategory, String> {
}
