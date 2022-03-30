package dmit2015.model;

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
 *          package-name:    dmit2015.model
 *          ClassName:       WeatherService
 *          baseUri:         https://api.openweathermap.org/data/2.5/weather
 *       The key/value pair you need to add is:
 *          dmit2015.model.MovieMicroprofileService/mp-rest/url=https://api.openweathermap.org/data/2.5/weather
 *
 *
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 *
 *     @Inject
 *     @RestClient
 *     private WeatherService _weatherService;
 *
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 *
 *      URI apiURi = new URI("http://sever/path");
 *      WeatherService _weatherService = RestClientBuilder.newBuilder()
 *                 .baseUri(apiURi)
 *                 .build(WeatherService.class);
 *
 */
//@RegisterRestClient(baseUri = "https://api.openweathermap.org/data/2.5/weather")
@RegisterRestClient
public interface WeatherService {

    @GET
    OpenWeatherData findByCityName(
            @QueryParam("q") String cityName,
            @QueryParam("appid") String apiKey,
            @QueryParam("units") String units);

    @GET
    OpenWeatherData findByGpsLocation(
            @QueryParam("lat") double latitude,
            @QueryParam("lon") double longitude,
            @QueryParam("appid") String apiKey,
            @QueryParam("units") String units);

}
