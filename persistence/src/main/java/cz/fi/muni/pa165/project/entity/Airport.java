package cz.fi.muni.pa165.project.entity;

import javax.persistence.*;
import java.util.*;

/**
 @author Simon Kobyda
 @created 07/04/2021
 @project airport-manager
 **/
@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    private String country;

    @OneToMany(mappedBy = "originAirport")
    private Set<Flight> departureFlights = new HashSet<>();

    @OneToMany(mappedBy = "destinationAirport")
    private Set<Flight> arrivalFlights = new HashSet<>();

    public Airport() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void addArrivalFlight(Flight arrival) {
        arrivalFlights.add(arrival);
    }

    public void addDepartureFlight(Flight departure) {
        departureFlights.add(departure);
    }

    public void deleteArrivalFlight(Flight arrival) {
        arrivalFlights.remove(arrival);
    }

    public void deleteDepartureFlight(Flight departure) {
        departureFlights.remove(departure);
    }

    public Set<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public Set<Flight> getDepartureFlights() {
        return departureFlights;
    }

    @Override
    public int hashCode() {
        String hashStr = name.concat(country.concat(city));
        int hash = 7;
        for (int i = 0; i < hashStr.length(); i++) {
            hash = hash*31 + hashStr.charAt(i);
        }

        return hash;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob)
            return true;
        if (ob == null)
            return false;
        if (!(ob instanceof Airport))
            return false;
        if (country == null)
            return false;
        if (name == null)
            return false;
        if (city == null)
            return false;
        Airport airport = (Airport) ob;
        return name.equals(airport.getName())
                && city.equals(airport.getCity())
                && country.equals(airport.getCountry())
                && departureFlights.containsAll(airport.getDepartureFlights())
                && arrivalFlights.containsAll(airport.getArrivalFlights());
    }
}
