package com.cloudfen.h1b.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.h1b.IntegrationTest;
import com.cloudfen.h1b.domain.BachelorDegree;
import com.cloudfen.h1b.repository.BachelorDegreeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BachelorDegreeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BachelorDegreeResourceIT {

    private static final String DEFAULT_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bachelor-degrees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BachelorDegreeRepository bachelorDegreeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBachelorDegreeMockMvc;

    private BachelorDegree bachelorDegree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BachelorDegree createEntity(EntityManager em) {
        BachelorDegree bachelorDegree = new BachelorDegree().course(DEFAULT_COURSE);
        return bachelorDegree;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BachelorDegree createUpdatedEntity(EntityManager em) {
        BachelorDegree bachelorDegree = new BachelorDegree().course(UPDATED_COURSE);
        return bachelorDegree;
    }

    @BeforeEach
    public void initTest() {
        bachelorDegree = createEntity(em);
    }

    @Test
    @Transactional
    void createBachelorDegree() throws Exception {
        int databaseSizeBeforeCreate = bachelorDegreeRepository.findAll().size();
        // Create the BachelorDegree
        restBachelorDegreeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isCreated());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeCreate + 1);
        BachelorDegree testBachelorDegree = bachelorDegreeList.get(bachelorDegreeList.size() - 1);
        assertThat(testBachelorDegree.getCourse()).isEqualTo(DEFAULT_COURSE);
    }

    @Test
    @Transactional
    void createBachelorDegreeWithExistingId() throws Exception {
        // Create the BachelorDegree with an existing ID
        bachelorDegree.setId(1L);

        int databaseSizeBeforeCreate = bachelorDegreeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBachelorDegreeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isBadRequest());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBachelorDegrees() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        // Get all the bachelorDegreeList
        restBachelorDegreeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bachelorDegree.getId().intValue())))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)));
    }

    @Test
    @Transactional
    void getBachelorDegree() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        // Get the bachelorDegree
        restBachelorDegreeMockMvc
            .perform(get(ENTITY_API_URL_ID, bachelorDegree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bachelorDegree.getId().intValue()))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE));
    }

    @Test
    @Transactional
    void getNonExistingBachelorDegree() throws Exception {
        // Get the bachelorDegree
        restBachelorDegreeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBachelorDegree() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();

        // Update the bachelorDegree
        BachelorDegree updatedBachelorDegree = bachelorDegreeRepository.findById(bachelorDegree.getId()).get();
        // Disconnect from session so that the updates on updatedBachelorDegree are not directly saved in db
        em.detach(updatedBachelorDegree);
        updatedBachelorDegree.course(UPDATED_COURSE);

        restBachelorDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBachelorDegree.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBachelorDegree))
            )
            .andExpect(status().isOk());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
        BachelorDegree testBachelorDegree = bachelorDegreeList.get(bachelorDegreeList.size() - 1);
        assertThat(testBachelorDegree.getCourse()).isEqualTo(UPDATED_COURSE);
    }

    @Test
    @Transactional
    void putNonExistingBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bachelorDegree.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isBadRequest());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isBadRequest());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bachelorDegree)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBachelorDegreeWithPatch() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();

        // Update the bachelorDegree using partial update
        BachelorDegree partialUpdatedBachelorDegree = new BachelorDegree();
        partialUpdatedBachelorDegree.setId(bachelorDegree.getId());

        restBachelorDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBachelorDegree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBachelorDegree))
            )
            .andExpect(status().isOk());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
        BachelorDegree testBachelorDegree = bachelorDegreeList.get(bachelorDegreeList.size() - 1);
        assertThat(testBachelorDegree.getCourse()).isEqualTo(DEFAULT_COURSE);
    }

    @Test
    @Transactional
    void fullUpdateBachelorDegreeWithPatch() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();

        // Update the bachelorDegree using partial update
        BachelorDegree partialUpdatedBachelorDegree = new BachelorDegree();
        partialUpdatedBachelorDegree.setId(bachelorDegree.getId());

        partialUpdatedBachelorDegree.course(UPDATED_COURSE);

        restBachelorDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBachelorDegree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBachelorDegree))
            )
            .andExpect(status().isOk());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
        BachelorDegree testBachelorDegree = bachelorDegreeList.get(bachelorDegreeList.size() - 1);
        assertThat(testBachelorDegree.getCourse()).isEqualTo(UPDATED_COURSE);
    }

    @Test
    @Transactional
    void patchNonExistingBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bachelorDegree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isBadRequest());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isBadRequest());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBachelorDegree() throws Exception {
        int databaseSizeBeforeUpdate = bachelorDegreeRepository.findAll().size();
        bachelorDegree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBachelorDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bachelorDegree))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BachelorDegree in the database
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBachelorDegree() throws Exception {
        // Initialize the database
        bachelorDegreeRepository.saveAndFlush(bachelorDegree);

        int databaseSizeBeforeDelete = bachelorDegreeRepository.findAll().size();

        // Delete the bachelorDegree
        restBachelorDegreeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bachelorDegree.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BachelorDegree> bachelorDegreeList = bachelorDegreeRepository.findAll();
        assertThat(bachelorDegreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
