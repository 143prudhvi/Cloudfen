package com.cloudfen.h1b.service.impl;

import com.cloudfen.h1b.domain.H1B;
import com.cloudfen.h1b.repository.H1BRepository;
import com.cloudfen.h1b.service.H1BService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link H1B}.
 */
@Service
@Transactional
public class H1BServiceImpl implements H1BService {

    private final Logger log = LoggerFactory.getLogger(H1BServiceImpl.class);

    private final H1BRepository h1BRepository;

    public H1BServiceImpl(H1BRepository h1BRepository) {
        this.h1BRepository = h1BRepository;
    }

    @Override
    public H1B save(H1B h1B) {
        log.debug("Request to save H1B : {}", h1B);
        return h1BRepository.save(h1B);
    }

    @Override
    public Optional<H1B> partialUpdate(H1B h1B) {
        log.debug("Request to partially update H1B : {}", h1B);

        return h1BRepository
            .findById(h1B.getId())
            .map(existingH1B -> {
                if (h1B.getUserId() != null) {
                    existingH1B.setUserId(h1B.getUserId());
                }
                if (h1B.getFirstName() != null) {
                    existingH1B.setFirstName(h1B.getFirstName());
                }
                if (h1B.getMiddleName() != null) {
                    existingH1B.setMiddleName(h1B.getMiddleName());
                }
                if (h1B.getLastName() != null) {
                    existingH1B.setLastName(h1B.getLastName());
                }
                if (h1B.getDateOfBirth() != null) {
                    existingH1B.setDateOfBirth(h1B.getDateOfBirth());
                }
                if (h1B.getCountryOfBirth() != null) {
                    existingH1B.setCountryOfBirth(h1B.getCountryOfBirth());
                }
                if (h1B.getCountryOfCitizenShip() != null) {
                    existingH1B.setCountryOfCitizenShip(h1B.getCountryOfCitizenShip());
                }
                if (h1B.getPassportNumber() != null) {
                    existingH1B.setPassportNumber(h1B.getPassportNumber());
                }
                if (h1B.getGender() != null) {
                    existingH1B.setGender(h1B.getGender());
                }
                if (h1B.getCategory() != null) {
                    existingH1B.setCategory(h1B.getCategory());
                }
                if (h1B.getEmail() != null) {
                    existingH1B.setEmail(h1B.getEmail());
                }
                if (h1B.getCurrentAddress() != null) {
                    existingH1B.setCurrentAddress(h1B.getCurrentAddress());
                }
                if (h1B.getPhoneNumber() != null) {
                    existingH1B.setPhoneNumber(h1B.getPhoneNumber());
                }
                if (h1B.getBachelorDegree() != null) {
                    existingH1B.setBachelorDegree(h1B.getBachelorDegree());
                }
                if (h1B.getYear() != null) {
                    existingH1B.setYear(h1B.getYear());
                }
                if (h1B.getCurrentVisaStatus() != null) {
                    existingH1B.setCurrentVisaStatus(h1B.getCurrentVisaStatus());
                }
                if (h1B.getReferedBy() != null) {
                    existingH1B.setReferedBy(h1B.getReferedBy());
                }
                if (h1B.getPassportFrontPage() != null) {
                    existingH1B.setPassportFrontPage(h1B.getPassportFrontPage());
                }
                if (h1B.getPassportBackPage() != null) {
                    existingH1B.setPassportBackPage(h1B.getPassportBackPage());
                }

                return existingH1B;
            })
            .map(h1BRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<H1B> findAll(Pageable pageable) {
        log.debug("Request to get all H1BS");
        return h1BRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<H1B> findOne(Long id) {
        log.debug("Request to get H1B : {}", id);
        return h1BRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete H1B : {}", id);
        h1BRepository.deleteById(id);
    }
}
