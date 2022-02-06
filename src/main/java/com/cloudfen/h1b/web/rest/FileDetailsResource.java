package com.cloudfen.h1b.web.rest;

import com.cloudfen.h1b.domain.FileDetails;
import com.cloudfen.h1b.repository.FileDetailsRepository;
import com.cloudfen.h1b.service.FileDetailsQueryService;
import com.cloudfen.h1b.service.FileDetailsService;
import com.cloudfen.h1b.service.criteria.FileDetailsCriteria;
import com.cloudfen.h1b.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cloudfen.h1b.domain.FileDetails}.
 */
@RestController
@RequestMapping("/api")
public class FileDetailsResource {

    private final Logger log = LoggerFactory.getLogger(FileDetailsResource.class);

    private static final String ENTITY_NAME = "fileDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileDetailsService fileDetailsService;

    private final FileDetailsRepository fileDetailsRepository;

    private final FileDetailsQueryService fileDetailsQueryService;

    public FileDetailsResource(
        FileDetailsService fileDetailsService,
        FileDetailsRepository fileDetailsRepository,
        FileDetailsQueryService fileDetailsQueryService
    ) {
        this.fileDetailsService = fileDetailsService;
        this.fileDetailsRepository = fileDetailsRepository;
        this.fileDetailsQueryService = fileDetailsQueryService;
    }

    /**
     * {@code POST  /file-details} : Create a new fileDetails.
     *
     * @param fileDetails the fileDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileDetails, or with status {@code 400 (Bad Request)} if the fileDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-details")
    public ResponseEntity<FileDetails> createFileDetails(@RequestBody FileDetails fileDetails) throws URISyntaxException {
        log.debug("REST request to save FileDetails : {}", fileDetails);
        if (fileDetails.getId() != null) {
            throw new BadRequestAlertException("A new fileDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileDetails result = fileDetailsService.save(fileDetails);
        return ResponseEntity
            .created(new URI("/api/file-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-details/:id} : Updates an existing fileDetails.
     *
     * @param id the id of the fileDetails to save.
     * @param fileDetails the fileDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDetails,
     * or with status {@code 400 (Bad Request)} if the fileDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-details/{id}")
    public ResponseEntity<FileDetails> updateFileDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileDetails fileDetails
    ) throws URISyntaxException {
        log.debug("REST request to update FileDetails : {}, {}", id, fileDetails);
        if (fileDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileDetails result = fileDetailsService.save(fileDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-details/:id} : Partial updates given fields of an existing fileDetails, field will ignore if it is null
     *
     * @param id the id of the fileDetails to save.
     * @param fileDetails the fileDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDetails,
     * or with status {@code 400 (Bad Request)} if the fileDetails is not valid,
     * or with status {@code 404 (Not Found)} if the fileDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileDetails> partialUpdateFileDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileDetails fileDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileDetails partially : {}, {}", id, fileDetails);
        if (fileDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileDetails> result = fileDetailsService.partialUpdate(fileDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /file-details} : get all the fileDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileDetails in body.
     */
    @GetMapping("/file-details")
    public ResponseEntity<List<FileDetails>> getAllFileDetails(FileDetailsCriteria criteria) {
        log.debug("REST request to get FileDetails by criteria: {}", criteria);
        List<FileDetails> entityList = fileDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /file-details/count} : count all the fileDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/file-details/count")
    public ResponseEntity<Long> countFileDetails(FileDetailsCriteria criteria) {
        log.debug("REST request to count FileDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /file-details/:id} : get the "id" fileDetails.
     *
     * @param id the id of the fileDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-details/{id}")
    public ResponseEntity<FileDetails> getFileDetails(@PathVariable Long id) {
        log.debug("REST request to get FileDetails : {}", id);
        Optional<FileDetails> fileDetails = fileDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileDetails);
    }

    /**
     * {@code DELETE  /file-details/:id} : delete the "id" fileDetails.
     *
     * @param id the id of the fileDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-details/{id}")
    public ResponseEntity<Void> deleteFileDetails(@PathVariable Long id) {
        log.debug("REST request to delete FileDetails : {}", id);
        fileDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
