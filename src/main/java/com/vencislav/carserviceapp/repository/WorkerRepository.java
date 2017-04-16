package com.vencislav.carserviceapp.repository;

import com.vencislav.carserviceapp.domain.Worker;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Worker entity.
 */
@SuppressWarnings("unused")
public interface WorkerRepository extends JpaRepository<Worker,Long> {

}
