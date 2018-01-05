package top.itning.hrms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ning
 */
public class CustomUserDetailsServiceImpl implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        logger.info("当前的用户名是：" + token.getName());
        logger.info(token.getCredentials().toString());
        logger.info(token.getPrincipal().toString());
        logger.info(String.valueOf(token.getAuthorities()));
        /*这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息*/
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getName());
        userInfo.setName(token.getName());
        Set<AuthorityInfo> authorities = new HashSet<>();
        AuthorityInfo authorityInfo = new AuthorityInfo("TEST");
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);
        return userInfo;
    }
}
