package cz.fi.muni.pa165.project.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Petr Hendrych
 **/
public class FlightDTO {

    private Long id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private AirportSimpleDTO originAirport;
    private AirportSimpleDTO destinationAirport;
    private AirplaneSimpleDTO airplane;
    private String flightCode;
    private HashSet<StewardSimpleDTO> stewards = new HashSet<>();

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


    public AirportSimpleDTO getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(AirportSimpleDTO originAirport) {
        this.originAirport = originAirport;
    }

    public AirportSimpleDTO getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(AirportSimpleDTO destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public AirplaneSimpleDTO getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneSimpleDTO airplane) {
        this.airplane = airplane;
    }

    public HashSet<StewardSimpleDTO> getStewards() {
        return stewards;
    }

    public void setStewards(HashSet<StewardSimpleDTO> stewards) {
        this.stewards = stewards;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightDTO flightDTO = (FlightDTO) o;
        return getFlightCode().equals(flightDTO.getFlightCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlightCode());
    }
}
