package dmit2015.client; /**
 * The baseUri for the web service be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 *
 * To set the baseUri in microprofile-config.properties:
 *    1) Open src/main/resources/META-INF/microprofile-config.properties
 *    2) Add a key/value pair in the following format:
 *          package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.client
 *          ClassName:       LoginService
 *          baseUri:         https://localhost:8443/contextName
 *       The key/value pair you need to add is:
 *          dmit2015.client.LoginService/mp-rest/url=https://localhost:8443/backend/webapi
 *
 *
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 *
 *     @Inject
 *     @RestClient
 *     private LoginService _loginService;
 *
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 *
 *      URI apiURi = new URI("http://sever/contextName");
 *      LoginService _loginService = RestClientBuilder.newBuilder()
 *                 .baseUri(apiURi)
 *                 .build(LoginService.class);
 *
 */
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient
@Path("/jwt")
public interface LoginService {

    @POST
    @Path("/jsonLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String jsonLogin(JsonObject credentials);

    @POST
    @Path("/formLogin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String formLogin(@FormParam("j_username") String username, @FormParam("j_password") String password);

    @POST
    @Path("/ldapJsonLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String ldapJsonLogin(JsonObject credentials);
}