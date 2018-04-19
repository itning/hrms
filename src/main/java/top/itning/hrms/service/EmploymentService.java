package top.itning.hrms.service;

import top.itning.hrms.entity.employment.EmploymentForm;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import java.util.List;

/**
 * 用工形式服务
 *
 * @author Ning
 */
public interface EmploymentService {
    /**
     * 获取所有用工形式
     *
     * @param key 默认:getAllEmploymentFormList  该参数用于缓存的KEY
     * @return 所有用工形式集合
     */
    List<EmploymentForm> getAllEmploymentFormList(String key);

    /**
     * 根据用工形式ID删除用工形式信息
     *
     * @param id  用工形式ID
     * @param key 该参数用于删除缓存名为key的缓存
     * @throws NoSuchIdException ID不存在则抛出该异常
     */
    void delEmploymentFormByID(String id, String key) throws NoSuchIdException;

    /**
     * 添加或修改用工形式
     *
     * @param employmentForm 用工形式
     * @param key            该参数用于删除缓存名为key的缓存
     * @throws NullParameterException 如果参数为空则抛出该异常
     */
    void addOrModifyEmploymentFormInfo(EmploymentForm employmentForm, String key) throws NullParameterException;
}
