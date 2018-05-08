package top.itning.hrms.security;

import com.google.gson.Gson;
import com.greathiit.common.util.HttpClientUtil;
import com.greathiit.common.util.SecureRequest;
import com.greathiit.common.util.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ning
 */
public class CustomUserDetailsServiceImpl implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    @Value("${app.admin.accounts}")
    private String[] adminAccounts;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    private static final String APP_ID = "1";

    private static final String API_URL = "http://www.greathiit.com/api/getUser";

    private static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKJCbQe+96CYcxvvAGJZGIfNSNnRD946ktrjeMbmVZ4dHm41hUwaVM465XjyFzi36DWgOYY8jWapiRO8LuuGsYnKZY4ofviGqRQXTtQnmE6jChBwMa8T2PTKRqQuToQF2SqrnTQyCgTtJXVFqwh0rW4NgG3fI7G63klY/I8Z9T1JAgMBAAECgYEAjZLjyuRm72rU1MSeUzFl4+fL7rEo5T+6Lt5W3UTP41uI16Q/H/3BjQd6fSLN/CEKrO+WAXgH8FFwVxXU4o/tit/r+IkUTMPq5clQb+j4CzWOJeU6J+frAgtkf+yfF7t5Hue7VuZ9WoiD8MFmIsGdZ7tkcKkJEfgexkXpdyhO5mECQQD3rAz+1h+lIBnJ0eUbNE6ADENLSL8VerzB8omv5GJ+reTdYRGq5S5ukhGe0LxAHu0VHHTXl4WCPWARtKgnl0VlAkEAp7clyvNl1PBAwny47Mifj+QmplsRPS8s6Pg7Hd6TDTIVun2Ta0T1swHo/Jve4TRSyFx4HTL/Jqw33QtehIecFQJBAMzNP93G0FoqRkjmQQ6S3UrzWP47BI/Nc6LpXUPOlkfsofESIJrxcsjKDroGH3TiXef0JQZV7He7KuLZQaejZiUCQGXChjfBvsOYknJu4nUotUfFEn5VOvx4pzMjihrxdR/Ih86DavLnAH0AZ7D9khnqeWAAWxC8ZHu+epav00VuUpECQQCUmEXF+Ar78x0i/SMXn+SW3qrxN6RptL3DkUay/9ATxQ9NMmyRg1MYqdr+9JMiTi5DOLDQ2K9ChtQ07TQXHPyL";

    private static final String OTHER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdrbIHE1/gvLUhHtoLJvNp3ZeNocI3wqAujZXaoS4F3pMicEM3XxMhSYuhxYlIRQL+L6sXjnUkwrab/eh5KzEIZVVkLvAIMOzmkVawfHD2JwWFaybYpYACAOhS3KOzWuYR4p45dHV/8hB1GjB+0u6pgmGt3xE1s90Doc2pOgSvOQIDAQAB";

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        String username = token.getName();
        logger.info("当前的用户名是：" + username);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setName(username);
        Set<AuthorityInfo> authorities = new HashSet<>();
        AuthorityInfo authorityInfo = new AuthorityInfo("USER");
        //匹配管理员账户
        boolean matchAdminAccount = Arrays.stream(adminAccounts).anyMatch(adminAccount -> adminAccount.equals(token.getName()));
        logger.info("当前登陆角色->" + (matchAdminAccount ? "ADMIN" : "USER"));
        if (matchAdminAccount) {
            authorityInfo.setAuthority("ADMIN");
        }
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);

        //TODO delete username code
        username = "thinkgem";
        String postJson = "";
        try {
            String sendJson = new Gson().toJson(SecureUtil.encryptTradeInfo(APP_ID, username, PRIVATE_KEY, OTHER_PUBLIC_KEY));
            postJson = HttpClientUtil.postJson(API_URL, sendJson);
            SecureRequest secureRequest = new Gson().fromJson(postJson, SecureRequest.class);
            String receiveJson = SecureUtil.decryptTradeInfo(APP_ID, secureRequest.getCER(), secureRequest.getDATA(), secureRequest.getSIGN(), PRIVATE_KEY, OTHER_PUBLIC_KEY);
            logger.debug("receiveJson->" + receiveJson);
        } catch (Exception e) {
            logger.warn("Exception Message->" + e.getMessage() + "postJson->" + postJson);
        }
        return userInfo;
    }
}
