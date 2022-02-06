package com.cloudfen.h1b.service.criteria;

import com.cloudfen.h1b.domain.enumeration.CategoryType;
import com.cloudfen.h1b.domain.enumeration.GenderType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cloudfen.h1b.domain.H1B} entity. This class is used
 * in {@link com.cloudfen.h1b.web.rest.H1BResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /h-1-bs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class H1BCriteria implements Serializable, Criteria {

    /**
     * Class for filtering GenderType
     */
    public static class GenderTypeFilter extends Filter<GenderType> {

        public GenderTypeFilter() {}

        public GenderTypeFilter(GenderTypeFilter filter) {
            super(filter);
        }

        @Override
        public GenderTypeFilter copy() {
            return new GenderTypeFilter(this);
        }
    }

    /**
     * Class for filtering CategoryType
     */
    public static class CategoryTypeFilter extends Filter<CategoryType> {

        public CategoryTypeFilter() {}

        public CategoryTypeFilter(CategoryTypeFilter filter) {
            super(filter);
        }

        @Override
        public CategoryTypeFilter copy() {
            return new CategoryTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userId;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private InstantFilter dateOfBirth;

    private StringFilter countryOfBirth;

    private StringFilter countryOfCitizenShip;

    private StringFilter passportNumber;

    private GenderTypeFilter gender;

    private CategoryTypeFilter category;

    private StringFilter email;

    private StringFilter currentAddress;

    private StringFilter phoneNumber;

    private StringFilter bachelorDegree;

    private StringFilter year;

    private StringFilter currentVisaStatus;

    private StringFilter referedBy;

    private Boolean distinct;

    public H1BCriteria() {}

    public H1BCriteria(H1BCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.countryOfBirth = other.countryOfBirth == null ? null : other.countryOfBirth.copy();
        this.countryOfCitizenShip = other.countryOfCitizenShip == null ? null : other.countryOfCitizenShip.copy();
        this.passportNumber = other.passportNumber == null ? null : other.passportNumber.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.currentAddress = other.currentAddress == null ? null : other.currentAddress.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.bachelorDegree = other.bachelorDegree == null ? null : other.bachelorDegree.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.currentVisaStatus = other.currentVisaStatus == null ? null : other.currentVisaStatus.copy();
        this.referedBy = other.referedBy == null ? null : other.referedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public H1BCriteria copy() {
        return new H1BCriteria(this);
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

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
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

    public InstantFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public InstantFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new InstantFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(InstantFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getCountryOfBirth() {
        return countryOfBirth;
    }

    public StringFilter countryOfBirth() {
        if (countryOfBirth == null) {
            countryOfBirth = new StringFilter();
        }
        return countryOfBirth;
    }

    public void setCountryOfBirth(StringFilter countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public StringFilter getCountryOfCitizenShip() {
        return countryOfCitizenShip;
    }

    public StringFilter countryOfCitizenShip() {
        if (countryOfCitizenShip == null) {
            countryOfCitizenShip = new StringFilter();
        }
        return countryOfCitizenShip;
    }

    public void setCountryOfCitizenShip(StringFilter countryOfCitizenShip) {
        this.countryOfCitizenShip = countryOfCitizenShip;
    }

    public StringFilter getPassportNumber() {
        return passportNumber;
    }

    public StringFilter passportNumber() {
        if (passportNumber == null) {
            passportNumber = new StringFilter();
        }
        return passportNumber;
    }

    public void setPassportNumber(StringFilter passportNumber) {
        this.passportNumber = passportNumber;
    }

    public GenderTypeFilter getGender() {
        return gender;
    }

    public GenderTypeFilter gender() {
        if (gender == null) {
            gender = new GenderTypeFilter();
        }
        return gender;
    }

    public void setGender(GenderTypeFilter gender) {
        this.gender = gender;
    }

    public CategoryTypeFilter getCategory() {
        return category;
    }

    public CategoryTypeFilter category() {
        if (category == null) {
            category = new CategoryTypeFilter();
        }
        return category;
    }

    public void setCategory(CategoryTypeFilter category) {
        this.category = category;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getCurrentAddress() {
        return currentAddress;
    }

    public StringFilter currentAddress() {
        if (currentAddress == null) {
            currentAddress = new StringFilter();
        }
        return currentAddress;
    }

    public void setCurrentAddress(StringFilter currentAddress) {
        this.currentAddress = currentAddress;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getBachelorDegree() {
        return bachelorDegree;
    }

    public StringFilter bachelorDegree() {
        if (bachelorDegree == null) {
            bachelorDegree = new StringFilter();
        }
        return bachelorDegree;
    }

    public void setBachelorDegree(StringFilter bachelorDegree) {
        this.bachelorDegree = bachelorDegree;
    }

    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
            year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getCurrentVisaStatus() {
        return currentVisaStatus;
    }

    public StringFilter currentVisaStatus() {
        if (currentVisaStatus == null) {
            currentVisaStatus = new StringFilter();
        }
        return currentVisaStatus;
    }

    public void setCurrentVisaStatus(StringFilter currentVisaStatus) {
        this.currentVisaStatus = currentVisaStatus;
    }

    public StringFilter getReferedBy() {
        return referedBy;
    }

    public StringFilter referedBy() {
        if (referedBy == null) {
            referedBy = new StringFilter();
        }
        return referedBy;
    }

    public void setReferedBy(StringFilter referedBy) {
        this.referedBy = referedBy;
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
        final H1BCriteria that = (H1BCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(countryOfBirth, that.countryOfBirth) &&
            Objects.equals(countryOfCitizenShip, that.countryOfCitizenShip) &&
            Objects.equals(passportNumber, that.passportNumber) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(category, that.category) &&
            Objects.equals(email, that.email) &&
            Objects.equals(currentAddress, that.currentAddress) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(bachelorDegree, that.bachelorDegree) &&
            Objects.equals(year, that.year) &&
            Objects.equals(currentVisaStatus, that.currentVisaStatus) &&
            Objects.equals(referedBy, that.referedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userId,
            firstName,
            middleName,
            lastName,
            dateOfBirth,
            countryOfBirth,
            countryOfCitizenShip,
            passportNumber,
            gender,
            category,
            email,
            currentAddress,
            phoneNumber,
            bachelorDegree,
            year,
            currentVisaStatus,
            referedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "H1BCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (countryOfBirth != null ? "countryOfBirth=" + countryOfBirth + ", " : "") +
            (countryOfCitizenShip != null ? "countryOfCitizenShip=" + countryOfCitizenShip + ", " : "") +
            (passportNumber != null ? "passportNumber=" + passportNumber + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (currentAddress != null ? "currentAddress=" + currentAddress + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (bachelorDegree != null ? "bachelorDegree=" + bachelorDegree + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (currentVisaStatus != null ? "currentVisaStatus=" + currentVisaStatus + ", " : "") +
            (referedBy != null ? "referedBy=" + referedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
