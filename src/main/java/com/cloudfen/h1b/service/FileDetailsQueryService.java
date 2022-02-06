package com.cloudfen.h1b.service;

import com.cloudfen.h1b.domain.*; // for static metamodels
import com.cloudfen.h1b.domain.FileDetails;
import com.cloudfen.h1b.repository.FileDetailsRepository;
import com.cloudfen.h1b.service.criteria.FileDetailsCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FileDetails} entities in the database.
 * The main input is a {@link FileDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FileDetails} or a {@link Page} of {@link FileDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileDetailsQueryService extends QueryService<FileDetails> {

    private final Logger log = LoggerFactory.getLogger(FileDetailsQueryService.class);

    private final FileDetailsRepository fileDetailsRepository;

    public FileDetailsQueryService(FileDetailsRepository fileDetailsRepository) {
        this.fileDetailsRepository = fileDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link FileDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FileDetails> findByCriteria(FileDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FileDetails> specification = createSpecification(criteria);
        return fileDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FileDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileDetails> findByCriteria(FileDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FileDetails> specification = createSpecification(criteria);
        return fileDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FileDetails> specification = createSpecification(criteria);
        return fileDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link FileDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FileDetails> createSpecification(FileDetailsCriteria criteria) {
        Specification<FileDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FileDetails_.id));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), FileDetails_.userName));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), FileDetails_.type));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), FileDetails_.fileName));
            }
        }
        return specification;
    }
}
