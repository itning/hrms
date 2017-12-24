package top.itning.hrms.service;

import top.itning.hrms.entity.department.Grassroot;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import java.util.List;

/**
 * 基层单位服务
 *
 * @author Ning
 */
public interface GrassrootService {
    /**
     * 根据部门ID获取基层单位集合
     *
     * @param id 部门ID
     * @return 基层单位集合
     */
    List<Grassroot> getGrassrootListByDepartment(String id) throws NoSuchIdException;

    /**
     * 修改基层单位信息
     *
     * @param grassroot 基层单位
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void modifyGrassroot(Grassroot grassroot) throws NullParameterException;

    /**
     * 根据部门ID添加基层单位信息
     *
     * @param id        部门ID
     * @param grassroot 基层单位
     * @throws NoSuchIdException      部门ID不存在则抛出该异常
     * @throws NullParameterException 参数为空则抛出该异常
     */
    void addGrassrootByDepartmentID(String id, Grassroot grassroot) throws NoSuchIdException, NullParameterException;
}
