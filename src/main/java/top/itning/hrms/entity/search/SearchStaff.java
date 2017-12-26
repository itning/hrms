package top.itning.hrms.entity.search;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职工搜索实体
 *
 * @author Ning
 */
@Data
public class SearchStaff implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 银行卡号
     */
    private String bankID;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private Boolean[] sex;
    /**
     * 身份证号
     */
    private String nid;
    /**
     * 出生日期开始
     */
    private Date startBirthday;
    /**
     * 出生日期结束
     */
    private Date endBirthday;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 部门ID
     */
    private String[] department;
    /**
     * 基层单位ID
     */
    private String[] grassroot;
    /**
     * 岗位名称
     */
    private String[] positionTitle;
    /**
     * 岗位类别
     */
    private String[] positionCategory;
    /**
     * 来校日期开始
     */
    private Date startComeDate;
    /**
     * 来校日期结束
     */
    private Date endComeDate;
    /**
     * 工龄起始日期开始
     */
    private Date startDate;
    /**
     * 工龄起始日期结束
     */
    private Date endDate;
    /**
     * 社会职称
     */
    private String[] jobTitle;
    /**
     * 职称级别
     */
    private String[] jobLevel;
    /**
     * 岗位工资开始
     */
    private Integer startWage;
    /**
     * 岗位工资结束
     */
    private Integer endWage;
    /**
     * 绩效工资开始
     */
    private Integer startPerformancePay;
    /**
     * 绩效工资结束
     */
    private Integer endPerformancePay;
}
