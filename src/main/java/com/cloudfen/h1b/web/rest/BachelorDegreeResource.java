package com.cloudfen.h1b.web.rest;

import com.cloudfen.h1b.domain.BachelorDegree;
import com.cloudfen.h1b.repository.BachelorDegreeRepository;
import com.cloudfen.h1b.service.BachelorDegreeService;
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
 * REST controller for managing {@link com.cloudfen.h1b.domain.BachelorDegree}.
 */
@RestController
@RequestMapping("/api")
public class BachelorDegreeResource {

    private final Logger log = LoggerFactory.getLogger(BachelorDegreeResource.class);

    private static final String ENTITY_NAME = "bachelorDegree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BachelorDegreeService bachelorDegreeService;

    private final BachelorDegreeRepository bachelorDegreeRepository;

    public BachelorDegreeResource(BachelorDegreeService bachelorDegreeService, BachelorDegreeRepository bachelorDegreeRepository) {
        this.bachelorDegreeService = bachelorDegreeService;
        this.bachelorDegreeRepository = bachelorDegreeRepository;
    }

    /**
     * {@code POST  /bachelor-degrees} : Create a new bachelorDegree.
     *
     * @param bachelorDegree the bachelorDegree to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bachelorDegree, or with status {@code 400 (Bad Request)} if the bachelorDegree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bachelor-degrees")
    public ResponseEntity<BachelorDegree> createBachelorDegree(@RequestBody BachelorDegree bachelorDegree) throws URISyntaxException {
        log.debug("REST request to save BachelorDegree : {}", bachelorDegree);
        if (bachelorDegree.getId() != null) {
            throw new BadRequestAlertException("A new bachelorDegree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BachelorDegree result = bachelorDegreeService.save(bachelorDegree);
        return ResponseEntity
            .created(new URI("/api/bachelor-degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bachelor-degrees/:id} : Updates an existing bachelorDegree.
     *
     * @param id the id of the bachelorDegree to save.
     * @param bachelorDegree the bachelorDegree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bachelorDegree,
     * or with status {@code 400 (Bad Request)} if the bachelorDegree is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bachelorDegree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bachelor-degrees/{id}")
    public ResponseEntity<BachelorDegree> updateBachelorDegree(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BachelorDegree bachelorDegree
    ) throws URISyntaxException {
        log.debug("REST request to update BachelorDegree : {}, {}", id, bachelorDegree);
        if (bachelorDegree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bachelorDegree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bachelorDegreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BachelorDegree result = bachelorDegreeService.save(bachelorDegree);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bachelorDegree.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bachelor-degrees/:id} : Partial updates given fields of an existing bachelorDegree, field will ignore if it is null
     *
     * @param id the id of the bachelorDegree to save.
     * @param bachelorDegree the bachelorDegree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bachelorDegree,
     * or with status {@code 400 (Bad Request)} if the bachelorDegree is not valid,
     * or with status {@code 404 (Not Found)} if the bachelorDegree is not found,
     * or with status {@code 500 (Internal Server Error)} if the bachelorDegree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bachelor-degrees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BachelorDegree> partialUpdateBachelorDegree(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BachelorDegree bachelorDegree
    ) throws URISyntaxException {
        log.debug("REST request to partial update BachelorDegree partially : {}, {}", id, bachelorDegree);
        if (bachelorDegree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bachelorDegree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bachelorDegreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BachelorDegree> result = bachelorDegreeService.partialUpdate(bachelorDegree);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bachelorDegree.getId().toString())
        );
    }

    /**
     * {@code GET  /bachelor-degrees} : get all the bachelorDegrees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bachelorDegrees in body.
     */
    @GetMapping("/bachelor-degrees")
    public List<BachelorDegree> getAllBachelorDegrees() {
        log.debug("REST request to get all BachelorDegrees");
        return bachelorDegreeService.findAll();
    }

    /**
     * {@code GET  /bachelor-degrees/:id} : get the "id" bachelorDegree.
     *
     * @param id the id of the bachelorDegree to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bachelorDegree, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bachelor-degrees/{id}")
    public ResponseEntity<BachelorDegree> getBachelorDegree(@PathVariable Long id) {
        log.debug("REST request to get BachelorDegree : {}", id);
        Optional<BachelorDegree> bachelorDegree = bachelorDegreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bachelorDegree);
    }

    /**
     * {@code DELETE  /bachelor-degrees/:id} : delete the "id" bachelorDegree.
     *
     * @param id the id of the bachelorDegree to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bachelor-degrees/{id}")
    public ResponseEntity<Void> deleteBachelorDegree(@PathVariable Long id) {
        log.debug("REST request to delete BachelorDegree : {}", id);
        bachelorDegreeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
