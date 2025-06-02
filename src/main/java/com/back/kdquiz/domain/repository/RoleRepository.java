package com.back.kdquiz.domain.repository;

import com.back.kdquiz.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
