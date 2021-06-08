package cz.fi.muni.pa165.project.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Michal Zelenák
 **/
public class AirportSimpleDTO extends AirportCreateDTO{

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
