package com.vencislav.carserviceapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Mem.
 */
@Entity
@Table(name = "mem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "km", nullable = false)
    private Long km;

    @Column(name = "after_date")
    private ZonedDateTime afterDate;

    @Column(name = "after_km")
    private Long afterKm;

    @Column(name = "jhi_comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Mem date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getKm() {
        return km;
    }

    public Mem km(Long km) {
        this.km = km;
        return this;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public ZonedDateTime getAfterDate() {
        return afterDate;
    }

    public Mem afterDate(ZonedDateTime afterDate) {
        this.afterDate = afterDate;
        return this;
    }

    public void setAfterDate(ZonedDateTime afterDate) {
        this.afterDate = afterDate;
    }

    public Long getAfterKm() {
        return afterKm;
    }

    public Mem afterKm(Long afterKm) {
        this.afterKm = afterKm;
        return this;
    }

    public void setAfterKm(Long afterKm) {
        this.afterKm = afterKm;
    }

    public String getComment() {
        return comment;
    }

    public Mem comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mem mem = (Mem) o;
        if (mem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mem{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", km='" + km + "'" +
            ", afterDate='" + afterDate + "'" +
            ", afterKm='" + afterKm + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
