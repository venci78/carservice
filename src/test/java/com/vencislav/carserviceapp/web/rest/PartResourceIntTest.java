package com.vencislav.carserviceapp.web.rest;

import com.vencislav.carserviceapp.CarserviceappApp;

import com.vencislav.carserviceapp.domain.Part;
import com.vencislav.carserviceapp.repository.PartRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartResource REST controller.
 *
 * @see PartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarserviceappApp.class)
public class PartResourceIntTest {

    private static final String DEFAULT_PART = "AAAAAAAAAA";
    private static final String UPDATED_PART = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_PRICE_ONE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_ONE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartMockMvc;

    private Part part;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartResource partResource = new PartResource(partRepository);
        this.restPartMockMvc = MockMvcBuilders.standaloneSetup(partResource)
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
    public static Part createEntity(EntityManager em) {
        Part part = new Part()
            .part(DEFAULT_PART)
            .quantity(DEFAULT_QUANTITY)
            .priceOne(DEFAULT_PRICE_ONE)
            .cost(DEFAULT_COST);
        return part;
    }

    @Before
    public void initTest() {
        part = createEntity(em);
    }

    @Test
    @Transactional
    public void createPart() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();

        // Create the Part
        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isCreated());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate + 1);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getPart()).isEqualTo(DEFAULT_PART);
        assertThat(testPart.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPart.getPriceOne()).isEqualTo(DEFAULT_PRICE_ONE);
        assertThat(testPart.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createPartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();

        // Create the Part with an existing ID
        part.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPartIsRequired() throws Exception {
        int databaseSizeBeforeTest = partRepository.findAll().size();
        // set the field null
        part.setPart(null);

        // Create the Part, which fails.

        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = partRepository.findAll().size();
        // set the field null
        part.setQuantity(null);

        // Create the Part, which fails.

        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = partRepository.findAll().size();
        // set the field null
        part.setPriceOne(null);

        // Create the Part, which fails.

        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = partRepository.findAll().size();
        // set the field null
        part.setCost(null);

        // Create the Part, which fails.

        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParts() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get all the partList
        restPartMockMvc.perform(get("/api/parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(part.getId().intValue())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].priceOne").value(hasItem(DEFAULT_PRICE_ONE.intValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())));
    }

    @Test
    @Transactional
    public void getPart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", part.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(part.getId().intValue()))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.priceOne").value(DEFAULT_PRICE_ONE.intValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPart() throws Exception {
        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);
        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Update the part
        Part updatedPart = partRepository.findOne(part.getId());
        updatedPart
            .part(UPDATED_PART)
            .quantity(UPDATED_QUANTITY)
            .priceOne(UPDATED_PRICE_ONE)
            .cost(UPDATED_COST);

        restPartMockMvc.perform(put("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPart)))
            .andExpect(status().isOk());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getPart()).isEqualTo(UPDATED_PART);
        assertThat(testPart.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPart.getPriceOne()).isEqualTo(UPDATED_PRICE_ONE);
        assertThat(testPart.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingPart() throws Exception {
        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Create the Part

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartMockMvc.perform(put("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isCreated());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);
        int databaseSizeBeforeDelete = partRepository.findAll().size();

        // Get the part
        restPartMockMvc.perform(delete("/api/parts/{id}", part.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Part.class);
    }
}
