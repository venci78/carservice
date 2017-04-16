package com.vencislav.carserviceapp.web.rest;

import com.vencislav.carserviceapp.CarserviceappApp;

import com.vencislav.carserviceapp.domain.Visit;
import com.vencislav.carserviceapp.repository.VisitRepository;
import com.vencislav.carserviceapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static com.vencislav.carserviceapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VisitResource REST controller.
 *
 * @see VisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarserviceappApp.class)
public class VisitResourceIntTest {

    private static final Long DEFAULT_KM = 1L;
    private static final Long UPDATED_KM = 2L;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_PARTS_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_PARTS_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WORK_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_WORK_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ALL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALL_COST = new BigDecimal(2);

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitMockMvc;

    private Visit visit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VisitResource visitResource = new VisitResource(visitRepository);
        this.restVisitMockMvc = MockMvcBuilders.standaloneSetup(visitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .km(DEFAULT_KM)
            .date(DEFAULT_DATE)
            .partsCost(DEFAULT_PARTS_COST)
            .workCost(DEFAULT_WORK_COST)
            .allCost(DEFAULT_ALL_COST);
        return visit;
    }

    @Before
    public void initTest() {
        visit = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getKm()).isEqualTo(DEFAULT_KM);
        assertThat(testVisit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVisit.getPartsCost()).isEqualTo(DEFAULT_PARTS_COST);
        assertThat(testVisit.getWorkCost()).isEqualTo(DEFAULT_WORK_COST);
        assertThat(testVisit.getAllCost()).isEqualTo(DEFAULT_ALL_COST);
    }

    @Test
    @Transactional
    public void createVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit with an existing ID
        visit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setDate(null);

        // Create the Visit, which fails.

        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartsCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setPartsCost(null);

        // Create the Visit, which fails.

        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setWorkCost(null);

        // Create the Visit, which fails.

        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setAllCost(null);

        // Create the Visit, which fails.

        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].partsCost").value(hasItem(DEFAULT_PARTS_COST.intValue())))
            .andExpect(jsonPath("$.[*].workCost").value(hasItem(DEFAULT_WORK_COST.intValue())))
            .andExpect(jsonPath("$.[*].allCost").value(hasItem(DEFAULT_ALL_COST.intValue())));
    }

    @Test
    @Transactional
    public void getVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId().intValue()))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.partsCost").value(DEFAULT_PARTS_COST.intValue()))
            .andExpect(jsonPath("$.workCost").value(DEFAULT_WORK_COST.intValue()))
            .andExpect(jsonPath("$.allCost").value(DEFAULT_ALL_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findOne(visit.getId());
        updatedVisit
            .km(UPDATED_KM)
            .date(UPDATED_DATE)
            .partsCost(UPDATED_PARTS_COST)
            .workCost(UPDATED_WORK_COST)
            .allCost(UPDATED_ALL_COST);

        restVisitMockMvc.perform(put("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisit)))
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testVisit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVisit.getPartsCost()).isEqualTo(UPDATED_PARTS_COST);
        assertThat(testVisit.getWorkCost()).isEqualTo(UPDATED_WORK_COST);
        assertThat(testVisit.getAllCost()).isEqualTo(UPDATED_ALL_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Create the Visit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitMockMvc.perform(put("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);
        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Get the visit
        restVisitMockMvc.perform(delete("/api/visits/{id}", visit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visit.class);
    }
}
