package cz.fi.muni.pa165.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Michal Zelenák
 * @created 31.05.2021
 * @project airport-manager
 **/
public class StewardSimpleDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 666) //based on wiki of "Hubert Blaine Wolfeschlegelsteinhausenbergerdorff Sr."
    private String lastName;

    @NotNull
    @Size(min = 2, max = 2)
    private String countryCode;

    @NotNull
    @Size(min = 9, max = 9)
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

        StewardSimpleDTO stewardSimpleDTO = (StewardSimpleDTO) o;

        if (firstName == null && stewardSimpleDTO.firstName != null) return false;
        if (lastName == null && stewardSimpleDTO.lastName != null) return false;
        if (passportNumber == null && stewardSimpleDTO.passportNumber != null) return false;
        if (countryCode == null && stewardSimpleDTO.countryCode != null) return false;
        return getCountryCode().equals(stewardSimpleDTO.getCountryCode()) && getPassportNumber().equals(stewardSimpleDTO.getPassportNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getPassportNumber());
    }

}
