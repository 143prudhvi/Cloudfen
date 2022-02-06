package com.cloudfen.h1b.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BachelorDegree.
 */
@Entity
@Table(name = "bachelor_degree")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BachelorDegree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course")
    private String course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BachelorDegree id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse() {
        return this.course;
    }

    public BachelorDegree course(String course) {
        this.setCourse(course);
        return this;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BachelorDegree)) {
            return false;
        }
        return id != null && id.equals(((BachelorDegree) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BachelorDegree{" +
            "id=" + getId() +
            ", course='" + getCourse() + "'" +
            "}";
    }
}
