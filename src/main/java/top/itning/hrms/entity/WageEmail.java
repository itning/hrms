package top.itning.hrms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 工资信息邮件实体
 *
 * @author wangn
 */
@Data
public class WageEmail implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 岗位工资
     */
    private int wage;
    /**
     * 绩效工资
     */
    private int performancePay;
    /**
     * 职务津贴
     */
    private int dutyAllowance;
    /**
     * 岗位超时补助
     */
    private int grants;
    /**
     * 小计1
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
     * 硕士津贴
     */
    private Integer mAllowance;
    /**
     * 电话补助
     */
    private Integer pSubsidies;
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
     * 学生处发放8小时以外补助
     */
    private int time8;
    /**
     * 加班补助
     */
    private int overtimeAssistance;
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
     * 获取所有属性的Map
     *
     * @return map
     */
    public Map<String, Object> getAllFiledMap() {
        Map<String, Object> map = new HashMap<>(27);
        map.put("name", name);
        map.put("wage", wage);
        map.put("performancePay", performancePay);
        map.put("dutyAllowance", dutyAllowance);
        map.put("grants", grants);
        map.put("total1", total1);
        map.put("replenishment", replenishment);
        map.put("chargeBack", chargeBack);
        map.put("total2", total2);
        map.put("teachingBonus", teachingBonus);
        map.put("mAllowance", mAllowance);
        map.put("pSubsidies", pSubsidies);
        map.put("studentAllowance", studentAllowance);
        map.put("bonus", bonus);
        map.put("schoolGrant", schoolGrant);
        map.put("time8", time8);
        map.put("overtimeAssistance", overtimeAssistance);
        map.put("onDutySubsidies", onDutySubsidies);
        map.put("otherBenefits", otherBenefits);
        map.put("totalBenefits", totalBenefits);
        map.put("total", total);
        map.put("pensionInsurance", pensionInsurance);
        map.put("medicalInsurance", medicalInsurance);
        map.put("unemploymentInsurance", unemploymentInsurance);
        map.put("housingFund", housingFund);
        map.put("shouldMade", schoolGrant);
        map.put("withholding", withholding);
        return map;
    }
}
