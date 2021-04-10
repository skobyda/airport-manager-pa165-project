package cz.fi.muni.pa165.project.entity;

import javax.persistence.*;
import java.util.*;

/**
 * @author Petr Hendrych
 * @created 08.04.2021
 * @project airport-manager
 **/

@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer capacity;

    private String type;

    @OneToMany(mappedBy = "airplane")
    private Set<Flight> flights;

    public Airplane(Integer id) {
        this.id = id;
    }

    public Airplane() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Airplane))
            return false;

        Airplane airplane = (Airplane) o;
        return Objects.equals(getName(), airplane.getName()) &&
                Objects.equals(getCapacity(), airplane.getCapacity()) &&
                Objects.equals(getType(), airplane.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), getType());
    }
}
