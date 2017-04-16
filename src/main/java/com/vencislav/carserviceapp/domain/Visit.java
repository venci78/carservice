package com.vencislav.carserviceapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "km")
    private Long km;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "parts_cost", precision=10, scale=2, nullable = false)
    private BigDecimal partsCost;

    @NotNull
    @Column(name = "work_cost", precision=10, scale=2, nullable = false)
    private BigDecimal workCost;

    @NotNull
    @Column(name = "all_cost", precision=10, scale=2, nullable = false)
    private BigDecimal allCost;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Worker worker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKm() {
        return km;
    }

    public Visit km(Long km) {
        this.km = km;
        return this;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Visit date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getPartsCost() {
        return partsCost;
    }

    public Visit partsCost(BigDecimal partsCost) {
        this.partsCost = partsCost;
        return this;
    }

    public void setPartsCost(BigDecimal partsCost) {
        this.partsCost = partsCost;
    }

    public BigDecimal getWorkCost() {
        return workCost;
    }

    public Visit workCost(BigDecimal workCost) {
        this.workCost = workCost;
        return this;
    }

    public void setWorkCost(BigDecimal workCost) {
        this.workCost = workCost;
    }

    public BigDecimal getAllCost() {
        return allCost;
    }

    public Visit allCost(BigDecimal allCost) {
        this.allCost = allCost;
        return this;
    }

    public void setAllCost(BigDecimal allCost) {
        this.allCost = allCost;
    }

    public Car getCar() {
        return car;
    }

    public Visit car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Worker getWorker() {
        return worker;
    }

    public Visit worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visit visit = (Visit) o;
        if (visit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Visit{" +
            "id=" + id +
            ", km='" + km + "'" +
            ", date='" + date + "'" +
            ", partsCost='" + partsCost + "'" +
            ", workCost='" + workCost + "'" +
            ", allCost='" + allCost + "'" +
            '}';
    }
}
