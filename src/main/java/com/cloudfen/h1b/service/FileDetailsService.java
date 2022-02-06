package com.cloudfen.h1b.service;

import com.cloudfen.h1b.domain.FileDetails;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FileDetails}.
 */
public interface FileDetailsService {
    /**
     * Save a fileDetails.
     *
     * @param fileDetails the entity to save.
     * @return the persisted entity.
     */
    FileDetails save(FileDetails fileDetails);

    /**
     * Partially updates a fileDetails.
     *
     * @param fileDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileDetails> partialUpdate(FileDetails fileDetails);

    /**
     * Get all the fileDetails.
     *
     * @return the list of entities.
     */
    List<FileDetails> findAll();

    /**
     * Get the "id" fileDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileDetails> findOne(Long id);

    /**
     * Delete the "id" fileDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
