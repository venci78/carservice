package com.vencislav.carserviceapp.repository;

import com.vencislav.carserviceapp.domain.Part;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Part entity.
 */
@SuppressWarnings("unused")
public interface PartRepository extends JpaRepository<Part,Long> {

}
