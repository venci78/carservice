package com.vencislav.carserviceapp.web.rest;

import com.vencislav.carserviceapp.CarserviceappApp;

import com.vencislav.carserviceapp.domain.Mem;
import com.vencislav.carserviceapp.repository.MemRepository;
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
import java.util.List;

import static com.vencislav.carserviceapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MemResource REST controller.
 *
 * @see MemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarserviceappApp.class)
public class MemResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_KM = 1L;
    private static final Long UPDATED_KM = 2L;

    private static final ZonedDateTime DEFAULT_AFTER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AFTER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_AFTER_KM = 1L;
    private static final Long UPDATED_AFTER_KM = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private MemRepository memRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemMockMvc;

    private Mem mem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemResource memResource = new MemResource(memRepository);
        this.restMemMockMvc = MockMvcBuilders.standaloneSetup(memResource)
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
    public static Mem createEntity(EntityManager em) {
        Mem mem = new Mem()
            .date(DEFAULT_DATE)
            .km(DEFAULT_KM)
            .afterDate(DEFAULT_AFTER_DATE)
            .afterKm(DEFAULT_AFTER_KM)
            .comment(DEFAULT_COMMENT);
        return mem;
    }

    @Before
    public void initTest() {
        mem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMem() throws Exception {
        int databaseSizeBeforeCreate = memRepository.findAll().size();

        // Create the Mem
        restMemMockMvc.perform(post("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mem)))
            .andExpect(status().isCreated());

        // Validate the Mem in the database
        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeCreate + 1);
        Mem testMem = memList.get(memList.size() - 1);
        assertThat(testMem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMem.getKm()).isEqualTo(DEFAULT_KM);
        assertThat(testMem.getAfterDate()).isEqualTo(DEFAULT_AFTER_DATE);
        assertThat(testMem.getAfterKm()).isEqualTo(DEFAULT_AFTER_KM);
        assertThat(testMem.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createMemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memRepository.findAll().size();

        // Create the Mem with an existing ID
        mem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemMockMvc.perform(post("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = memRepository.findAll().size();
        // set the field null
        mem.setDate(null);

        // Create the Mem, which fails.

        restMemMockMvc.perform(post("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mem)))
            .andExpect(status().isBadRequest());

        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKmIsRequired() throws Exception {
        int databaseSizeBeforeTest = memRepository.findAll().size();
        // set the field null
        mem.setKm(null);

        // Create the Mem, which fails.

        restMemMockMvc.perform(post("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mem)))
            .andExpect(status().isBadRequest());

        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMems() throws Exception {
        // Initialize the database
        memRepository.saveAndFlush(mem);

        // Get all the memList
        restMemMockMvc.perform(get("/api/mems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mem.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.intValue())))
            .andExpect(jsonPath("$.[*].afterDate").value(hasItem(sameInstant(DEFAULT_AFTER_DATE))))
            .andExpect(jsonPath("$.[*].afterKm").value(hasItem(DEFAULT_AFTER_KM.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getMem() throws Exception {
        // Initialize the database
        memRepository.saveAndFlush(mem);

        // Get the mem
        restMemMockMvc.perform(get("/api/mems/{id}", mem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mem.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM.intValue()))
            .andExpect(jsonPath("$.afterDate").value(sameInstant(DEFAULT_AFTER_DATE)))
            .andExpect(jsonPath("$.afterKm").value(DEFAULT_AFTER_KM.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMem() throws Exception {
        // Get the mem
        restMemMockMvc.perform(get("/api/mems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMem() throws Exception {
        // Initialize the database
        memRepository.saveAndFlush(mem);
        int databaseSizeBeforeUpdate = memRepository.findAll().size();

        // Update the mem
        Mem updatedMem = memRepository.findOne(mem.getId());
        updatedMem
            .date(UPDATED_DATE)
            .km(UPDATED_KM)
            .afterDate(UPDATED_AFTER_DATE)
            .afterKm(UPDATED_AFTER_KM)
            .comment(UPDATED_COMMENT);

        restMemMockMvc.perform(put("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMem)))
            .andExpect(status().isOk());

        // Validate the Mem in the database
        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeUpdate);
        Mem testMem = memList.get(memList.size() - 1);
        assertThat(testMem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMem.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testMem.getAfterDate()).isEqualTo(UPDATED_AFTER_DATE);
        assertThat(testMem.getAfterKm()).isEqualTo(UPDATED_AFTER_KM);
        assertThat(testMem.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMem() throws Exception {
        int databaseSizeBeforeUpdate = memRepository.findAll().size();

        // Create the Mem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemMockMvc.perform(put("/api/mems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mem)))
            .andExpect(status().isCreated());

        // Validate the Mem in the database
        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMem() throws Exception {
        // Initialize the database
        memRepository.saveAndFlush(mem);
        int databaseSizeBeforeDelete = memRepository.findAll().size();

        // Get the mem
        restMemMockMvc.perform(delete("/api/mems/{id}", mem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mem> memList = memRepository.findAll();
        assertThat(memList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mem.class);
    }
}
