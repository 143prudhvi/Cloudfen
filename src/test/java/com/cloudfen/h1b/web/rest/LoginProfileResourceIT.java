package com.cloudfen.h1b.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.h1b.IntegrationTest;
import com.cloudfen.h1b.domain.LoginProfile;
import com.cloudfen.h1b.repository.LoginProfileRepository;
import com.cloudfen.h1b.service.criteria.LoginProfileCriteria;
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
 * Integration tests for the {@link LoginProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoginProfileResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/login-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoginProfileRepository loginProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoginProfileMockMvc;

    private LoginProfile loginProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .userName(DEFAULT_USER_NAME)
            .userId(DEFAULT_USER_ID)
            .memberId(DEFAULT_MEMBER_ID)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailId(DEFAULT_EMAIL_ID)
            .password(DEFAULT_PASSWORD)
            .status(DEFAULT_STATUS)
            .activationCode(DEFAULT_ACTIVATION_CODE);
        return loginProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createUpdatedEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);
        return loginProfile;
    }

    @BeforeEach
    public void initTest() {
        loginProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createLoginProfile() throws Exception {
        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();
        // Create the LoginProfile
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isCreated());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate + 1);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(DEFAULT_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void createLoginProfileWithExistingId() throws Exception {
        // Create the LoginProfile with an existing ID
        loginProfile.setId(1L);

        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoginProfiles() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].activationCode").value(hasItem(DEFAULT_ACTIVATION_CODE)));
    }

    @Test
    @Transactional
    void getLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get the loginProfile
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, loginProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginProfile.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.activationCode").value(DEFAULT_ACTIVATION_CODE));
    }

    @Test
    @Transactional
    void getLoginProfilesByIdFiltering() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        Long id = loginProfile.getId();

        defaultLoginProfileShouldBeFound("id.equals=" + id);
        defaultLoginProfileShouldNotBeFound("id.notEquals=" + id);

        defaultLoginProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoginProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultLoginProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoginProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName equals to DEFAULT_USER_NAME
        defaultLoginProfileShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the loginProfileList where userName equals to UPDATED_USER_NAME
        defaultLoginProfileShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName not equals to DEFAULT_USER_NAME
        defaultLoginProfileShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the loginProfileList where userName not equals to UPDATED_USER_NAME
        defaultLoginProfileShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultLoginProfileShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the loginProfileList where userName equals to UPDATED_USER_NAME
        defaultLoginProfileShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName is not null
        defaultLoginProfileShouldBeFound("userName.specified=true");

        // Get all the loginProfileList where userName is null
        defaultLoginProfileShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName contains DEFAULT_USER_NAME
        defaultLoginProfileShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the loginProfileList where userName contains UPDATED_USER_NAME
        defaultLoginProfileShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userName does not contain DEFAULT_USER_NAME
        defaultLoginProfileShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the loginProfileList where userName does not contain UPDATED_USER_NAME
        defaultLoginProfileShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId equals to DEFAULT_USER_ID
        defaultLoginProfileShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the loginProfileList where userId equals to UPDATED_USER_ID
        defaultLoginProfileShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId not equals to DEFAULT_USER_ID
        defaultLoginProfileShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the loginProfileList where userId not equals to UPDATED_USER_ID
        defaultLoginProfileShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultLoginProfileShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the loginProfileList where userId equals to UPDATED_USER_ID
        defaultLoginProfileShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId is not null
        defaultLoginProfileShouldBeFound("userId.specified=true");

        // Get all the loginProfileList where userId is null
        defaultLoginProfileShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId contains DEFAULT_USER_ID
        defaultLoginProfileShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the loginProfileList where userId contains UPDATED_USER_ID
        defaultLoginProfileShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where userId does not contain DEFAULT_USER_ID
        defaultLoginProfileShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the loginProfileList where userId does not contain UPDATED_USER_ID
        defaultLoginProfileShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId equals to DEFAULT_MEMBER_ID
        defaultLoginProfileShouldBeFound("memberId.equals=" + DEFAULT_MEMBER_ID);

        // Get all the loginProfileList where memberId equals to UPDATED_MEMBER_ID
        defaultLoginProfileShouldNotBeFound("memberId.equals=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId not equals to DEFAULT_MEMBER_ID
        defaultLoginProfileShouldNotBeFound("memberId.notEquals=" + DEFAULT_MEMBER_ID);

        // Get all the loginProfileList where memberId not equals to UPDATED_MEMBER_ID
        defaultLoginProfileShouldBeFound("memberId.notEquals=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId in DEFAULT_MEMBER_ID or UPDATED_MEMBER_ID
        defaultLoginProfileShouldBeFound("memberId.in=" + DEFAULT_MEMBER_ID + "," + UPDATED_MEMBER_ID);

        // Get all the loginProfileList where memberId equals to UPDATED_MEMBER_ID
        defaultLoginProfileShouldNotBeFound("memberId.in=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId is not null
        defaultLoginProfileShouldBeFound("memberId.specified=true");

        // Get all the loginProfileList where memberId is null
        defaultLoginProfileShouldNotBeFound("memberId.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId contains DEFAULT_MEMBER_ID
        defaultLoginProfileShouldBeFound("memberId.contains=" + DEFAULT_MEMBER_ID);

        // Get all the loginProfileList where memberId contains UPDATED_MEMBER_ID
        defaultLoginProfileShouldNotBeFound("memberId.contains=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByMemberIdNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where memberId does not contain DEFAULT_MEMBER_ID
        defaultLoginProfileShouldNotBeFound("memberId.doesNotContain=" + DEFAULT_MEMBER_ID);

        // Get all the loginProfileList where memberId does not contain UPDATED_MEMBER_ID
        defaultLoginProfileShouldBeFound("memberId.doesNotContain=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultLoginProfileShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the loginProfileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultLoginProfileShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the loginProfileList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the loginProfileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber is not null
        defaultLoginProfileShouldBeFound("phoneNumber.specified=true");

        // Get all the loginProfileList where phoneNumber is null
        defaultLoginProfileShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultLoginProfileShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the loginProfileList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultLoginProfileShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the loginProfileList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultLoginProfileShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId equals to DEFAULT_EMAIL_ID
        defaultLoginProfileShouldBeFound("emailId.equals=" + DEFAULT_EMAIL_ID);

        // Get all the loginProfileList where emailId equals to UPDATED_EMAIL_ID
        defaultLoginProfileShouldNotBeFound("emailId.equals=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId not equals to DEFAULT_EMAIL_ID
        defaultLoginProfileShouldNotBeFound("emailId.notEquals=" + DEFAULT_EMAIL_ID);

        // Get all the loginProfileList where emailId not equals to UPDATED_EMAIL_ID
        defaultLoginProfileShouldBeFound("emailId.notEquals=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId in DEFAULT_EMAIL_ID or UPDATED_EMAIL_ID
        defaultLoginProfileShouldBeFound("emailId.in=" + DEFAULT_EMAIL_ID + "," + UPDATED_EMAIL_ID);

        // Get all the loginProfileList where emailId equals to UPDATED_EMAIL_ID
        defaultLoginProfileShouldNotBeFound("emailId.in=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId is not null
        defaultLoginProfileShouldBeFound("emailId.specified=true");

        // Get all the loginProfileList where emailId is null
        defaultLoginProfileShouldNotBeFound("emailId.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId contains DEFAULT_EMAIL_ID
        defaultLoginProfileShouldBeFound("emailId.contains=" + DEFAULT_EMAIL_ID);

        // Get all the loginProfileList where emailId contains UPDATED_EMAIL_ID
        defaultLoginProfileShouldNotBeFound("emailId.contains=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByEmailIdNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where emailId does not contain DEFAULT_EMAIL_ID
        defaultLoginProfileShouldNotBeFound("emailId.doesNotContain=" + DEFAULT_EMAIL_ID);

        // Get all the loginProfileList where emailId does not contain UPDATED_EMAIL_ID
        defaultLoginProfileShouldBeFound("emailId.doesNotContain=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password equals to DEFAULT_PASSWORD
        defaultLoginProfileShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the loginProfileList where password equals to UPDATED_PASSWORD
        defaultLoginProfileShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password not equals to DEFAULT_PASSWORD
        defaultLoginProfileShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the loginProfileList where password not equals to UPDATED_PASSWORD
        defaultLoginProfileShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultLoginProfileShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the loginProfileList where password equals to UPDATED_PASSWORD
        defaultLoginProfileShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password is not null
        defaultLoginProfileShouldBeFound("password.specified=true");

        // Get all the loginProfileList where password is null
        defaultLoginProfileShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password contains DEFAULT_PASSWORD
        defaultLoginProfileShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the loginProfileList where password contains UPDATED_PASSWORD
        defaultLoginProfileShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where password does not contain DEFAULT_PASSWORD
        defaultLoginProfileShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the loginProfileList where password does not contain UPDATED_PASSWORD
        defaultLoginProfileShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status equals to DEFAULT_STATUS
        defaultLoginProfileShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loginProfileList where status equals to UPDATED_STATUS
        defaultLoginProfileShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status not equals to DEFAULT_STATUS
        defaultLoginProfileShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the loginProfileList where status not equals to UPDATED_STATUS
        defaultLoginProfileShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoginProfileShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loginProfileList where status equals to UPDATED_STATUS
        defaultLoginProfileShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status is not null
        defaultLoginProfileShouldBeFound("status.specified=true");

        // Get all the loginProfileList where status is null
        defaultLoginProfileShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status contains DEFAULT_STATUS
        defaultLoginProfileShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the loginProfileList where status contains UPDATED_STATUS
        defaultLoginProfileShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where status does not contain DEFAULT_STATUS
        defaultLoginProfileShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the loginProfileList where status does not contain UPDATED_STATUS
        defaultLoginProfileShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode equals to DEFAULT_ACTIVATION_CODE
        defaultLoginProfileShouldBeFound("activationCode.equals=" + DEFAULT_ACTIVATION_CODE);

        // Get all the loginProfileList where activationCode equals to UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldNotBeFound("activationCode.equals=" + UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode not equals to DEFAULT_ACTIVATION_CODE
        defaultLoginProfileShouldNotBeFound("activationCode.notEquals=" + DEFAULT_ACTIVATION_CODE);

        // Get all the loginProfileList where activationCode not equals to UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldBeFound("activationCode.notEquals=" + UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode in DEFAULT_ACTIVATION_CODE or UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldBeFound("activationCode.in=" + DEFAULT_ACTIVATION_CODE + "," + UPDATED_ACTIVATION_CODE);

        // Get all the loginProfileList where activationCode equals to UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldNotBeFound("activationCode.in=" + UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode is not null
        defaultLoginProfileShouldBeFound("activationCode.specified=true");

        // Get all the loginProfileList where activationCode is null
        defaultLoginProfileShouldNotBeFound("activationCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode contains DEFAULT_ACTIVATION_CODE
        defaultLoginProfileShouldBeFound("activationCode.contains=" + DEFAULT_ACTIVATION_CODE);

        // Get all the loginProfileList where activationCode contains UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldNotBeFound("activationCode.contains=" + UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void getAllLoginProfilesByActivationCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList where activationCode does not contain DEFAULT_ACTIVATION_CODE
        defaultLoginProfileShouldNotBeFound("activationCode.doesNotContain=" + DEFAULT_ACTIVATION_CODE);

        // Get all the loginProfileList where activationCode does not contain UPDATED_ACTIVATION_CODE
        defaultLoginProfileShouldBeFound("activationCode.doesNotContain=" + UPDATED_ACTIVATION_CODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoginProfileShouldBeFound(String filter) throws Exception {
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].activationCode").value(hasItem(DEFAULT_ACTIVATION_CODE)));

        // Check, that the count call also returns 1
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoginProfileShouldNotBeFound(String filter) throws Exception {
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoginProfile() throws Exception {
        // Get the loginProfile
        restLoginProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile
        LoginProfile updatedLoginProfile = loginProfileRepository.findById(loginProfile.getId()).get();
        // Disconnect from session so that the updates on updatedLoginProfile are not directly saved in db
        em.detach(updatedLoginProfile);
        updatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void putNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .password(UPDATED_PASSWORD)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void fullUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeDelete = loginProfileRepository.findAll().size();

        // Delete the loginProfile
        restLoginProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, loginProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
