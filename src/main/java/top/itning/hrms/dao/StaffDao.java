package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.entity.job.JobTitle;

import java.util.List;

/**
 * Staff DAO
 *
 * @author Ning
 */
public interface StaffDao extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff> {
    /**
     * 根据部门查找职工
     *
     * @param department 部门
     * @return 职工信息集合
     */
    List<Staff> findByDepartment(Department department);

    /**
     * 根据基层单位查找职工
     *
     * @param grassroot 基层单位
     * @return 职工信息集合
     */
    List<Staff> findByGrassroot(Grassroot grassroot);

    /**
     * 根据职称信息查询职工
     *
     * @param jobTitle 职称信息
     * @return 职工信息集合
     */
    List<Staff> findByJobTitle(JobTitle jobTitle);

    /**
     * 根据身份证号查找员工
     *
     * @param nid 身份证号
     * @return 职工信息集合
     */
    List<Staff> findByNid(String nid);

    /**
     * 根据姓名查询职工信息
     *
     * @param name 职工姓名
     * @return 职工信息集合
     */
    List<Staff> findByName(String name);
}
