package top.itning.hrms.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * @author Ning
 */
public class AuthorityInfo implements GrantedAuthority {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorityInfo)) {
            return false;
        }
        AuthorityInfo that = (AuthorityInfo) o;
        return Objects.equals(getAuthority(), that.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthority());
    }
}
