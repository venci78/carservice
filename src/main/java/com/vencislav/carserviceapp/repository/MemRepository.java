package com.vencislav.carserviceapp.repository;

import com.vencislav.carserviceapp.domain.Mem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mem entity.
 */
@SuppressWarnings("unused")
public interface MemRepository extends JpaRepository<Mem,Long> {

}
