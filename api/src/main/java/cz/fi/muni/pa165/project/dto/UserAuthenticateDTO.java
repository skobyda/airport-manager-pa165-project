package cz.fi.muni.pa165.project.dto;

/**
 * @author Petr Hendrych
 * @created 04.05.2021
 * @project airport-manager
 **/
public class UserAuthenticateDTO {

    private Long id;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
