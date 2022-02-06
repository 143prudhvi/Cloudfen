package com.cloudfen.h1b.web.rest;

import com.cloudfen.h1b.domain.H1B;
import com.cloudfen.h1b.repository.H1BRepository;
import com.cloudfen.h1b.service.H1BQueryService;
import com.cloudfen.h1b.service.H1BService;
import com.cloudfen.h1b.service.criteria.H1BCriteria;
import com.cloudfen.h1b.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cloudfen.h1b.domain.H1B}.
 */
@RestController
@RequestMapping("/api")
public class H1BResource {

    private final Logger log = LoggerFactory.getLogger(H1BResource.class);

    private static final String ENTITY_NAME = "h1B";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final H1BService h1BService;

    private final H1BRepository h1BRepository;

    private final H1BQueryService h1BQueryService;

    public H1BResource(H1BService h1BService, H1BRepository h1BRepository, H1BQueryService h1BQueryService) {
        this.h1BService = h1BService;
        this.h1BRepository = h1BRepository;
        this.h1BQueryService = h1BQueryService;
    }

    /**
     * {@code POST  /h-1-bs} : Create a new h1B.
     *
     * @param h1B the h1B to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new h1B, or with status {@code 400 (Bad Request)} if the h1B has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/h-1-bs")
    public ResponseEntity<H1B> createH1B(@RequestBody H1B h1B) throws URISyntaxException {
        log.debug("REST request to save H1B : {}", h1B);
        if (h1B.getId() != null) {
            throw new BadRequestAlertException("A new h1B cannot already have an ID", ENTITY_NAME, "idexists");
        }
        H1B result = h1BService.save(h1B);
        return ResponseEntity
            .created(new URI("/api/h-1-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /h-1-bs/:id} : Updates an existing h1B.
     *
     * @param id the id of the h1B to save.
     * @param h1B the h1B to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated h1B,
     * or with status {@code 400 (Bad Request)} if the h1B is not valid,
     * or with status {@code 500 (Internal Server Error)} if the h1B couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/h-1-bs/{id}")
    public ResponseEntity<H1B> updateH1B(@PathVariable(value = "id", required = false) final Long id, @RequestBody H1B h1B)
        throws URISyntaxException {
        log.debug("REST request to update H1B : {}, {}", id, h1B);
        if (h1B.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, h1B.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!h1BRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        H1B result = h1BService.save(h1B);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, h1B.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /h-1-bs/:id} : Partial updates given fields of an existing h1B, field will ignore if it is null
     *
     * @param id the id of the h1B to save.
     * @param h1B the h1B to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated h1B,
     * or with status {@code 400 (Bad Request)} if the h1B is not valid,
     * or with status {@code 404 (Not Found)} if the h1B is not found,
     * or with status {@code 500 (Internal Server Error)} if the h1B couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/h-1-bs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<H1B> partialUpdateH1B(@PathVariable(value = "id", required = false) final Long id, @RequestBody H1B h1B)
        throws URISyntaxException {
        log.debug("REST request to partial update H1B partially : {}, {}", id, h1B);
        if (h1B.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, h1B.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!h1BRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<H1B> result = h1BService.partialUpdate(h1B);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, h1B.getId().toString())
        );
    }

    /**
     * {@code GET  /h-1-bs} : get all the h1BS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of h1BS in body.
     */
    @GetMapping("/h-1-bs")
    public ResponseEntity<List<H1B>> getAllH1BS(H1BCriteria criteria, @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get H1BS by criteria: {}", criteria);
        Page<H1B> page = h1BQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /h-1-bs/count} : count all the h1BS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/h-1-bs/count")
    public ResponseEntity<Long> countH1BS(H1BCriteria criteria) {
        log.debug("REST request to count H1BS by criteria: {}", criteria);
        return ResponseEntity.ok().body(h1BQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /h-1-bs/:id} : get the "id" h1B.
     *
     * @param id the id of the h1B to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the h1B, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/h-1-bs/{id}")
    public ResponseEntity<H1B> getH1B(@PathVariable Long id) {
        log.debug("REST request to get H1B : {}", id);
        Optional<H1B> h1B = h1BService.findOne(id);
        return ResponseUtil.wrapOrNotFound(h1B);
    }

    /**
     * {@code DELETE  /h-1-bs/:id} : delete the "id" h1B.
     *
     * @param id the id of the h1B to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/h-1-bs/{id}")
    public ResponseEntity<Void> deleteH1B(@PathVariable Long id) {
        log.debug("REST request to delete H1B : {}", id);
        h1BService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
