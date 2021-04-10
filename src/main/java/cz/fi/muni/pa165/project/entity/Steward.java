package cz.fi.muni.pa165.project.entity;

import javax.persistence.*;
import java.util.*;

/**
 * @author Jozef Vanický
 * @created 07.04.2021
 * @project airport-manager
 **/


@Entity
public class Steward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String countryCode;

    private String passportNumber;

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "stewards")
    private Set<Flight> flights = new HashSet<>();

    public Steward() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<Flight> getFlights() {
        return Collections.unmodifiableSet(this.flights);
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Steward)) return false;
        Steward steward = (Steward) o;
        if (passportNumber == null && steward.passportNumber != null) return false;
        if (countryCode == null && steward.countryCode != null) return false;
        return getCountryCode().equals(steward.getCountryCode()) && getPassportNumber().equals(steward.getPassportNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getPassportNumber());
    }
}
