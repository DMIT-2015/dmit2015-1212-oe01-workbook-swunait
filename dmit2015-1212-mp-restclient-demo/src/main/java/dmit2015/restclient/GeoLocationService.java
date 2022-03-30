package dmit2015.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


/**
 * The baseUri for the web service be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 *
 * To set the baseUri in microprofile-config.properties:
 *    1) Open src/main/resources/META-INF/microprofile-config.properties
 *    2) Add a key/value pair in the following format:
 *          package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.restclient
 *          ClassName:       GeoLocationService
 *          baseUri:         https://api.ipgeolocation.io/ipgeo
 *       The key/value pair you need to add is:
 *          dmit2015.restclient.GeoLocationService/mp-rest/url=https://api.ipgeolocation.io/ipgeo
 *
 *
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 *
 *     @Inject
 *     @RestClient
 *     private GeoLocationService _geoLocationService;
 *
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 *
 *      URI apiURi = new URI("http://sever/path");
 *      GeoLocationService _geoLocationService = RestClientBuilder.newBuilder()
 *                 .baseUri(apiURi)
 *                 .build(GeoLocationService.class);
 *
 */
//@RegisterRestClient(baseUri = "https://api.ipgeolocation.io/ipgeo")
@RegisterRestClient
public interface GeoLocationService {

    @GET
    GeoLocation withIPAddress(
            @QueryParam("apiKey") String apiKey,
            @QueryParam("ip") String ip);

    @GET
    GeoLocation withoutIPAddress(@QueryParam("apiKey") String apiKey );

}
