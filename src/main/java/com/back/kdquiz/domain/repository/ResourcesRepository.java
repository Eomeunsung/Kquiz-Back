package com.back.kdquiz.domain.repository;

import com.back.kdquiz.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    Optional<Resources> findByResourceName(String resource);
}
