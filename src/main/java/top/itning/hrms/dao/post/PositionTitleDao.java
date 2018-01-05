package top.itning.hrms.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.post.PositionTitle;

import java.util.List;

/**
 * PositionTitle DAO
 *
 * @author Ning
 */
public interface PositionTitleDao extends JpaRepository<PositionTitle, String> {
    /**
     * 根据岗位名称名查找岗位名称信息
     *
     * @param name 岗位名称名
     * @return 岗位名称集合
     */
    List<PositionTitle> findByName(String name);
}
