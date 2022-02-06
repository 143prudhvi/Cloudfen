package com.cloudfen.h1b.service;

import com.cloudfen.h1b.domain.BachelorDegree;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BachelorDegree}.
 */
public interface BachelorDegreeService {
    /**
     * Save a bachelorDegree.
     *
     * @param bachelorDegree the entity to save.
     * @return the persisted entity.
     */
    BachelorDegree save(BachelorDegree bachelorDegree);

    /**
     * Partially updates a bachelorDegree.
     *
     * @param bachelorDegree the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BachelorDegree> partialUpdate(BachelorDegree bachelorDegree);

    /**
     * Get all the bachelorDegrees.
     *
     * @return the list of entities.
     */
    List<BachelorDegree> findAll();

    /**
     * Get the "id" bachelorDegree.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BachelorDegree> findOne(Long id);

    /**
     * Delete the "id" bachelorDegree.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
