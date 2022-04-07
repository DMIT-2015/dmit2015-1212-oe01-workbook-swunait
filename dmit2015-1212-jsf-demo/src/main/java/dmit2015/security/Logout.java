package dmit2015.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class Logout {

	@Inject
	private HttpServletRequest request;

	public String submit() throws ServletException {
		request.logout();
		request.getSession().invalidate();
		return "/index?faces-redirect=true";
	}
}