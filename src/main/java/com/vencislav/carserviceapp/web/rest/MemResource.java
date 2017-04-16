package com.vencislav.carserviceapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vencislav.carserviceapp.domain.Mem;

import com.vencislav.carserviceapp.repository.MemRepository;
import com.vencislav.carserviceapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mem.
 */
@RestController
@RequestMapping("/api")
public class MemResource {

    private final Logger log = LoggerFactory.getLogger(MemResource.class);

    private static final String ENTITY_NAME = "mem";
        
    private final MemRepository memRepository;

    public MemResource(MemRepository memRepository) {
        this.memRepository = memRepository;
    }

    /**
     * POST  /mems : Create a new mem.
     *
     * @param mem the mem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mem, or with status 400 (Bad Request) if the mem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mems")
    @Timed
    public ResponseEntity<Mem> createMem(@Valid @RequestBody Mem mem) throws URISyntaxException {
        log.debug("REST request to save Mem : {}", mem);
        if (mem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mem cannot already have an ID")).body(null);
        }
        Mem result = memRepository.save(mem);
        return ResponseEntity.created(new URI("/api/mems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mems : Updates an existing mem.
     *
     * @param mem the mem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mem,
     * or with status 400 (Bad Request) if the mem is not valid,
     * or with status 500 (Internal Server Error) if the mem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mems")
    @Timed
    public ResponseEntity<Mem> updateMem(@Valid @RequestBody Mem mem) throws URISyntaxException {
        log.debug("REST request to update Mem : {}", mem);
        if (mem.getId() == null) {
            return createMem(mem);
        }
        Mem result = memRepository.save(mem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mems : get all the mems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mems in body
     */
    @GetMapping("/mems")
    @Timed
    public List<Mem> getAllMems() {
        log.debug("REST request to get all Mems");
        List<Mem> mems = memRepository.findAll();
        return mems;
    }

    /**
     * GET  /mems/:id : get the "id" mem.
     *
     * @param id the id of the mem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mem, or with status 404 (Not Found)
     */
    @GetMapping("/mems/{id}")
    @Timed
    public ResponseEntity<Mem> getMem(@PathVariable Long id) {
        log.debug("REST request to get Mem : {}", id);
        Mem mem = memRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mem));
    }

    /**
     * DELETE  /mems/:id : delete the "id" mem.
     *
     * @param id the id of the mem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mems/{id}")
    @Timed
    public ResponseEntity<Void> deleteMem(@PathVariable Long id) {
        log.debug("REST request to delete Mem : {}", id);
        memRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
