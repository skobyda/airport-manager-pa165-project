package cz.fi.muni.pa165.project.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Petr Hendrych
 **/
public class AirplaneDTO extends AirplaneCreateDTO{

    private Long id;
    private Set<FlightDTO> flights = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(Set<FlightDTO> flights) {
        this.flights = flights;
    }
}
