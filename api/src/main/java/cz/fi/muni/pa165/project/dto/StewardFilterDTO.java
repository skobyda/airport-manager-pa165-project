package cz.fi.muni.pa165.project.dto;

/**
 * @author Petr Hendrych
 **/
public class StewardFilterDTO {

    private String firstName;
    private String lastName;
    private String countryCode;

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
}
