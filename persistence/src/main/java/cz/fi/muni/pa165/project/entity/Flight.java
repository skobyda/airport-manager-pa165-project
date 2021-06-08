package cz.fi.muni.pa165.project.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michal Zelenák
 **/

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    @ManyToOne
    private Airport originAirport;

    @ManyToOne
    private Airport destinationAirport;

    @ManyToOne
    private Airplane airplane;

    @Column(nullable = false, unique = true)
    private String flightCode;

    @ManyToMany
    private Set<Steward> stewards = new HashSet<>();

    public Flight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
        originAirport.addDepartureFlight(this);
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
        destinationAirport.addArrivalFlight(this);
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
        airplane.addFlight(this);
    }

    public Set<Steward> getStewards() {
        return Collections.unmodifiableSet(this.stewards);
    }

    public void setStewards(Set<Steward> stewards) {
        this.stewards = stewards;
        for (Steward steward : stewards) {
            steward.addFlight(this);
        }
    }

    public void addSteward(Steward steward) {
        this.stewards.add(steward);
        steward.addFlight(this);
    }

    public void deleteSteward(Steward steward) {
        stewards.remove(steward);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(getFlightCode(), flight.getFlightCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlightCode());
    }
}
