package com.cloudfen.h1b.repository;

import com.cloudfen.h1b.domain.BachelorDegree;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BachelorDegree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BachelorDegreeRepository extends JpaRepository<BachelorDegree, Long> {}
