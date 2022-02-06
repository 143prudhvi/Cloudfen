package com.cloudfen.h1b.service;

import com.cloudfen.h1b.domain.H1B;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link H1B}.
 */
public interface H1BService {
    /**
     * Save a h1B.
     *
     * @param h1B the entity to save.
     * @return the persisted entity.
     */
    H1B save(H1B h1B);

    /**
     * Partially updates a h1B.
     *
     * @param h1B the entity to update partially.
     * @return the persisted entity.
     */
    Optional<H1B> partialUpdate(H1B h1B);

    /**
     * Get all the h1BS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<H1B> findAll(Pageable pageable);

    /**
     * Get the "id" h1B.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<H1B> findOne(Long id);

    /**
     * Delete the "id" h1B.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
