package com.cloudfen.h1b.service;

import com.cloudfen.h1b.domain.*; // for static metamodels
import com.cloudfen.h1b.domain.H1B;
import com.cloudfen.h1b.repository.H1BRepository;
import com.cloudfen.h1b.service.criteria.H1BCriteria;
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
 * Service for executing complex queries for {@link H1B} entities in the database.
 * The main input is a {@link H1BCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link H1B} or a {@link Page} of {@link H1B} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class H1BQueryService extends QueryService<H1B> {

    private final Logger log = LoggerFactory.getLogger(H1BQueryService.class);

    private final H1BRepository h1BRepository;

    public H1BQueryService(H1BRepository h1BRepository) {
        this.h1BRepository = h1BRepository;
    }

    /**
     * Return a {@link List} of {@link H1B} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<H1B> findByCriteria(H1BCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<H1B> specification = createSpecification(criteria);
        return h1BRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link H1B} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<H1B> findByCriteria(H1BCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<H1B> specification = createSpecification(criteria);
        return h1BRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(H1BCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<H1B> specification = createSpecification(criteria);
        return h1BRepository.count(specification);
    }

    /**
     * Function to convert {@link H1BCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<H1B> createSpecification(H1BCriteria criteria) {
        Specification<H1B> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), H1B_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), H1B_.userId));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), H1B_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), H1B_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), H1B_.lastName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), H1B_.dateOfBirth));
            }
            if (criteria.getCountryOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryOfBirth(), H1B_.countryOfBirth));
            }
            if (criteria.getCountryOfCitizenShip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryOfCitizenShip(), H1B_.countryOfCitizenShip));
            }
            if (criteria.getPassportNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportNumber(), H1B_.passportNumber));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), H1B_.gender));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), H1B_.category));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), H1B_.email));
            }
            if (criteria.getCurrentAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentAddress(), H1B_.currentAddress));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), H1B_.phoneNumber));
            }
            if (criteria.getBachelorDegree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBachelorDegree(), H1B_.bachelorDegree));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), H1B_.year));
            }
            if (criteria.getCurrentVisaStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentVisaStatus(), H1B_.currentVisaStatus));
            }
            if (criteria.getReferedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferedBy(), H1B_.referedBy));
            }
        }
        return specification;
    }
}
