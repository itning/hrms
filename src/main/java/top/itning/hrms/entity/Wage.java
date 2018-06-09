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
    private int total1;
    /**
     * 补款
     */
    private int replenishment;
    /**
     * 扣款
     */
    private int chargeBack;
    /**
     * 小计2
     */
    private int total2;
    /**
     * 教学奖金
     */
    private int teachingBonus;
    /**
     * 带学生津贴
     */
    private int studentAllowance;
    /**
     * 奖金
     */
    private int bonus;
    /**
     * 校拨津贴
     */
    private int schoolGrant;
    /**
     * 教师节津贴
     */
    private int teacherDayAllowance;
    /**
     * 加班补助
     */
    private int overtimeAssistance;
    /**
     * 学生处发放8小时以外补助
     */
    private int time8;
    /**
     * 值班补助
     */
    private int onDutySubsidies;
    /**
     * 其他补助
     */
    private int otherBenefits;
    /**
     * 补助小计
     */
    private int totalBenefits;
    /**
     * 工资合计
     */
    private int total;
    /**
     * 代扣养老保险
     */
    private int pensionInsurance;
    /**
     * 代扣医疗保险
     */
    private int medicalInsurance;
    /**
     * 代扣失业保险
     */
    private int unemploymentInsurance;
    /**
     * 代扣住房公积金
     */
    private int housingFund;
    /**
     * 应发额
     */
    private int shouldMade;
    /**
     * 代扣款
     */
    private int withholding;
    /**
     * 个人所得税
     */
    private int personalIncomeTax;
    /**
     * 税后实发
     */
    private int afterTaxRealHair;
    /**
     * 单位承担养老保险
     */
    private int unitPensionInsurance;
    /**
     * 单位承担医疗保险
     */
    private int unitMedicalInsurance;
    /**
     * 单位承担工伤保险
     */
    private int unitInjuryInsurance;
    /**
     * 单位承担生育保险
     */
    private int unitMaternityInsurance;
    /**
     * 单位承担失业保险
     */
    private int unitUnemploymentInsurance;
    /**
     * 单位承担住房公积金
     */
    private int unitHousingFund;
}
