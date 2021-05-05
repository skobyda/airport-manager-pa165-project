package cz.fi.muni.pa165.project.dto;

import java.util.*;

/**
 * @author Jozef Vanický
 * @created 27.04.2021
 * @project airport-manager
 **/
public class StewardDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String passportNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StewardDTO stewardDTO = (StewardDTO) o;

        if (passportNumber == null && stewardDTO.passportNumber != null) return false;
        if (countryCode == null && stewardDTO.countryCode != null) return false;
        return getCountryCode().equals(stewardDTO.getCountryCode()) && getPassportNumber().equals(stewardDTO.getPassportNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getPassportNumber());
    }

}
