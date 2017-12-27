package top.itning.hrms.entity.search;

import lombok.Data;

import java.io.Serializable;

/**
 * 工资搜索实体
 *
 * @author Ning
 */
@Data
public class SearchWage implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String nid;
    /**
     * 社会职称
     */
    private String[] jobTitle;
    /**
     * 部门ID
     */
    private String[] department;
    /**
     * 基层单位ID
     */
    private String[] grassroot;
    /**
     * 年份
     */
    private String[] year;
    /**
     * 月份
     */
    private String[] month;
}
