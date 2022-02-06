package com.cloudfen.h1b.domain;

import com.cloudfen.h1b.domain.enumeration.CategoryType;
import com.cloudfen.h1b.domain.enumeration.GenderType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A H1B.
 */
@Entity
@Table(name = "h_1_b")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class H1B implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "country_of_citizen_ship")
    private String countryOfCitizenShip;

    @Column(name = "passport_number")
    private String passportNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryType category;

    @Column(name = "email")
    private String email;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "bachelor_degree")
    private String bachelorDegree;

    @Column(name = "year")
    private String year;

    @Column(name = "current_visa_status")
    private String currentVisaStatus;

    @Column(name = "refered_by")
    private String referedBy;

    @Lob
    @Column(name = "passport_front_page")
    private String passportFrontPage;

    @Lob
    @Column(name = "passport_back_page")
    private String passportBackPage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public H1B id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public H1B userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public H1B firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public H1B middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public H1B lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public H1B dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryOfBirth() {
        return this.countryOfBirth;
    }

    public H1B countryOfBirth(String countryOfBirth) {
        this.setCountryOfBirth(countryOfBirth);
        return this;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getCountryOfCitizenShip() {
        return this.countryOfCitizenShip;
    }

    public H1B countryOfCitizenShip(String countryOfCitizenShip) {
        this.setCountryOfCitizenShip(countryOfCitizenShip);
        return this;
    }

    public void setCountryOfCitizenShip(String countryOfCitizenShip) {
        this.countryOfCitizenShip = countryOfCitizenShip;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public H1B passportNumber(String passportNumber) {
        this.setPassportNumber(passportNumber);
        return this;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public GenderType getGender() {
        return this.gender;
    }

    public H1B gender(GenderType gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public CategoryType getCategory() {
        return this.category;
    }

    public H1B category(CategoryType category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public String getEmail() {
        return this.email;
    }

    public H1B email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentAddress() {
        return this.currentAddress;
    }

    public H1B currentAddress(String currentAddress) {
        this.setCurrentAddress(currentAddress);
        return this;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public H1B phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBachelorDegree() {
        return this.bachelorDegree;
    }

    public H1B bachelorDegree(String bachelorDegree) {
        this.setBachelorDegree(bachelorDegree);
        return this;
    }

    public void setBachelorDegree(String bachelorDegree) {
        this.bachelorDegree = bachelorDegree;
    }

    public String getYear() {
        return this.year;
    }

    public H1B year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCurrentVisaStatus() {
        return this.currentVisaStatus;
    }

    public H1B currentVisaStatus(String currentVisaStatus) {
        this.setCurrentVisaStatus(currentVisaStatus);
        return this;
    }

    public void setCurrentVisaStatus(String currentVisaStatus) {
        this.currentVisaStatus = currentVisaStatus;
    }

    public String getReferedBy() {
        return this.referedBy;
    }

    public H1B referedBy(String referedBy) {
        this.setReferedBy(referedBy);
        return this;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public String getPassportFrontPage() {
        return this.passportFrontPage;
    }

    public H1B passportFrontPage(String passportFrontPage) {
        this.setPassportFrontPage(passportFrontPage);
        return this;
    }

    public void setPassportFrontPage(String passportFrontPage) {
        this.passportFrontPage = passportFrontPage;
    }

    public String getPassportBackPage() {
        return this.passportBackPage;
    }

    public H1B passportBackPage(String passportBackPage) {
        this.setPassportBackPage(passportBackPage);
        return this;
    }

    public void setPassportBackPage(String passportBackPage) {
        this.passportBackPage = passportBackPage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof H1B)) {
            return false;
        }
        return id != null && id.equals(((H1B) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "H1B{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", countryOfBirth='" + getCountryOfBirth() + "'" +
            ", countryOfCitizenShip='" + getCountryOfCitizenShip() + "'" +
            ", passportNumber='" + getPassportNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", category='" + getCategory() + "'" +
            ", email='" + getEmail() + "'" +
            ", currentAddress='" + getCurrentAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", bachelorDegree='" + getBachelorDegree() + "'" +
            ", year='" + getYear() + "'" +
            ", currentVisaStatus='" + getCurrentVisaStatus() + "'" +
            ", referedBy='" + getReferedBy() + "'" +
            ", passportFrontPage='" + getPassportFrontPage() + "'" +
            ", passportBackPage='" + getPassportBackPage() + "'" +
            "}";
    }
}
