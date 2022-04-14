package common.listener;

import dmit2015.entity.Employee;
import dmit2015.repository.EmployeeRepository;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ApplicationStartupListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Inject
    private EmployeeRepository _employeeRepository;

    public ApplicationStartupListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        if (_employeeRepository.count() == 0) {
            try {
               _employeeRepository.create(new Employee("Bilbo Baggins", "burglar"));
               _employeeRepository.create(new Employee("Frodo Baggins", "thief"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
