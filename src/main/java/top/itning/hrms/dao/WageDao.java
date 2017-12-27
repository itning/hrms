package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.Wage;

/**
 * Wage DAO
 *
 * @author Ning
 */
public interface WageDao extends JpaRepository<Wage, String>, JpaSpecificationExecutor<Wage> {
    /**
     * 删除工资信息根据职工
     *
     * @param staff 职工信息
     */
    void deleteByStaff(Staff staff);

    /**
     * 查询数据库中所有年
     *
     * @return 年数组
     */
    @Query("select w.year from Wage w group by w.year")
    String[] findYear();
}
