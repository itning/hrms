package top.itning.hrms.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.post.PositionCategory;

import java.util.List;

/**
 * PositionCategory DAO
 *
 * @author Ning
 */
public interface PositionCategoryDao extends JpaRepository<PositionCategory, String> {
    /**
     * 根据岗位类别名查找岗位类别
     *
     * @param name 岗位类别名
     * @return 岗位类别集合
     */
    List<PositionCategory> findByName(String name);
}
