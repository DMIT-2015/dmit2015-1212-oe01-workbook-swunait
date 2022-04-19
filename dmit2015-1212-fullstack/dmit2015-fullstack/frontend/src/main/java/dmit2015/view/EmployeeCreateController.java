package dmit2015.view;

import dmit2015.client.Employee;
import dmit2015.client.EmployeeService;

import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentEmployeeCreateController")
@RequestScoped
public class EmployeeCreateController {

    @Inject
    @RestClient
    private EmployeeService _employeeService;

    @Inject
    private LoginSession _loginSession;

    @Getter
    private Employee newEmployee = new Employee();

    public String onCreateNew() {
        String nextPage = "";
        try {
            nextPage = _loginSession.checkForToken();
            if (nextPage == null) {
                String authorization = _loginSession.getAuthorization();
                _employeeService.create(newEmployee, authorization);
                Messages.addFlashGlobalInfo("Create was successful.");
                nextPage = "index?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}