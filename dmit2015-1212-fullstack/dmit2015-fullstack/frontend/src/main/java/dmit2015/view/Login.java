package dmit2015.view;

import dmit2015.client.LoginService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotBlank;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class Login {

    @Inject
    @RestClient
    private LoginService _loginService;

    @NotBlank(message = "Username value is required.")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password value is required.")
    @Getter
    @Setter
    private String password;

    @Inject
    private LoginSession _loginSession;

    public String submit() {
        String nextPage = null;
        JsonObject credentials = Json.createObjectBuilder()
                .add("username", username)
                .add("password", password)
                .build();
        try {
            String token = _loginService.ldapJsonLogin(credentials);
            _loginSession.setToken(token);
            _loginSession.setUsername(username);
            nextPage = "/index?faces-redirect=true";
        } catch (Exception e) {
            Messages.addGlobalError(e.getMessage());
        }

        return nextPage;
    }

}