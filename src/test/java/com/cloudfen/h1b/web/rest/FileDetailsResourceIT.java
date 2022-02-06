package com.cloudfen.h1b.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.h1b.IntegrationTest;
import com.cloudfen.h1b.domain.FileDetails;
import com.cloudfen.h1b.repository.FileDetailsRepository;
import com.cloudfen.h1b.service.criteria.FileDetailsCriteria;
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
 * Integration tests for the {@link FileDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileDetailsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileDetailsMockMvc;

    private FileDetails fileDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileDetails createEntity(EntityManager em) {
        FileDetails fileDetails = new FileDetails()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .type(DEFAULT_TYPE)
            .fileName(DEFAULT_FILE_NAME);
        return fileDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileDetails createUpdatedEntity(EntityManager em) {
        FileDetails fileDetails = new FileDetails()
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME);
        return fileDetails;
    }

    @BeforeEach
    public void initTest() {
        fileDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createFileDetails() throws Exception {
        int databaseSizeBeforeCreate = fileDetailsRepository.findAll().size();
        // Create the FileDetails
        restFileDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileDetails)))
            .andExpect(status().isCreated());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        FileDetails testFileDetails = fileDetailsList.get(fileDetailsList.size() - 1);
        assertThat(testFileDetails.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFileDetails.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testFileDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFileDetails.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void createFileDetailsWithExistingId() throws Exception {
        // Create the FileDetails with an existing ID
        fileDetails.setId(1L);

        int databaseSizeBeforeCreate = fileDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileDetails)))
            .andExpect(status().isBadRequest());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFileDetails() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)));
    }

    @Test
    @Transactional
    void getFileDetails() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get the fileDetails
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, fileDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileDetails.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME));
    }

    @Test
    @Transactional
    void getFileDetailsByIdFiltering() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        Long id = fileDetails.getId();

        defaultFileDetailsShouldBeFound("id.equals=" + id);
        defaultFileDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultFileDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFileDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultFileDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFileDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId equals to DEFAULT_USER_ID
        defaultFileDetailsShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the fileDetailsList where userId equals to UPDATED_USER_ID
        defaultFileDetailsShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId not equals to DEFAULT_USER_ID
        defaultFileDetailsShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the fileDetailsList where userId not equals to UPDATED_USER_ID
        defaultFileDetailsShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultFileDetailsShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the fileDetailsList where userId equals to UPDATED_USER_ID
        defaultFileDetailsShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId is not null
        defaultFileDetailsShouldBeFound("userId.specified=true");

        // Get all the fileDetailsList where userId is null
        defaultFileDetailsShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId contains DEFAULT_USER_ID
        defaultFileDetailsShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the fileDetailsList where userId contains UPDATED_USER_ID
        defaultFileDetailsShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userId does not contain DEFAULT_USER_ID
        defaultFileDetailsShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the fileDetailsList where userId does not contain UPDATED_USER_ID
        defaultFileDetailsShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName equals to DEFAULT_USER_NAME
        defaultFileDetailsShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the fileDetailsList where userName equals to UPDATED_USER_NAME
        defaultFileDetailsShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName not equals to DEFAULT_USER_NAME
        defaultFileDetailsShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the fileDetailsList where userName not equals to UPDATED_USER_NAME
        defaultFileDetailsShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultFileDetailsShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the fileDetailsList where userName equals to UPDATED_USER_NAME
        defaultFileDetailsShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName is not null
        defaultFileDetailsShouldBeFound("userName.specified=true");

        // Get all the fileDetailsList where userName is null
        defaultFileDetailsShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName contains DEFAULT_USER_NAME
        defaultFileDetailsShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the fileDetailsList where userName contains UPDATED_USER_NAME
        defaultFileDetailsShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where userName does not contain DEFAULT_USER_NAME
        defaultFileDetailsShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the fileDetailsList where userName does not contain UPDATED_USER_NAME
        defaultFileDetailsShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type equals to DEFAULT_TYPE
        defaultFileDetailsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the fileDetailsList where type equals to UPDATED_TYPE
        defaultFileDetailsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type not equals to DEFAULT_TYPE
        defaultFileDetailsShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the fileDetailsList where type not equals to UPDATED_TYPE
        defaultFileDetailsShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultFileDetailsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the fileDetailsList where type equals to UPDATED_TYPE
        defaultFileDetailsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type is not null
        defaultFileDetailsShouldBeFound("type.specified=true");

        // Get all the fileDetailsList where type is null
        defaultFileDetailsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type contains DEFAULT_TYPE
        defaultFileDetailsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the fileDetailsList where type contains UPDATED_TYPE
        defaultFileDetailsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFileDetailsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where type does not contain DEFAULT_TYPE
        defaultFileDetailsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the fileDetailsList where type does not contain UPDATED_TYPE
        defaultFileDetailsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName equals to DEFAULT_FILE_NAME
        defaultFileDetailsShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the fileDetailsList where fileName equals to UPDATED_FILE_NAME
        defaultFileDetailsShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName not equals to DEFAULT_FILE_NAME
        defaultFileDetailsShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the fileDetailsList where fileName not equals to UPDATED_FILE_NAME
        defaultFileDetailsShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultFileDetailsShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the fileDetailsList where fileName equals to UPDATED_FILE_NAME
        defaultFileDetailsShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName is not null
        defaultFileDetailsShouldBeFound("fileName.specified=true");

        // Get all the fileDetailsList where fileName is null
        defaultFileDetailsShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName contains DEFAULT_FILE_NAME
        defaultFileDetailsShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the fileDetailsList where fileName contains UPDATED_FILE_NAME
        defaultFileDetailsShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllFileDetailsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        // Get all the fileDetailsList where fileName does not contain DEFAULT_FILE_NAME
        defaultFileDetailsShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the fileDetailsList where fileName does not contain UPDATED_FILE_NAME
        defaultFileDetailsShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileDetailsShouldBeFound(String filter) throws Exception {
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)));

        // Check, that the count call also returns 1
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileDetailsShouldNotBeFound(String filter) throws Exception {
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFileDetails() throws Exception {
        // Get the fileDetails
        restFileDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileDetails() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();

        // Update the fileDetails
        FileDetails updatedFileDetails = fileDetailsRepository.findById(fileDetails.getId()).get();
        // Disconnect from session so that the updates on updatedFileDetails are not directly saved in db
        em.detach(updatedFileDetails);
        updatedFileDetails.userId(UPDATED_USER_ID).userName(UPDATED_USER_NAME).type(UPDATED_TYPE).fileName(UPDATED_FILE_NAME);

        restFileDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFileDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFileDetails))
            )
            .andExpect(status().isOk());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
        FileDetails testFileDetails = fileDetailsList.get(fileDetailsList.size() - 1);
        assertThat(testFileDetails.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFileDetails.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testFileDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFileDetails.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileDetailsWithPatch() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();

        // Update the fileDetails using partial update
        FileDetails partialUpdatedFileDetails = new FileDetails();
        partialUpdatedFileDetails.setId(fileDetails.getId());

        partialUpdatedFileDetails.userName(UPDATED_USER_NAME).fileName(UPDATED_FILE_NAME);

        restFileDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileDetails))
            )
            .andExpect(status().isOk());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
        FileDetails testFileDetails = fileDetailsList.get(fileDetailsList.size() - 1);
        assertThat(testFileDetails.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFileDetails.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testFileDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFileDetails.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateFileDetailsWithPatch() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();

        // Update the fileDetails using partial update
        FileDetails partialUpdatedFileDetails = new FileDetails();
        partialUpdatedFileDetails.setId(fileDetails.getId());

        partialUpdatedFileDetails.userId(UPDATED_USER_ID).userName(UPDATED_USER_NAME).type(UPDATED_TYPE).fileName(UPDATED_FILE_NAME);

        restFileDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileDetails))
            )
            .andExpect(status().isOk());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
        FileDetails testFileDetails = fileDetailsList.get(fileDetailsList.size() - 1);
        assertThat(testFileDetails.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFileDetails.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testFileDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFileDetails.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileDetails() throws Exception {
        int databaseSizeBeforeUpdate = fileDetailsRepository.findAll().size();
        fileDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileDetails in the database
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileDetails() throws Exception {
        // Initialize the database
        fileDetailsRepository.saveAndFlush(fileDetails);

        int databaseSizeBeforeDelete = fileDetailsRepository.findAll().size();

        // Delete the fileDetails
        restFileDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileDetails> fileDetailsList = fileDetailsRepository.findAll();
        assertThat(fileDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
