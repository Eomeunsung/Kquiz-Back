package com.back.kdquiz.mapper;

import com.back.kdquiz.domain.entity.Resources;
import com.back.kdquiz.domain.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PersistenUrlRoleMapper implements UrlRoleMapper{
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourcesRepository resourcesRepository;


    @Override
    public Map<String, String> getUrlRoleMappings() {
        urlRoleMappings.clear();
        List<Resources> resourcesList = resourcesRepository.findAll();

        resourcesList.forEach(re->{
            re.getRoleSet().forEach(role->{
                urlRoleMappings.put(re.getResourceName(), role.getRoleName());
            });
        });
        return urlRoleMappings;
    }
}
