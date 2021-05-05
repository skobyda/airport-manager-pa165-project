package cz.fi.muni.pa165.project.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 @author Simon Kobyda
 @created 27/04/2021
 @project airport-manager
 **/
public class AirportCreateDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    private String city;

    @NotNull(message = "Country cannot be null")
    @Size(min = 2, max = 2, message = "Country code has to be exactly 2 characters long")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirportCreateDTO that = (AirportCreateDTO) o;

        return getName().equals(that.getName()) && getCountry().equals(that.getCountry()) && (getCity().isEmpty() || getCity().equals(that.getCity()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCountry());
    }

    @Override
    public String toString() {
        return "AirportCreateDTO{" +
                "name='" + name + '\'' +
                "city='" + city + '\'' +
                "country='" + country + '\'' +
                '}';
    }
}
