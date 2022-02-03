package dmit2015.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 *
 * curl -k -i https://localhost:8443/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: text/plain'
 * curl -k -i -X GET https://localhost:8443/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: text/html'
 * curl -k -i -X GET https://localhost:8443/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: application/json'
 * curl -OJ -k -X GET https://localhost:8443/dmit2015-1212-jaxrs-demo/webapi/helloworld/image
 * 
 */
@RequestScoped
@Path("/helloworld")
public class HelloWorldResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN) //@Produces("text/plain")
    public Response helloWorldText() {
        String message = "Hello World from JAX-RS!";
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML) // "text/html"
    public Response helloWorldHtml() {
        String message = "<p>Hello World from <strong>JAX-RS</strong>";
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)   // "application/json"
    public Response helloWorldJson() {
        String message = "{\"message\":\"Hello World from JAX-RS\"}";
        return Response.ok(message).build();
    }

    @Path("/image")
    @GET
    @Produces("image/png")
    public Response helloImage(@Context HttpServletRequest request) {
        File imageFile = new File(URI.create("file:///home/user2015/Pictures/globe1.jpeg"));
        return Response
                .ok(imageFile)
                .header("Content-Disposition","attachment; filename=globe1.jpeg")
                .build();

//        InputStream is = getClass().getResourceAsStream("/images/globe2.png");
//        return Response
//                .ok(is)
//                .header("Content-Disposition","attachment; filename=globe2.png")
//                .build();
    }

}
