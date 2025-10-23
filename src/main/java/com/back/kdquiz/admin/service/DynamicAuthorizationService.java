package com.back.kdquiz.admin.service;

import com.back.kdquiz.mapper.UrlRoleMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class DynamicAuthorizationService {
    private final UrlRoleMapper delegate;

    public DynamicAuthorizationService(UrlRoleMapper delegate) {
        this.delegate = delegate;
    }
    @Transactional(readOnly = true)
    public Map<String, String> getUrlRoleMappings(){return delegate.getUrlRoleMappings(); }
}
