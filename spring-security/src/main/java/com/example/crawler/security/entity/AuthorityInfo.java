package com.example.crawler.security.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 *  权限信息
 * Created by luoxx on 2017/10/6.
 */
public class AuthorityInfo implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

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
