package cz.fi.muni.pa165.project.dto;

import cz.fi.muni.pa165.project.enums.AirplaneType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Petr Hendrych
 **/
public class AirplaneCreateDTO {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer capacity;

    @NotNull
    private AirplaneType type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplaneCreateDTO that = (AirplaneCreateDTO) o;

        return getName().equals(that.getName()) && getCapacity().equals(that.getCapacity()) && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), getType());
    }
}
