package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.entity.Employee;
import dmit2015.repository.EmployeeRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@RequestScoped
@Path("employees")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class EmployeeResource {

    @Inject
    private EmployeeRepository _employeeRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listEmployees() {
        return Response.ok(_employeeRepository.list()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findEmployeeById(@PathParam("id") Long employeeId) {
        Employee existingEmployee = _employeeRepository.findOptional(employeeId).orElseThrow(NotFoundException::new);

        return Response.ok(existingEmployee).build();
    }

    @RolesAllowed({"Sales","IT"})
    @POST    // This method only accepts HTTP POST requests.
    public Response addEmployee(Employee newEmployee, @Context UriInfo uriInfo) {

        String errorMessage = BeanValidator.validateBean(Employee.class, newEmployee);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new Employee into the database
            _employeeRepository.create(newEmployee);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newEmployee.getId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the Employee was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @RolesAllowed({"Sales","IT"})
    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateEmployee(@PathParam("id") Long id, Employee updatedEmployee) {
        if (!id.equals(updatedEmployee.getId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(Employee.class, updatedEmployee);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        Employee existingEmployee = _employeeRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);
        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setName(updatedEmployee.getRole());

        try {
            _employeeRepository.update(existingEmployee);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingEmployee).build();
    }

    @RolesAllowed({"IT"})
    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Long id) {

        Employee existingEmployee = _employeeRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);

        try {
            _employeeRepository.remove(existingEmployee);    // Removes the Employee from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the Employee was successfully deleted
        return Response.noContent().build();
    }

}