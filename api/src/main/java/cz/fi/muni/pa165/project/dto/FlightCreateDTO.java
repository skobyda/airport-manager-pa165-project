package cz.fi.muni.pa165.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Petr Hendrych
 * @created 27.04.2021
 * @project airport-manager
 **/
public class FlightCreateDTO {

    @NotNull
    private LocalDate departure;

    @NotNull
    private LocalDate arrival;

    @NotNull
    private Long originAirportId;

    @NotNull
    private Long destinationAirportId;

    @NotNull
    private Long airplaneId;

    @NotNull
    @Size(max = 30)
    private String flightCode;

    private Set<Long> stewardIds = new HashSet<>();

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

    public Long getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(Long originAirportId) {
        this.originAirportId = originAirportId;
    }

    public Long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Set<Long> getStewardIds() {
        return stewardIds;
    }

    public void setStewardIds(Set<Long> stewardIds) {
        this.stewardIds = stewardIds;
    }
}
