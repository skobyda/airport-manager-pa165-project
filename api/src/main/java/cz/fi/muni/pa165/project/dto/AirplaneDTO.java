package cz.fi.muni.pa165.project.dto;

import cz.fi.muni.pa165.project.enums.AirplaneType;

import java.util.*;

/**
 * @author Petr Hendrych
 * @created 26.04.2021
 * @project airport-manager
 **/
public class AirplaneDTO {

    private Long id;
    private String name;
    private Integer capacity;
    private AirplaneType type;
    private Set<FlightDTO> flights = new HashSet<>();

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public AirplaneType getType() {
        return type;
    }

    public void setType(AirplaneType type) {
        this.type = type;
    }

    public Set<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(Set<FlightDTO> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplaneDTO that = (AirplaneDTO) o;

        return getCapacity().equals(that.getCapacity())
                && getName().equals(that.getName())
                && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), getType());
    }
}
