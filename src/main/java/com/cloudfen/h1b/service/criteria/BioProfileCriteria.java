package com.cloudfen.h1b.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cloudfen.h1b.domain.BioProfile} entity. This class is used
 * in {@link com.cloudfen.h1b.web.rest.BioProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bio-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BioProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter userId;

    private StringFilter memberId;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter dob;

    private StringFilter gender;

    private StringFilter title;

    private Boolean distinct;

    public BioProfileCriteria() {}

    public BioProfileCriteria(BioProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BioProfileCriteria copy() {
        return new BioProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public StringFilter userId() {
        if (userId == null) {
            userId = new StringFilter();
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getMemberId() {
        return memberId;
    }

    public StringFilter memberId() {
        if (memberId == null) {
            memberId = new StringFilter();
        }
        return memberId;
    }

    public void setMemberId(StringFilter memberId) {
        this.memberId = memberId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getDob() {
        return dob;
    }

    public StringFilter dob() {
        if (dob == null) {
            dob = new StringFilter();
        }
        return dob;
    }

    public void setDob(StringFilter dob) {
        this.dob = dob;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BioProfileCriteria that = (BioProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(title, that.title) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userId, memberId, firstName, lastName, dob, gender, title, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BioProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
