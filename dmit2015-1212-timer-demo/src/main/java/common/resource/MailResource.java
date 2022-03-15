package common.resource;

import java.net.URI;

import common.ejb.EmailSessionBean;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 * RESTful Web services for sending mail.
 * <p>
 * URI                                        Http Method                Request Body                                                                        Description
 * ----------------        -----------                --------------------------------                                ------------------------------------------
 * /mail/                                POST                        {"mailToAddresses":"username@nait.ca",                        Send a email to the recipient with the subject and text
 * "mailSubject":"DMIT2015 JavaMail Test",
 * "mailText";"Guess JavaMail is working!"}
 * <p>
 * curl command to send mail to username@nait.ca (change username@nait.ca an actual email address) for webapp running on HTTP and @ApplicationPath("webapi") in JAXRSConfiguration.java
 * <p>
 curl -i -X POST http://localhost:8080/contextPath/webapi/mail \
 -d '{"mailToAddresses":"usename@nait.ca", "mailSubject": "DMIT2015 WildFly mail subsystem", "mailText": "This email was sent using the Wildfly mail subsystem and a statless session bean."},' \
 -H 'Content-Type:application/json'
 */
@Path("mail")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailResource {

    @Inject
    private EmailSessionBean mailBean;

    @POST
    public Response sendMail(JsonObject mailJsonObject, @Context UriInfo uriInfo) {

        try {
            mailBean.sendTextEmail(
                    mailJsonObject.getString("mailToAddresses"),
                    mailJsonObject.getString("mailSubject"),
                    mailJsonObject.getString("mailText")
            );
            URI location = uriInfo.getAbsolutePathBuilder()
                    .build();
            return Response
                    .created(location)
                    .build();
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        }
    }
}