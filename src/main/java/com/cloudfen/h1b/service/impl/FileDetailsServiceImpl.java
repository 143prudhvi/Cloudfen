package com.cloudfen.h1b.service.impl;

import com.cloudfen.h1b.domain.FileDetails;
import com.cloudfen.h1b.repository.FileDetailsRepository;
import com.cloudfen.h1b.service.FileDetailsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileDetails}.
 */
@Service
@Transactional
public class FileDetailsServiceImpl implements FileDetailsService {

    private final Logger log = LoggerFactory.getLogger(FileDetailsServiceImpl.class);

    private final FileDetailsRepository fileDetailsRepository;

    public FileDetailsServiceImpl(FileDetailsRepository fileDetailsRepository) {
        this.fileDetailsRepository = fileDetailsRepository;
    }

    @Override
    public FileDetails save(FileDetails fileDetails) {
        log.debug("Request to save FileDetails : {}", fileDetails);
        return fileDetailsRepository.save(fileDetails);
    }

    @Override
    public Optional<FileDetails> partialUpdate(FileDetails fileDetails) {
        log.debug("Request to partially update FileDetails : {}", fileDetails);

        return fileDetailsRepository
            .findById(fileDetails.getId())
            .map(existingFileDetails -> {
                if (fileDetails.getUserName() != null) {
                    existingFileDetails.setUserName(fileDetails.getUserName());
                }
                if (fileDetails.getType() != null) {
                    existingFileDetails.setType(fileDetails.getType());
                }
                if (fileDetails.getFileName() != null) {
                    existingFileDetails.setFileName(fileDetails.getFileName());
                }

                return existingFileDetails;
            })
            .map(fileDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileDetails> findAll() {
        log.debug("Request to get all FileDetails");
        return fileDetailsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileDetails> findOne(Long id) {
        log.debug("Request to get FileDetails : {}", id);
        return fileDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileDetails : {}", id);
        fileDetailsRepository.deleteById(id);
    }
}
