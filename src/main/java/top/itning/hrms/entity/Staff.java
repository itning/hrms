package top.itning.hrms.entity;

import lombok.Data;
import top.itning.hrms.entity.department.Department;
import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.entity.employment.EmploymentForm;
import top.itning.hrms.entity.fixed.Ethnic;
import top.itning.hrms.entity.fixed.PoliticalStatus;
import top.itning.hrms.entity.job.JobLevel;
import top.itning.hrms.entity.job.JobTitle;
import top.itning.hrms.entity.post.PositionCategory;
import top.itning.hrms.entity.post.PositionTitle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 职工信息实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 姓名
     */
    @NotNull
    private String name;
    /**
     * 银行卡账号
     */
    @NotNull
    private String bankID;
    /**
     * 邮箱
     */
    @NotNull
    private String email;
    /**
     * 性别
     */
    @Transient
    private Boolean sex;
    /**
     * 民族
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ethnic")
    private Ethnic ethnic;
    /**
     * 政治面貌(politicalStatus)
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ps")
    private PoliticalStatus ps;
    /**
     * 出生日期
     */
    @NotNull
    private Date birthday;
    /**
     * 身份证号码
     */
    @NotNull
    private String nid;
    /**
     * 年龄
     */
    @Transient
    private String age;
    /**
     * 户籍地址
     */
    @NotNull
    private String address;
    /**
     * 现住址
     */
    @NotNull
    private String naddress;
    /**
     * 电话号码
     */
    @NotNull
    private String tel;
    /**
     * 部门/
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "department")
    private Department department;
    /**
     * 基层单位
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "grassroot")
    private Grassroot grassroot;
    /**
     * 岗位名称
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "positionTitle")
    private PositionTitle positionTitle;
    /**
     * 岗位类别
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "positionCategory")
    private PositionCategory positionCategory;
    /**
     * 备注
     */
    private String marks;
    /**
     * 来校日期
     */
    @NotNull
    private Date comeDate;
    /**
     * 工龄起始日期
     */
    @NotNull
    private Date startDate;
    /**
     * 社会职称
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "jobTitle")
    private JobTitle jobTitle;
    /**
     * 职称级别
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "jobLevel")
    private JobLevel jobLevel;
    /**
     * 职称授予专业
     */
    private String rta;
    /**
     * 获证时间
     */
    private Date certifiedTime;
    /**
     * 职业资格证（1）
     */
    private String oqc1;
    /**
     * 发证单位
     */
    private String issuingUnit;
    /**
     * 获证时间
     */
    private Date oqc1Time;
    /**
     * 职业资格证（2）
     */
    private String oqc2;
    /**
     * 认定专业
     */
    private String cp;
    /**
     * 岗前培训证(Pre-job training certificate)
     */
    private String ptc;
    /**
     * 岗前培训获得时间
     */
    private Date ptcTime;
    /**
     * 岗位工资
     */
    @NotNull
    private int wage;
    /**
     * 绩效工资
     */
    @NotNull
    private int performancePay;
    /**
     * 职务津贴
     */
    private Integer dutyAllowance;
    /**
     * 岗位超时补助
     */
    private Integer grants;
    /**
     * 硕士津贴
     */
    private Integer mAllowance;
    /**
     * 电话补助
     */
    private Integer pSubsidies;
    /**
     * 用工形式
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employmentForm")
    private EmploymentForm employmentForm;
    /**
     * 用工起始时间
     */
    private Date eStartDate;
    /**
     * 第一次签约劳动合同期限
     */
    private Date laborContract1;
    /**
     * 第一次签约到期日
     */
    private Date laborContract1End;
    /**
     * 第二次签约劳动合同期限
     */
    private Date laborContract2;
    /**
     * 第二次签约到期日
     */
    private Date laborContract2End;
    /**
     * 第三次签约劳动合同期限
     */
    private Date laborContract3;
    /**
     * 第三次签约到期日
     */
    private Date laborContract3End;
    /**
     * 第一学历[1]
     */
    private String ducation1;
    /**
     * 获得学位(Bachelor of Science)
     */
    private String bs;
    /**
     * 性质[1]
     */
    private String nature1;
    /**
     * 毕业时间[1]
     */
    private Date graduationTime1;
    /**
     * 毕业学校[1]
     */
    private String graduatedSchool1;
    /**
     * 专业名称[1]
     */
    private String professionalTitle1;
    /**
     * 最高学历
     */
    private String highestEducation;
    /**
     * 最高学位
     */
    private String hghestDegree;
    /**
     * 性质[2]
     */
    private String nature2;
    /**
     * 毕业时间[2]
     */
    private Date graduationTime2;
    /**
     * 毕业学校[2]
     */
    private String graduatedSchool2;
    /**
     * 最高学历学缘结构
     */
    private String th;
    /**
     * 专业名称[2]
     */
    private String professionalTitle2;
    /**
     * 外语语种
     */
    private String foreignLanguage;
    /**
     * 外语等级
     */
    private String flLevel;
    /**
     * 其他证书
     */
    private String otherCertificates;
    /**
     * 住房公积金有无
     */
    @NotNull
    private boolean hasHousingFund;
}
