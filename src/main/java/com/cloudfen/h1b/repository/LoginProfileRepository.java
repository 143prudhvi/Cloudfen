package com.cloudfen.h1b.repository;

import com.cloudfen.h1b.domain.LoginProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoginProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginProfileRepository extends JpaRepository<LoginProfile, Long>, JpaSpecificationExecutor<LoginProfile> {}
