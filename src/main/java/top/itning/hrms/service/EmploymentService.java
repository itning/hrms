package top.itning.hrms.service;

import top.itning.hrms.entity.employment.EmploymentForm;

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
}
