package com.back.kdquiz.config.security;

import com.back.kdquiz.mapper.UrlRoleMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper {
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    @Override
    public Map<String, String> getUrlRoleMappings() {
        return null;
    }
}
