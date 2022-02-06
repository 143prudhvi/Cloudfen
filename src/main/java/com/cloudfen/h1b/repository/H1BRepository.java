package com.cloudfen.h1b.repository;

import com.cloudfen.h1b.domain.H1B;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the H1B entity.
 */
@SuppressWarnings("unused")
@Repository
public interface H1BRepository extends JpaRepository<H1B, Long>, JpaSpecificationExecutor<H1B> {}
