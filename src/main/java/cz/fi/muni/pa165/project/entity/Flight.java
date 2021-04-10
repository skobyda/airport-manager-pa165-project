package cz.fi.muni.pa165.project.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Michal Zelenák
 * @created 09.04.2021
 * @project airport-manager
 **/

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate departure;

    private LocalDate arrival;

    @ManyToOne
    private Airport originAirport;

    @ManyToOne
    private Airport destinationAirport;

    @ManyToOne
    private Airplane airplane;

    private String flightCode;

    @ManyToMany
    private Set<Steward> stewards= new HashSet<>();

    public Flight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Set<Steward> getStewards() {
        return Collections.unmodifiableSet(this.stewards);
    }

    public void addSteward(Steward steward) {
        this.stewards.add(steward);
    }

    public void setStewards(Set<Steward> stewards){
        this.stewards=stewards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return getFlightCode().equals(flight.getFlightCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlightCode());
    }
}
