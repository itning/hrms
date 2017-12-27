package top.itning.hrms.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工资实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "WAGE")
public class Wage implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 年
     */
    @NotNull
    private String year;
    /**
     * 月
     */
    @NotNull
    private String month;
    /**
     * 职工
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff")
    private Staff staff;
    /**
     * 小计1
     * wage+performancePay+dutyAllowance+grants
     * "岗位工资"	"绩效工资"	"职务津贴"	岗位超时补助
     */
    private String total1;
    /**
     * 补款
     */
    private String replenishment;
    /**
     * 扣款
     */
    private String chargeBack;
    /**
     * 小计2
     */
    private String total2;
    /**
     * 教学奖金
     */
    private String teachingBonus;
    /**
     * 带学生津贴
     */
    private String studentAllowance;
    /**
     * 奖金
     */
    private String bonus;
    /**
     * 校拨津贴
     */
    private String schoolGrant;
    /**
     * 教师节津贴
     */
    private String teacherDayAllowance;
    /**
     * 加班补助
     */
    private String overtimeAssistance;
    /**
     * 值班补助
     */
    private String onDutySubsidies;
    /**
     * 其他补助
     */
    private String otherBenefits;
    /**
     * 补助小计
     */
    private String totalBenefits;
    /**
     * 工资合计
     */
    private String total;
    /**
     * 代扣养老保险
     */
    private String pensionInsurance;
    /**
     * 代扣医疗保险
     */
    private String medicalInsurance;
    /**
     * 代扣失业保险
     */
    private String unemploymentInsurance;
    /**
     * 代扣住房公积金
     */
    private String housingFund;
    /**
     * 应发额
     */
    private String shouldMade;
    /**
     * 代扣款
     */
    private String withholding;
    /**
     * 个人所得税
     */
    private String personalIncomeTax;
    /**
     * 税后实发
     */
    private String afterTaxRealHair;
    /**
     * 单位承担养老保险
     */
    private String unitPensionInsurance;
    /**
     * 单位承担医疗保险
     */
    private String unitMedicalInsurance;
    /**
     * 单位承担工伤保险
     */
    private String unitInjuryInsurance;
    /**
     * 单位承担生育保险
     */
    private String unitMaternityInsurance;
    /**
     * 单位承担失业保险
     */
    private String unitUnemploymentInsurance;
    /**
     * 单位承担住房公积金
     */
    private String unitHousingFund;
}
