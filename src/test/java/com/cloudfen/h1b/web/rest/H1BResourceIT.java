package com.cloudfen.h1b.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.h1b.IntegrationTest;
import com.cloudfen.h1b.domain.H1B;
import com.cloudfen.h1b.domain.enumeration.CategoryType;
import com.cloudfen.h1b.domain.enumeration.GenderType;
import com.cloudfen.h1b.repository.H1BRepository;
import com.cloudfen.h1b.service.criteria.H1BCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link H1BResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class H1BResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COUNTRY_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_CITIZEN_SHIP = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_CITIZEN_SHIP = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NUMBER = "BBBBBBBBBB";

    private static final GenderType DEFAULT_GENDER = GenderType.MALE;
    private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

    private static final CategoryType DEFAULT_CATEGORY = CategoryType.REGULARCAP;
    private static final CategoryType UPDATED_CATEGORY = CategoryType.MASTERCAP;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BACHELOR_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_BACHELOR_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_VISA_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_VISA_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REFERED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFERED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_FRONT_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_FRONT_PAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_BACK_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_BACK_PAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/h-1-bs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private H1BRepository h1BRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restH1BMockMvc;

    private H1B h1B;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static H1B createEntity(EntityManager em) {
        H1B h1B = new H1B()
            .userId(DEFAULT_USER_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .countryOfBirth(DEFAULT_COUNTRY_OF_BIRTH)
            .countryOfCitizenShip(DEFAULT_COUNTRY_OF_CITIZEN_SHIP)
            .passportNumber(DEFAULT_PASSPORT_NUMBER)
            .gender(DEFAULT_GENDER)
            .category(DEFAULT_CATEGORY)
            .email(DEFAULT_EMAIL)
            .currentAddress(DEFAULT_CURRENT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .bachelorDegree(DEFAULT_BACHELOR_DEGREE)
            .year(DEFAULT_YEAR)
            .currentVisaStatus(DEFAULT_CURRENT_VISA_STATUS)
            .referedBy(DEFAULT_REFERED_BY)
            .passportFrontPage(DEFAULT_PASSPORT_FRONT_PAGE)
            .passportBackPage(DEFAULT_PASSPORT_BACK_PAGE);
        return h1B;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static H1B createUpdatedEntity(EntityManager em) {
        H1B h1B = new H1B()
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .countryOfCitizenShip(UPDATED_COUNTRY_OF_CITIZEN_SHIP)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .gender(UPDATED_GENDER)
            .category(UPDATED_CATEGORY)
            .email(UPDATED_EMAIL)
            .currentAddress(UPDATED_CURRENT_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .bachelorDegree(UPDATED_BACHELOR_DEGREE)
            .year(UPDATED_YEAR)
            .currentVisaStatus(UPDATED_CURRENT_VISA_STATUS)
            .referedBy(UPDATED_REFERED_BY)
            .passportFrontPage(UPDATED_PASSPORT_FRONT_PAGE)
            .passportBackPage(UPDATED_PASSPORT_BACK_PAGE);
        return h1B;
    }

    @BeforeEach
    public void initTest() {
        h1B = createEntity(em);
    }

    @Test
    @Transactional
    void createH1B() throws Exception {
        int databaseSizeBeforeCreate = h1BRepository.findAll().size();
        // Create the H1B
        restH1BMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(h1B)))
            .andExpect(status().isCreated());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeCreate + 1);
        H1B testH1B = h1BList.get(h1BList.size() - 1);
        assertThat(testH1B.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testH1B.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testH1B.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testH1B.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testH1B.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testH1B.getCountryOfBirth()).isEqualTo(DEFAULT_COUNTRY_OF_BIRTH);
        assertThat(testH1B.getCountryOfCitizenShip()).isEqualTo(DEFAULT_COUNTRY_OF_CITIZEN_SHIP);
        assertThat(testH1B.getPassportNumber()).isEqualTo(DEFAULT_PASSPORT_NUMBER);
        assertThat(testH1B.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testH1B.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testH1B.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testH1B.getCurrentAddress()).isEqualTo(DEFAULT_CURRENT_ADDRESS);
        assertThat(testH1B.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testH1B.getBachelorDegree()).isEqualTo(DEFAULT_BACHELOR_DEGREE);
        assertThat(testH1B.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testH1B.getCurrentVisaStatus()).isEqualTo(DEFAULT_CURRENT_VISA_STATUS);
        assertThat(testH1B.getReferedBy()).isEqualTo(DEFAULT_REFERED_BY);
        assertThat(testH1B.getPassportFrontPage()).isEqualTo(DEFAULT_PASSPORT_FRONT_PAGE);
        assertThat(testH1B.getPassportBackPage()).isEqualTo(DEFAULT_PASSPORT_BACK_PAGE);
    }

    @Test
    @Transactional
    void createH1BWithExistingId() throws Exception {
        // Create the H1B with an existing ID
        h1B.setId(1L);

        int databaseSizeBeforeCreate = h1BRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restH1BMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(h1B)))
            .andExpect(status().isBadRequest());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllH1BS() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList
        restH1BMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(h1B.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].countryOfCitizenShip").value(hasItem(DEFAULT_COUNTRY_OF_CITIZEN_SHIP)))
            .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].currentAddress").value(hasItem(DEFAULT_CURRENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].bachelorDegree").value(hasItem(DEFAULT_BACHELOR_DEGREE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].currentVisaStatus").value(hasItem(DEFAULT_CURRENT_VISA_STATUS)))
            .andExpect(jsonPath("$.[*].referedBy").value(hasItem(DEFAULT_REFERED_BY)))
            .andExpect(jsonPath("$.[*].passportFrontPage").value(hasItem(DEFAULT_PASSPORT_FRONT_PAGE.toString())))
            .andExpect(jsonPath("$.[*].passportBackPage").value(hasItem(DEFAULT_PASSPORT_BACK_PAGE.toString())));
    }

    @Test
    @Transactional
    void getH1B() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get the h1B
        restH1BMockMvc
            .perform(get(ENTITY_API_URL_ID, h1B.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(h1B.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.countryOfBirth").value(DEFAULT_COUNTRY_OF_BIRTH))
            .andExpect(jsonPath("$.countryOfCitizenShip").value(DEFAULT_COUNTRY_OF_CITIZEN_SHIP))
            .andExpect(jsonPath("$.passportNumber").value(DEFAULT_PASSPORT_NUMBER))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.currentAddress").value(DEFAULT_CURRENT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.bachelorDegree").value(DEFAULT_BACHELOR_DEGREE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.currentVisaStatus").value(DEFAULT_CURRENT_VISA_STATUS))
            .andExpect(jsonPath("$.referedBy").value(DEFAULT_REFERED_BY))
            .andExpect(jsonPath("$.passportFrontPage").value(DEFAULT_PASSPORT_FRONT_PAGE.toString()))
            .andExpect(jsonPath("$.passportBackPage").value(DEFAULT_PASSPORT_BACK_PAGE.toString()));
    }

    @Test
    @Transactional
    void getH1BSByIdFiltering() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        Long id = h1B.getId();

        defaultH1BShouldBeFound("id.equals=" + id);
        defaultH1BShouldNotBeFound("id.notEquals=" + id);

        defaultH1BShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultH1BShouldNotBeFound("id.greaterThan=" + id);

        defaultH1BShouldBeFound("id.lessThanOrEqual=" + id);
        defaultH1BShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId equals to DEFAULT_USER_ID
        defaultH1BShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the h1BList where userId equals to UPDATED_USER_ID
        defaultH1BShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId not equals to DEFAULT_USER_ID
        defaultH1BShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the h1BList where userId not equals to UPDATED_USER_ID
        defaultH1BShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultH1BShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the h1BList where userId equals to UPDATED_USER_ID
        defaultH1BShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId is not null
        defaultH1BShouldBeFound("userId.specified=true");

        // Get all the h1BList where userId is null
        defaultH1BShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId contains DEFAULT_USER_ID
        defaultH1BShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the h1BList where userId contains UPDATED_USER_ID
        defaultH1BShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllH1BSByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where userId does not contain DEFAULT_USER_ID
        defaultH1BShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the h1BList where userId does not contain UPDATED_USER_ID
        defaultH1BShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName equals to DEFAULT_FIRST_NAME
        defaultH1BShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the h1BList where firstName equals to UPDATED_FIRST_NAME
        defaultH1BShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName not equals to DEFAULT_FIRST_NAME
        defaultH1BShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the h1BList where firstName not equals to UPDATED_FIRST_NAME
        defaultH1BShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultH1BShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the h1BList where firstName equals to UPDATED_FIRST_NAME
        defaultH1BShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName is not null
        defaultH1BShouldBeFound("firstName.specified=true");

        // Get all the h1BList where firstName is null
        defaultH1BShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName contains DEFAULT_FIRST_NAME
        defaultH1BShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the h1BList where firstName contains UPDATED_FIRST_NAME
        defaultH1BShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where firstName does not contain DEFAULT_FIRST_NAME
        defaultH1BShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the h1BList where firstName does not contain UPDATED_FIRST_NAME
        defaultH1BShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultH1BShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the h1BList where middleName equals to UPDATED_MIDDLE_NAME
        defaultH1BShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultH1BShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the h1BList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultH1BShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultH1BShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the h1BList where middleName equals to UPDATED_MIDDLE_NAME
        defaultH1BShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName is not null
        defaultH1BShouldBeFound("middleName.specified=true");

        // Get all the h1BList where middleName is null
        defaultH1BShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName contains DEFAULT_MIDDLE_NAME
        defaultH1BShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the h1BList where middleName contains UPDATED_MIDDLE_NAME
        defaultH1BShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultH1BShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the h1BList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultH1BShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName equals to DEFAULT_LAST_NAME
        defaultH1BShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the h1BList where lastName equals to UPDATED_LAST_NAME
        defaultH1BShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName not equals to DEFAULT_LAST_NAME
        defaultH1BShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the h1BList where lastName not equals to UPDATED_LAST_NAME
        defaultH1BShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultH1BShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the h1BList where lastName equals to UPDATED_LAST_NAME
        defaultH1BShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName is not null
        defaultH1BShouldBeFound("lastName.specified=true");

        // Get all the h1BList where lastName is null
        defaultH1BShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName contains DEFAULT_LAST_NAME
        defaultH1BShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the h1BList where lastName contains UPDATED_LAST_NAME
        defaultH1BShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where lastName does not contain DEFAULT_LAST_NAME
        defaultH1BShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the h1BList where lastName does not contain UPDATED_LAST_NAME
        defaultH1BShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllH1BSByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultH1BShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the h1BList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultH1BShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultH1BShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the h1BList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultH1BShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultH1BShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the h1BList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultH1BShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where dateOfBirth is not null
        defaultH1BShouldBeFound("dateOfBirth.specified=true");

        // Get all the h1BList where dateOfBirth is null
        defaultH1BShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth equals to DEFAULT_COUNTRY_OF_BIRTH
        defaultH1BShouldBeFound("countryOfBirth.equals=" + DEFAULT_COUNTRY_OF_BIRTH);

        // Get all the h1BList where countryOfBirth equals to UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldNotBeFound("countryOfBirth.equals=" + UPDATED_COUNTRY_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth not equals to DEFAULT_COUNTRY_OF_BIRTH
        defaultH1BShouldNotBeFound("countryOfBirth.notEquals=" + DEFAULT_COUNTRY_OF_BIRTH);

        // Get all the h1BList where countryOfBirth not equals to UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldBeFound("countryOfBirth.notEquals=" + UPDATED_COUNTRY_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth in DEFAULT_COUNTRY_OF_BIRTH or UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldBeFound("countryOfBirth.in=" + DEFAULT_COUNTRY_OF_BIRTH + "," + UPDATED_COUNTRY_OF_BIRTH);

        // Get all the h1BList where countryOfBirth equals to UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldNotBeFound("countryOfBirth.in=" + UPDATED_COUNTRY_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth is not null
        defaultH1BShouldBeFound("countryOfBirth.specified=true");

        // Get all the h1BList where countryOfBirth is null
        defaultH1BShouldNotBeFound("countryOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth contains DEFAULT_COUNTRY_OF_BIRTH
        defaultH1BShouldBeFound("countryOfBirth.contains=" + DEFAULT_COUNTRY_OF_BIRTH);

        // Get all the h1BList where countryOfBirth contains UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldNotBeFound("countryOfBirth.contains=" + UPDATED_COUNTRY_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfBirth does not contain DEFAULT_COUNTRY_OF_BIRTH
        defaultH1BShouldNotBeFound("countryOfBirth.doesNotContain=" + DEFAULT_COUNTRY_OF_BIRTH);

        // Get all the h1BList where countryOfBirth does not contain UPDATED_COUNTRY_OF_BIRTH
        defaultH1BShouldBeFound("countryOfBirth.doesNotContain=" + UPDATED_COUNTRY_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip equals to DEFAULT_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldBeFound("countryOfCitizenShip.equals=" + DEFAULT_COUNTRY_OF_CITIZEN_SHIP);

        // Get all the h1BList where countryOfCitizenShip equals to UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldNotBeFound("countryOfCitizenShip.equals=" + UPDATED_COUNTRY_OF_CITIZEN_SHIP);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip not equals to DEFAULT_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldNotBeFound("countryOfCitizenShip.notEquals=" + DEFAULT_COUNTRY_OF_CITIZEN_SHIP);

        // Get all the h1BList where countryOfCitizenShip not equals to UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldBeFound("countryOfCitizenShip.notEquals=" + UPDATED_COUNTRY_OF_CITIZEN_SHIP);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip in DEFAULT_COUNTRY_OF_CITIZEN_SHIP or UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldBeFound("countryOfCitizenShip.in=" + DEFAULT_COUNTRY_OF_CITIZEN_SHIP + "," + UPDATED_COUNTRY_OF_CITIZEN_SHIP);

        // Get all the h1BList where countryOfCitizenShip equals to UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldNotBeFound("countryOfCitizenShip.in=" + UPDATED_COUNTRY_OF_CITIZEN_SHIP);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip is not null
        defaultH1BShouldBeFound("countryOfCitizenShip.specified=true");

        // Get all the h1BList where countryOfCitizenShip is null
        defaultH1BShouldNotBeFound("countryOfCitizenShip.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip contains DEFAULT_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldBeFound("countryOfCitizenShip.contains=" + DEFAULT_COUNTRY_OF_CITIZEN_SHIP);

        // Get all the h1BList where countryOfCitizenShip contains UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldNotBeFound("countryOfCitizenShip.contains=" + UPDATED_COUNTRY_OF_CITIZEN_SHIP);
    }

    @Test
    @Transactional
    void getAllH1BSByCountryOfCitizenShipNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where countryOfCitizenShip does not contain DEFAULT_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldNotBeFound("countryOfCitizenShip.doesNotContain=" + DEFAULT_COUNTRY_OF_CITIZEN_SHIP);

        // Get all the h1BList where countryOfCitizenShip does not contain UPDATED_COUNTRY_OF_CITIZEN_SHIP
        defaultH1BShouldBeFound("countryOfCitizenShip.doesNotContain=" + UPDATED_COUNTRY_OF_CITIZEN_SHIP);
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber equals to DEFAULT_PASSPORT_NUMBER
        defaultH1BShouldBeFound("passportNumber.equals=" + DEFAULT_PASSPORT_NUMBER);

        // Get all the h1BList where passportNumber equals to UPDATED_PASSPORT_NUMBER
        defaultH1BShouldNotBeFound("passportNumber.equals=" + UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber not equals to DEFAULT_PASSPORT_NUMBER
        defaultH1BShouldNotBeFound("passportNumber.notEquals=" + DEFAULT_PASSPORT_NUMBER);

        // Get all the h1BList where passportNumber not equals to UPDATED_PASSPORT_NUMBER
        defaultH1BShouldBeFound("passportNumber.notEquals=" + UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber in DEFAULT_PASSPORT_NUMBER or UPDATED_PASSPORT_NUMBER
        defaultH1BShouldBeFound("passportNumber.in=" + DEFAULT_PASSPORT_NUMBER + "," + UPDATED_PASSPORT_NUMBER);

        // Get all the h1BList where passportNumber equals to UPDATED_PASSPORT_NUMBER
        defaultH1BShouldNotBeFound("passportNumber.in=" + UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber is not null
        defaultH1BShouldBeFound("passportNumber.specified=true");

        // Get all the h1BList where passportNumber is null
        defaultH1BShouldNotBeFound("passportNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber contains DEFAULT_PASSPORT_NUMBER
        defaultH1BShouldBeFound("passportNumber.contains=" + DEFAULT_PASSPORT_NUMBER);

        // Get all the h1BList where passportNumber contains UPDATED_PASSPORT_NUMBER
        defaultH1BShouldNotBeFound("passportNumber.contains=" + UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPassportNumberNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where passportNumber does not contain DEFAULT_PASSPORT_NUMBER
        defaultH1BShouldNotBeFound("passportNumber.doesNotContain=" + DEFAULT_PASSPORT_NUMBER);

        // Get all the h1BList where passportNumber does not contain UPDATED_PASSPORT_NUMBER
        defaultH1BShouldBeFound("passportNumber.doesNotContain=" + UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where gender equals to DEFAULT_GENDER
        defaultH1BShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the h1BList where gender equals to UPDATED_GENDER
        defaultH1BShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllH1BSByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where gender not equals to DEFAULT_GENDER
        defaultH1BShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the h1BList where gender not equals to UPDATED_GENDER
        defaultH1BShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllH1BSByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultH1BShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the h1BList where gender equals to UPDATED_GENDER
        defaultH1BShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllH1BSByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where gender is not null
        defaultH1BShouldBeFound("gender.specified=true");

        // Get all the h1BList where gender is null
        defaultH1BShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where category equals to DEFAULT_CATEGORY
        defaultH1BShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the h1BList where category equals to UPDATED_CATEGORY
        defaultH1BShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllH1BSByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where category not equals to DEFAULT_CATEGORY
        defaultH1BShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the h1BList where category not equals to UPDATED_CATEGORY
        defaultH1BShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllH1BSByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultH1BShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the h1BList where category equals to UPDATED_CATEGORY
        defaultH1BShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllH1BSByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where category is not null
        defaultH1BShouldBeFound("category.specified=true");

        // Get all the h1BList where category is null
        defaultH1BShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email equals to DEFAULT_EMAIL
        defaultH1BShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the h1BList where email equals to UPDATED_EMAIL
        defaultH1BShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllH1BSByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email not equals to DEFAULT_EMAIL
        defaultH1BShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the h1BList where email not equals to UPDATED_EMAIL
        defaultH1BShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllH1BSByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultH1BShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the h1BList where email equals to UPDATED_EMAIL
        defaultH1BShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllH1BSByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email is not null
        defaultH1BShouldBeFound("email.specified=true");

        // Get all the h1BList where email is null
        defaultH1BShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByEmailContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email contains DEFAULT_EMAIL
        defaultH1BShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the h1BList where email contains UPDATED_EMAIL
        defaultH1BShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllH1BSByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where email does not contain DEFAULT_EMAIL
        defaultH1BShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the h1BList where email does not contain UPDATED_EMAIL
        defaultH1BShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress equals to DEFAULT_CURRENT_ADDRESS
        defaultH1BShouldBeFound("currentAddress.equals=" + DEFAULT_CURRENT_ADDRESS);

        // Get all the h1BList where currentAddress equals to UPDATED_CURRENT_ADDRESS
        defaultH1BShouldNotBeFound("currentAddress.equals=" + UPDATED_CURRENT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress not equals to DEFAULT_CURRENT_ADDRESS
        defaultH1BShouldNotBeFound("currentAddress.notEquals=" + DEFAULT_CURRENT_ADDRESS);

        // Get all the h1BList where currentAddress not equals to UPDATED_CURRENT_ADDRESS
        defaultH1BShouldBeFound("currentAddress.notEquals=" + UPDATED_CURRENT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress in DEFAULT_CURRENT_ADDRESS or UPDATED_CURRENT_ADDRESS
        defaultH1BShouldBeFound("currentAddress.in=" + DEFAULT_CURRENT_ADDRESS + "," + UPDATED_CURRENT_ADDRESS);

        // Get all the h1BList where currentAddress equals to UPDATED_CURRENT_ADDRESS
        defaultH1BShouldNotBeFound("currentAddress.in=" + UPDATED_CURRENT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress is not null
        defaultH1BShouldBeFound("currentAddress.specified=true");

        // Get all the h1BList where currentAddress is null
        defaultH1BShouldNotBeFound("currentAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress contains DEFAULT_CURRENT_ADDRESS
        defaultH1BShouldBeFound("currentAddress.contains=" + DEFAULT_CURRENT_ADDRESS);

        // Get all the h1BList where currentAddress contains UPDATED_CURRENT_ADDRESS
        defaultH1BShouldNotBeFound("currentAddress.contains=" + UPDATED_CURRENT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentAddressNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentAddress does not contain DEFAULT_CURRENT_ADDRESS
        defaultH1BShouldNotBeFound("currentAddress.doesNotContain=" + DEFAULT_CURRENT_ADDRESS);

        // Get all the h1BList where currentAddress does not contain UPDATED_CURRENT_ADDRESS
        defaultH1BShouldBeFound("currentAddress.doesNotContain=" + UPDATED_CURRENT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultH1BShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the h1BList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultH1BShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultH1BShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the h1BList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultH1BShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultH1BShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the h1BList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultH1BShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber is not null
        defaultH1BShouldBeFound("phoneNumber.specified=true");

        // Get all the h1BList where phoneNumber is null
        defaultH1BShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultH1BShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the h1BList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultH1BShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultH1BShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the h1BList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultH1BShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree equals to DEFAULT_BACHELOR_DEGREE
        defaultH1BShouldBeFound("bachelorDegree.equals=" + DEFAULT_BACHELOR_DEGREE);

        // Get all the h1BList where bachelorDegree equals to UPDATED_BACHELOR_DEGREE
        defaultH1BShouldNotBeFound("bachelorDegree.equals=" + UPDATED_BACHELOR_DEGREE);
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree not equals to DEFAULT_BACHELOR_DEGREE
        defaultH1BShouldNotBeFound("bachelorDegree.notEquals=" + DEFAULT_BACHELOR_DEGREE);

        // Get all the h1BList where bachelorDegree not equals to UPDATED_BACHELOR_DEGREE
        defaultH1BShouldBeFound("bachelorDegree.notEquals=" + UPDATED_BACHELOR_DEGREE);
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree in DEFAULT_BACHELOR_DEGREE or UPDATED_BACHELOR_DEGREE
        defaultH1BShouldBeFound("bachelorDegree.in=" + DEFAULT_BACHELOR_DEGREE + "," + UPDATED_BACHELOR_DEGREE);

        // Get all the h1BList where bachelorDegree equals to UPDATED_BACHELOR_DEGREE
        defaultH1BShouldNotBeFound("bachelorDegree.in=" + UPDATED_BACHELOR_DEGREE);
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree is not null
        defaultH1BShouldBeFound("bachelorDegree.specified=true");

        // Get all the h1BList where bachelorDegree is null
        defaultH1BShouldNotBeFound("bachelorDegree.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree contains DEFAULT_BACHELOR_DEGREE
        defaultH1BShouldBeFound("bachelorDegree.contains=" + DEFAULT_BACHELOR_DEGREE);

        // Get all the h1BList where bachelorDegree contains UPDATED_BACHELOR_DEGREE
        defaultH1BShouldNotBeFound("bachelorDegree.contains=" + UPDATED_BACHELOR_DEGREE);
    }

    @Test
    @Transactional
    void getAllH1BSByBachelorDegreeNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where bachelorDegree does not contain DEFAULT_BACHELOR_DEGREE
        defaultH1BShouldNotBeFound("bachelorDegree.doesNotContain=" + DEFAULT_BACHELOR_DEGREE);

        // Get all the h1BList where bachelorDegree does not contain UPDATED_BACHELOR_DEGREE
        defaultH1BShouldBeFound("bachelorDegree.doesNotContain=" + UPDATED_BACHELOR_DEGREE);
    }

    @Test
    @Transactional
    void getAllH1BSByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year equals to DEFAULT_YEAR
        defaultH1BShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the h1BList where year equals to UPDATED_YEAR
        defaultH1BShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllH1BSByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year not equals to DEFAULT_YEAR
        defaultH1BShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the h1BList where year not equals to UPDATED_YEAR
        defaultH1BShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllH1BSByYearIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultH1BShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the h1BList where year equals to UPDATED_YEAR
        defaultH1BShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllH1BSByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year is not null
        defaultH1BShouldBeFound("year.specified=true");

        // Get all the h1BList where year is null
        defaultH1BShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByYearContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year contains DEFAULT_YEAR
        defaultH1BShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the h1BList where year contains UPDATED_YEAR
        defaultH1BShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllH1BSByYearNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where year does not contain DEFAULT_YEAR
        defaultH1BShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the h1BList where year does not contain UPDATED_YEAR
        defaultH1BShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus equals to DEFAULT_CURRENT_VISA_STATUS
        defaultH1BShouldBeFound("currentVisaStatus.equals=" + DEFAULT_CURRENT_VISA_STATUS);

        // Get all the h1BList where currentVisaStatus equals to UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldNotBeFound("currentVisaStatus.equals=" + UPDATED_CURRENT_VISA_STATUS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus not equals to DEFAULT_CURRENT_VISA_STATUS
        defaultH1BShouldNotBeFound("currentVisaStatus.notEquals=" + DEFAULT_CURRENT_VISA_STATUS);

        // Get all the h1BList where currentVisaStatus not equals to UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldBeFound("currentVisaStatus.notEquals=" + UPDATED_CURRENT_VISA_STATUS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus in DEFAULT_CURRENT_VISA_STATUS or UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldBeFound("currentVisaStatus.in=" + DEFAULT_CURRENT_VISA_STATUS + "," + UPDATED_CURRENT_VISA_STATUS);

        // Get all the h1BList where currentVisaStatus equals to UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldNotBeFound("currentVisaStatus.in=" + UPDATED_CURRENT_VISA_STATUS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus is not null
        defaultH1BShouldBeFound("currentVisaStatus.specified=true");

        // Get all the h1BList where currentVisaStatus is null
        defaultH1BShouldNotBeFound("currentVisaStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus contains DEFAULT_CURRENT_VISA_STATUS
        defaultH1BShouldBeFound("currentVisaStatus.contains=" + DEFAULT_CURRENT_VISA_STATUS);

        // Get all the h1BList where currentVisaStatus contains UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldNotBeFound("currentVisaStatus.contains=" + UPDATED_CURRENT_VISA_STATUS);
    }

    @Test
    @Transactional
    void getAllH1BSByCurrentVisaStatusNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where currentVisaStatus does not contain DEFAULT_CURRENT_VISA_STATUS
        defaultH1BShouldNotBeFound("currentVisaStatus.doesNotContain=" + DEFAULT_CURRENT_VISA_STATUS);

        // Get all the h1BList where currentVisaStatus does not contain UPDATED_CURRENT_VISA_STATUS
        defaultH1BShouldBeFound("currentVisaStatus.doesNotContain=" + UPDATED_CURRENT_VISA_STATUS);
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByIsEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy equals to DEFAULT_REFERED_BY
        defaultH1BShouldBeFound("referedBy.equals=" + DEFAULT_REFERED_BY);

        // Get all the h1BList where referedBy equals to UPDATED_REFERED_BY
        defaultH1BShouldNotBeFound("referedBy.equals=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy not equals to DEFAULT_REFERED_BY
        defaultH1BShouldNotBeFound("referedBy.notEquals=" + DEFAULT_REFERED_BY);

        // Get all the h1BList where referedBy not equals to UPDATED_REFERED_BY
        defaultH1BShouldBeFound("referedBy.notEquals=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByIsInShouldWork() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy in DEFAULT_REFERED_BY or UPDATED_REFERED_BY
        defaultH1BShouldBeFound("referedBy.in=" + DEFAULT_REFERED_BY + "," + UPDATED_REFERED_BY);

        // Get all the h1BList where referedBy equals to UPDATED_REFERED_BY
        defaultH1BShouldNotBeFound("referedBy.in=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy is not null
        defaultH1BShouldBeFound("referedBy.specified=true");

        // Get all the h1BList where referedBy is null
        defaultH1BShouldNotBeFound("referedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy contains DEFAULT_REFERED_BY
        defaultH1BShouldBeFound("referedBy.contains=" + DEFAULT_REFERED_BY);

        // Get all the h1BList where referedBy contains UPDATED_REFERED_BY
        defaultH1BShouldNotBeFound("referedBy.contains=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    void getAllH1BSByReferedByNotContainsSomething() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        // Get all the h1BList where referedBy does not contain DEFAULT_REFERED_BY
        defaultH1BShouldNotBeFound("referedBy.doesNotContain=" + DEFAULT_REFERED_BY);

        // Get all the h1BList where referedBy does not contain UPDATED_REFERED_BY
        defaultH1BShouldBeFound("referedBy.doesNotContain=" + UPDATED_REFERED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultH1BShouldBeFound(String filter) throws Exception {
        restH1BMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(h1B.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].countryOfCitizenShip").value(hasItem(DEFAULT_COUNTRY_OF_CITIZEN_SHIP)))
            .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].currentAddress").value(hasItem(DEFAULT_CURRENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].bachelorDegree").value(hasItem(DEFAULT_BACHELOR_DEGREE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].currentVisaStatus").value(hasItem(DEFAULT_CURRENT_VISA_STATUS)))
            .andExpect(jsonPath("$.[*].referedBy").value(hasItem(DEFAULT_REFERED_BY)))
            .andExpect(jsonPath("$.[*].passportFrontPage").value(hasItem(DEFAULT_PASSPORT_FRONT_PAGE.toString())))
            .andExpect(jsonPath("$.[*].passportBackPage").value(hasItem(DEFAULT_PASSPORT_BACK_PAGE.toString())));

        // Check, that the count call also returns 1
        restH1BMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultH1BShouldNotBeFound(String filter) throws Exception {
        restH1BMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restH1BMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingH1B() throws Exception {
        // Get the h1B
        restH1BMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewH1B() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();

        // Update the h1B
        H1B updatedH1B = h1BRepository.findById(h1B.getId()).get();
        // Disconnect from session so that the updates on updatedH1B are not directly saved in db
        em.detach(updatedH1B);
        updatedH1B
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .countryOfCitizenShip(UPDATED_COUNTRY_OF_CITIZEN_SHIP)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .gender(UPDATED_GENDER)
            .category(UPDATED_CATEGORY)
            .email(UPDATED_EMAIL)
            .currentAddress(UPDATED_CURRENT_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .bachelorDegree(UPDATED_BACHELOR_DEGREE)
            .year(UPDATED_YEAR)
            .currentVisaStatus(UPDATED_CURRENT_VISA_STATUS)
            .referedBy(UPDATED_REFERED_BY)
            .passportFrontPage(UPDATED_PASSPORT_FRONT_PAGE)
            .passportBackPage(UPDATED_PASSPORT_BACK_PAGE);

        restH1BMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedH1B.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedH1B))
            )
            .andExpect(status().isOk());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
        H1B testH1B = h1BList.get(h1BList.size() - 1);
        assertThat(testH1B.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testH1B.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testH1B.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testH1B.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testH1B.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testH1B.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testH1B.getCountryOfCitizenShip()).isEqualTo(UPDATED_COUNTRY_OF_CITIZEN_SHIP);
        assertThat(testH1B.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testH1B.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testH1B.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testH1B.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testH1B.getCurrentAddress()).isEqualTo(UPDATED_CURRENT_ADDRESS);
        assertThat(testH1B.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testH1B.getBachelorDegree()).isEqualTo(UPDATED_BACHELOR_DEGREE);
        assertThat(testH1B.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testH1B.getCurrentVisaStatus()).isEqualTo(UPDATED_CURRENT_VISA_STATUS);
        assertThat(testH1B.getReferedBy()).isEqualTo(UPDATED_REFERED_BY);
        assertThat(testH1B.getPassportFrontPage()).isEqualTo(UPDATED_PASSPORT_FRONT_PAGE);
        assertThat(testH1B.getPassportBackPage()).isEqualTo(UPDATED_PASSPORT_BACK_PAGE);
    }

    @Test
    @Transactional
    void putNonExistingH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(
                put(ENTITY_API_URL_ID, h1B.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(h1B))
            )
            .andExpect(status().isBadRequest());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(h1B))
            )
            .andExpect(status().isBadRequest());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(h1B)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateH1BWithPatch() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();

        // Update the h1B using partial update
        H1B partialUpdatedH1B = new H1B();
        partialUpdatedH1B.setId(h1B.getId());

        partialUpdatedH1B
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .email(UPDATED_EMAIL)
            .currentAddress(UPDATED_CURRENT_ADDRESS)
            .bachelorDegree(UPDATED_BACHELOR_DEGREE)
            .year(UPDATED_YEAR)
            .referedBy(UPDATED_REFERED_BY);

        restH1BMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedH1B.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedH1B))
            )
            .andExpect(status().isOk());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
        H1B testH1B = h1BList.get(h1BList.size() - 1);
        assertThat(testH1B.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testH1B.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testH1B.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testH1B.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testH1B.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testH1B.getCountryOfBirth()).isEqualTo(DEFAULT_COUNTRY_OF_BIRTH);
        assertThat(testH1B.getCountryOfCitizenShip()).isEqualTo(DEFAULT_COUNTRY_OF_CITIZEN_SHIP);
        assertThat(testH1B.getPassportNumber()).isEqualTo(DEFAULT_PASSPORT_NUMBER);
        assertThat(testH1B.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testH1B.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testH1B.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testH1B.getCurrentAddress()).isEqualTo(UPDATED_CURRENT_ADDRESS);
        assertThat(testH1B.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testH1B.getBachelorDegree()).isEqualTo(UPDATED_BACHELOR_DEGREE);
        assertThat(testH1B.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testH1B.getCurrentVisaStatus()).isEqualTo(DEFAULT_CURRENT_VISA_STATUS);
        assertThat(testH1B.getReferedBy()).isEqualTo(UPDATED_REFERED_BY);
        assertThat(testH1B.getPassportFrontPage()).isEqualTo(DEFAULT_PASSPORT_FRONT_PAGE);
        assertThat(testH1B.getPassportBackPage()).isEqualTo(DEFAULT_PASSPORT_BACK_PAGE);
    }

    @Test
    @Transactional
    void fullUpdateH1BWithPatch() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();

        // Update the h1B using partial update
        H1B partialUpdatedH1B = new H1B();
        partialUpdatedH1B.setId(h1B.getId());

        partialUpdatedH1B
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .countryOfCitizenShip(UPDATED_COUNTRY_OF_CITIZEN_SHIP)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .gender(UPDATED_GENDER)
            .category(UPDATED_CATEGORY)
            .email(UPDATED_EMAIL)
            .currentAddress(UPDATED_CURRENT_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .bachelorDegree(UPDATED_BACHELOR_DEGREE)
            .year(UPDATED_YEAR)
            .currentVisaStatus(UPDATED_CURRENT_VISA_STATUS)
            .referedBy(UPDATED_REFERED_BY)
            .passportFrontPage(UPDATED_PASSPORT_FRONT_PAGE)
            .passportBackPage(UPDATED_PASSPORT_BACK_PAGE);

        restH1BMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedH1B.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedH1B))
            )
            .andExpect(status().isOk());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
        H1B testH1B = h1BList.get(h1BList.size() - 1);
        assertThat(testH1B.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testH1B.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testH1B.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testH1B.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testH1B.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testH1B.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testH1B.getCountryOfCitizenShip()).isEqualTo(UPDATED_COUNTRY_OF_CITIZEN_SHIP);
        assertThat(testH1B.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testH1B.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testH1B.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testH1B.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testH1B.getCurrentAddress()).isEqualTo(UPDATED_CURRENT_ADDRESS);
        assertThat(testH1B.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testH1B.getBachelorDegree()).isEqualTo(UPDATED_BACHELOR_DEGREE);
        assertThat(testH1B.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testH1B.getCurrentVisaStatus()).isEqualTo(UPDATED_CURRENT_VISA_STATUS);
        assertThat(testH1B.getReferedBy()).isEqualTo(UPDATED_REFERED_BY);
        assertThat(testH1B.getPassportFrontPage()).isEqualTo(UPDATED_PASSPORT_FRONT_PAGE);
        assertThat(testH1B.getPassportBackPage()).isEqualTo(UPDATED_PASSPORT_BACK_PAGE);
    }

    @Test
    @Transactional
    void patchNonExistingH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, h1B.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(h1B))
            )
            .andExpect(status().isBadRequest());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(h1B))
            )
            .andExpect(status().isBadRequest());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamH1B() throws Exception {
        int databaseSizeBeforeUpdate = h1BRepository.findAll().size();
        h1B.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restH1BMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(h1B)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the H1B in the database
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteH1B() throws Exception {
        // Initialize the database
        h1BRepository.saveAndFlush(h1B);

        int databaseSizeBeforeDelete = h1BRepository.findAll().size();

        // Delete the h1B
        restH1BMockMvc.perform(delete(ENTITY_API_URL_ID, h1B.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<H1B> h1BList = h1BRepository.findAll();
        assertThat(h1BList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
