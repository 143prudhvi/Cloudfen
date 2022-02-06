package com.cloudfen.h1b.service.impl;

import com.cloudfen.h1b.domain.BachelorDegree;
import com.cloudfen.h1b.repository.BachelorDegreeRepository;
import com.cloudfen.h1b.service.BachelorDegreeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BachelorDegree}.
 */
@Service
@Transactional
public class BachelorDegreeServiceImpl implements BachelorDegreeService {

    private final Logger log = LoggerFactory.getLogger(BachelorDegreeServiceImpl.class);

    private final BachelorDegreeRepository bachelorDegreeRepository;

    public BachelorDegreeServiceImpl(BachelorDegreeRepository bachelorDegreeRepository) {
        this.bachelorDegreeRepository = bachelorDegreeRepository;
    }

    @Override
    public BachelorDegree save(BachelorDegree bachelorDegree) {
        log.debug("Request to save BachelorDegree : {}", bachelorDegree);
        return bachelorDegreeRepository.save(bachelorDegree);
    }

    @Override
    public Optional<BachelorDegree> partialUpdate(BachelorDegree bachelorDegree) {
        log.debug("Request to partially update BachelorDegree : {}", bachelorDegree);

        return bachelorDegreeRepository
            .findById(bachelorDegree.getId())
            .map(existingBachelorDegree -> {
                if (bachelorDegree.getCourse() != null) {
                    existingBachelorDegree.setCourse(bachelorDegree.getCourse());
                }

                return existingBachelorDegree;
            })
            .map(bachelorDegreeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BachelorDegree> findAll() {
        log.debug("Request to get all BachelorDegrees");
        return bachelorDegreeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BachelorDegree> findOne(Long id) {
        log.debug("Request to get BachelorDegree : {}", id);
        return bachelorDegreeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BachelorDegree : {}", id);
        bachelorDegreeRepository.deleteById(id);
    }
}
