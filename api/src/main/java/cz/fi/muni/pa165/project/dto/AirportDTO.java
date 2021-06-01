package cz.fi.muni.pa165.project.dto;

import java.util.*;

/**
 * @author Simon Kobyda
 * @created 27/04/2021
 * @project airport-manager
 **/
public class AirportDTO {

    private Long id;
    private String name;
    private String city;
    private String country;
    private Set<FlightDTO> departureFlights = new HashSet<>();
    private Set<FlightDTO> arrivalFlights = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<FlightDTO> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(Set<FlightDTO> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public Set<FlightDTO> getArrivalFlights() {
        return arrivalFlights;
    }

    public void setArrivalFlights(Set<FlightDTO> arrivalFlights) {
        this.arrivalFlights = arrivalFlights;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCity(), getCountry());
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob)
            return true;
        if (ob == null)
            return false;
        if (!(ob instanceof AirportDTO))
            return false;
        if (country == null)
            return false;
        if (name == null)
            return false;
        if (city == null)
            return false;
        AirportDTO airport = (AirportDTO) ob;
        return name.equals(airport.name)
                && city.equals(airport.city)
                && country.equals(airport.country)
                && departureFlights.containsAll(airport.departureFlights)
                && arrivalFlights.containsAll(airport.arrivalFlights);
    }
}
