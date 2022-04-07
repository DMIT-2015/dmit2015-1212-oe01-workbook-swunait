package dmit2015.security;

import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MAX_ATTEMPTS_ALLOWED = 5;

    @Getter
    private int loginAttempts = 0;

    @Inject
    private SecurityContext securityContext;

    @Inject
    @ManagedProperty("#{param.new}")
    private boolean isNew;    // added for Caller-Initiated Authentication

    @NotBlank(message = "Username value is required.")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password value is required.")
    @Getter
    @Setter
    private String password;

    @Inject
    private FacesContext _facesContext;

    public void submit() {
        if (loginAttempts > MAX_ATTEMPTS_ALLOWED) {
            // Sends a permanent (301) redirect to the given URL.
            Faces.redirectPermanent(Faces.getRequestContextPath() + "/errorpages/banned.xhtml");
        }

        switch (continueAuthentication()) {
            case SEND_CONTINUE:
//                Faces.responseComplete();
                _facesContext.responseComplete();
                Faces.redirect(Faces.getRequestContextPath() + "/index.xhtml");        // added for Caller-Initiated Authentication
                break;
            case SEND_FAILURE:
                loginAttempts += 1;
                Messages.addGlobalError("Login failed. Incorrect login credentials.");
                Messages.addGlobalError("Login attempt #{0}", loginAttempts);
                if (loginAttempts > MAX_ATTEMPTS_ALLOWED) {
                    Messages.addGlobalFatal("You {0} are banned from this site", username);
                    // Sends a permanent (301) redirect to the given URL.
                    Faces.redirectPermanent(Faces.getRequestContextPath() + "/errorpages/banned.xhtml");
                }
                break;
            case SUCCESS:
                Faces.getFlash().setKeepMessages(true);
                Messages.addFlashGlobalInfo("Login succeed");
                // Sends a temporary (302) redirect to the given URL.
                Faces.redirect(Faces.getRequestContextPath() + "/index.xhtml");        // added for Caller-Initiated Authentication
                break;
            case NOT_DONE:
                // JSF does not need to take any special action here
                break;
        }
    }

    private AuthenticationStatus continueAuthentication() {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        HttpServletRequest request = Faces.getRequest();
        HttpServletResponse response = Faces.getResponse();
        return securityContext.authenticate(request, response,
                AuthenticationParameters.withParams()
                        .newAuthentication(isNew)    // added for Caller-Initiated Authentication
                        .credential(credential));
    }
}