package top.itning.hrms.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Ning
 */
public class AuthorityInfo  implements GrantedAuthority {
    /**
     * 权限CODE
     */
    private String authority;

    public AuthorityInfo(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
