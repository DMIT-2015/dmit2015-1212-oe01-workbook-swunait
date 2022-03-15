package ca.nait.dmit.resource;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;
import common.validator.BeanValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@ApplicationScoped
@Path("EnforcementZoneCentres")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class EnforcementZoneCentreResource {

    @Inject
    private EnforcementZoneCentreRepository _enforcementZoneCentreRepository;

    @GET
    @Path("count")
    public Response count() {
        return Response.ok(_enforcementZoneCentreRepository.count()).build();
    }

    @GET    // This method only accepts HTTP GET requests.
    public Response listEnforcementZoneCentres() {
        return Response.ok(_enforcementZoneCentreRepository.list()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findEnforcementZoneCentreById(@PathParam("id") Short enforcementZoneCentreId) {
        EnforcementZoneCentre existingEnforcementZoneCentre = _enforcementZoneCentreRepository.findOptional(enforcementZoneCentreId).orElseThrow(NotFoundException::new);

        return Response.ok(existingEnforcementZoneCentre).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addEnforcementZoneCentre(EnforcementZoneCentre newEnforcementZoneCentre, @Context UriInfo uriInfo) {

        String errorMessage = BeanValidator.validateBean(EnforcementZoneCentre.class, newEnforcementZoneCentre);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new EnforcementZoneCentre into the database
            _enforcementZoneCentreRepository.create(newEnforcementZoneCentre);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newEnforcementZoneCentre.getSiteId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the EnforcementZoneCentre was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateEnforcementZoneCentre(@PathParam("id") Short id, EnforcementZoneCentre updatedEnforcementZoneCentre) {
        if (!id.equals(updatedEnforcementZoneCentre.getSiteId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(EnforcementZoneCentre.class, updatedEnforcementZoneCentre);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        EnforcementZoneCentre existingEnforcementZoneCentre = _enforcementZoneCentreRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);
//        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
//        existingEnforcementZoneCentre.setVersion(updatedEnforcementZoneCentre.getVersion());
        existingEnforcementZoneCentre.setLatitude(updatedEnforcementZoneCentre.getLatitude());
        existingEnforcementZoneCentre.setLongitude(updatedEnforcementZoneCentre.getLongitude());
        existingEnforcementZoneCentre.setSpeedLimit(updatedEnforcementZoneCentre.getSpeedLimit());
        existingEnforcementZoneCentre.setLocationDescription(updatedEnforcementZoneCentre.getLocationDescription());
        existingEnforcementZoneCentre.setReasonCodes(updatedEnforcementZoneCentre.getReasonCodes());

        try {
            _enforcementZoneCentreRepository.update(existingEnforcementZoneCentre);
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
        return Response.ok(existingEnforcementZoneCentre).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Short enforcementZoneCentreId) {

        _enforcementZoneCentreRepository.findOptional(enforcementZoneCentreId).orElseThrow(NotFoundException::new);

        try {
            _enforcementZoneCentreRepository.delete(enforcementZoneCentreId);    // Removes the EnforcementZoneCentre from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the EnforcementZoneCentre was successfully deleted
        return Response.noContent().build();
    }

}
    