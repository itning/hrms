package top.itning.hrms.service;

import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Map;

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
     * @throws JsonException JsonException
     */
    Map<String, Object> searchWage(SearchWage searchWage) throws JsonException;

    /**
     * 根据ID下载职工工资信息
     *
     * @param servletOutputStream ServletOutputStream
     * @param id                  要下载的职工工资ID
     * @throws NoSuchIdException      ID不存在时则抛出该异常
     * @throws IllegalAccessException IllegalAccessException
     * @throws IOException            IOException
     * @throws InstantiationException InstantiationException
     */
    void downWageInfoByID(ServletOutputStream servletOutputStream, String... id) throws NoSuchIdException, IllegalAccessException, IOException, InstantiationException;

    /**
     * 根据工资ID删除职工工资信息
     *
     * @param id 工资ID
     * @throws NoSuchIdException 如果工资ID不存在则抛出该异常
     */
    void delWageInfoByID(String id) throws NoSuchIdException;
}
