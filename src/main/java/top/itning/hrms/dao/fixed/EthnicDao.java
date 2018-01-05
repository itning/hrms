package top.itning.hrms.dao.fixed;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.fixed.Ethnic;

import java.util.List;

/**
 * Ethnic DAO
 *
 * @author Ning
 */
public interface EthnicDao extends JpaRepository<Ethnic, String> {
    /**
     * 根据name查询所有民族
     *
     * @param name 民族名
     * @return 民族集合
     */
    List<Ethnic> findByName(String name);
}
