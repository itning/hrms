package top.itning.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.hrms.entity.User;

/**
 * 用户DAO
 *
 * @author wangn
 */
public interface UserDao extends JpaRepository<User, String> {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return User 实体
     */
    User findByUsername(String username);
}
