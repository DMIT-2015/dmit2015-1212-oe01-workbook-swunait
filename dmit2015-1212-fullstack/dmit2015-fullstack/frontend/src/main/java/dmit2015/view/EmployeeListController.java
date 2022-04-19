package dmit2015.view;

import dmit2015.client.Employee;
import dmit2015.client.EmployeeService;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Named("currentEmployeeListController")
@ViewScoped
public class EmployeeListController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private EmployeeService _employeeService;


    @Getter
    private List<Employee> employeeList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            employeeList = _employeeService.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}