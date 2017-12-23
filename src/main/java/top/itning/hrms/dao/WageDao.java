package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.Wage;

/**
 * Wage DAO
 *
 * @author Ning
 */
public interface WageDao extends JpaRepository<Wage, String> {
    /**
     * 删除工资信息根据职工
     *
     * @param staff 职工信息
     */
    void deleteByStaff(Staff staff);
}
