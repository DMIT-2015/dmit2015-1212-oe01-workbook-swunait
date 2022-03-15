package ca.nait.dmit.dmit20151212timerdemo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Welcome to Jakarta EE 9.1 Universe!";
    }
}