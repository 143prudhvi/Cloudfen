package com.cloudfen.h1b.service.impl;

import com.cloudfen.h1b.domain.BioProfile;
import com.cloudfen.h1b.repository.BioProfileRepository;
import com.cloudfen.h1b.service.BioProfileService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BioProfile}.
 */
@Service
@Transactional
public class BioProfileServiceImpl implements BioProfileService {

    private final Logger log = LoggerFactory.getLogger(BioProfileServiceImpl.class);

    private final BioProfileRepository bioProfileRepository;

    public BioProfileServiceImpl(BioProfileRepository bioProfileRepository) {
        this.bioProfileRepository = bioProfileRepository;
    }

    @Override
    public BioProfile save(BioProfile bioProfile) {
        log.debug("Request to save BioProfile : {}", bioProfile);
        return bioProfileRepository.save(bioProfile);
    }

    @Override
    public Optional<BioProfile> partialUpdate(BioProfile bioProfile) {
        log.debug("Request to partially update BioProfile : {}", bioProfile);

        return bioProfileRepository
            .findById(bioProfile.getId())
            .map(existingBioProfile -> {
                if (bioProfile.getUserName() != null) {
                    existingBioProfile.setUserName(bioProfile.getUserName());
                }
                if (bioProfile.getUserId() != null) {
                    existingBioProfile.setUserId(bioProfile.getUserId());
                }
                if (bioProfile.getMemberId() != null) {
                    existingBioProfile.setMemberId(bioProfile.getMemberId());
                }
                if (bioProfile.getFirstName() != null) {
                    existingBioProfile.setFirstName(bioProfile.getFirstName());
                }
                if (bioProfile.getLastName() != null) {
                    existingBioProfile.setLastName(bioProfile.getLastName());
                }
                if (bioProfile.getDob() != null) {
                    existingBioProfile.setDob(bioProfile.getDob());
                }
                if (bioProfile.getGender() != null) {
                    existingBioProfile.setGender(bioProfile.getGender());
                }
                if (bioProfile.getImageUrl() != null) {
                    existingBioProfile.setImageUrl(bioProfile.getImageUrl());
                }
                if (bioProfile.getTitle() != null) {
                    existingBioProfile.setTitle(bioProfile.getTitle());
                }
                if (bioProfile.getSummary() != null) {
                    existingBioProfile.setSummary(bioProfile.getSummary());
                }

                return existingBioProfile;
            })
            .map(bioProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BioProfile> findAll() {
        log.debug("Request to get all BioProfiles");
        return bioProfileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BioProfile> findOne(Long id) {
        log.debug("Request to get BioProfile : {}", id);
        return bioProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BioProfile : {}", id);
        bioProfileRepository.deleteById(id);
    }
}
