package top.itning.hrms.dao.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.itning.hrms.entity.department.Grassroot;

import java.util.List;

/**
 * Grassroot DAO
 *
 * @author Ning
 */
public interface GrassrootDao extends JpaRepository<Grassroot, String> {
    /**
     * 根据基层单位ID修改基层单位名称
     *
     * @param id   基层单位ID
     * @param name 基层单位名称
     */
    @Modifying
    @Query("update Grassroot g set g.name=?2 where g.id=?1")
    void modifyGrassrootInfo(String id, String name);

    /**
     * 根据基层单位名查找基层单位
     *
     * @param name 基层单位名
     * @return 基层单位集合
     */
    List<Grassroot> findByName(String name);
}
