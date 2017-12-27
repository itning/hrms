package top.itning.hrms.service;

import top.itning.hrms.entity.Wage;
import top.itning.hrms.entity.search.SearchWage;

import java.util.List;

/**
 * 职工工资服务
 *
 * @author Ning
 */
public interface WageService {
    /**
     * 获取工资所有年份
     *
     * @return 年份数组
     */
    String[] getWageYear();

    /**
     * 搜索工资信息
     *
     * @param searchWage 搜索工资实体
     * @return 搜索到的工资实体集合
     */
    List<Wage> searchWage(SearchWage searchWage);
}
