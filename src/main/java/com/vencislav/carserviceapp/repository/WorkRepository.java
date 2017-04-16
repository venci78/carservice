package com.vencislav.carserviceapp.repository;

import com.vencislav.carserviceapp.domain.Work;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Work entity.
 */
@SuppressWarnings("unused")
public interface WorkRepository extends JpaRepository<Work,Long> {

}
