package cz.fi.muni.pa165.project.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Petr Hendrych
 * @created 27.04.2021
 * @project airport-manager
 **/
public class FlightDTO {

   private Long id;
   private LocalDate departure;
   private LocalDate arrival;
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
