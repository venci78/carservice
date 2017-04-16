package com.vencislav.carserviceapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Worker.
 */
@Entity
@Table(name = "worker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "postition")
    private String postition;

    @OneToMany(mappedBy = "worker")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Visit> visits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Worker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostition() {
        return postition;
    }

    public Worker postition(String postition) {
        this.postition = postition;
        return this;
    }

    public void setPostition(String postition) {
        this.postition = postition;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public Worker visits(Set<Visit> visits) {
        this.visits = visits;
        return this;
    }

    public Worker addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setWorker(this);
        return this;
    }

    public Worker removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.setWorker(null);
        return this;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        if (worker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, worker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Worker{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", postition='" + postition + "'" +
            '}';
    }
}
