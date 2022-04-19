package dmit2015.view;

import lombok.Getter;
import lombok.Setter;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String token;

    public String getAuthorization() {
        return "Bearer " + token;
    }

    public String checkForToken() {
        String nextPage = null;

        if (token == null) {
            nextPage = "/login?faces-redirect=true";
        }

        return nextPage;
    }
}