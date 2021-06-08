package cz.fi.muni.pa165.project.entity;

import cz.fi.muni.pa165.project.enums.AirplaneType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Petr Hendrych
 **/

@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer capacity;

    @Enumerated
    private AirplaneType type;

    @OneToMany(mappedBy = "airplane")
    private Set<Flight> flights = new HashSet<>();

    public Airplane(Long id) {
        this.id = id;
    }

    public Airplane() {
    }

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

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void deleteFlight(Flight flight) {
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
