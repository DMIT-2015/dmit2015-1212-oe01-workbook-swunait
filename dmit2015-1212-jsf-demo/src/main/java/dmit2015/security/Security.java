package dmit2015.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;

@Named
@ApplicationScoped
public class Security {

    @Inject
    private SecurityContext _securityContext;

    public boolean hasAccessToWebResource(String resource) {
        return _securityContext.hasAccessToWebResource(resource, "GET");
    }

    public boolean isInAnyRole(String... roles) {
        boolean inRole = false;

        if (_securityContext.getCallerPrincipal() != null) {
            for (String singleRole : roles) {
                if (_securityContext.isCallerInRole(singleRole)) {
                    inRole = true;
                    break;
                }
            }
        }

        return inRole;
    }

    public boolean isInAllRole(String... roles) {
        boolean inRole = true;

        if (_securityContext.getCallerPrincipal() != null) {
            for (String singleRole : roles) {
                if (!_securityContext.isCallerInRole(singleRole)) {
                    inRole = false;
                    break;
                }
            }
        }

        return inRole;
    }

    public String getUsername() {
        return _securityContext.getCallerPrincipal().getName();
    }

    public boolean isAuthenticated() {
        return _securityContext.getCallerPrincipal() != null;
    }

}