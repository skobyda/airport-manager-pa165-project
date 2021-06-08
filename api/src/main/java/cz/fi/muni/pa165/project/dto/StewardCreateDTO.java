package cz.fi.muni.pa165.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Jozef Vanický
 **/
public class StewardCreateDTO {

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

        StewardCreateDTO stewardCreateDTO = (StewardCreateDTO) o;

        if (firstName == null && stewardCreateDTO.firstName != null) return false;
        if (lastName == null && stewardCreateDTO.lastName != null) return false;
        if (passportNumber == null && stewardCreateDTO.passportNumber != null) return false;
        if (countryCode == null && stewardCreateDTO.countryCode != null) return false;
        return getCountryCode().equals(stewardCreateDTO.getCountryCode()) && getPassportNumber().equals(stewardCreateDTO.getPassportNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getPassportNumber());
    }

}
