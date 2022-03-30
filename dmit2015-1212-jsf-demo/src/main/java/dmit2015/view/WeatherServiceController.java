package dmit2015.view;

import dmit2015.model.OpenWeatherData;
import dmit2015.model.WeatherService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

@Named
public class WeatherServiceController {

    @Inject
    @RestClient
    private WeatherService _weatherService;

    @Inject
//    @ConfigProperty(name="api.openweathermap.org.ApiKey")
    @ConfigProperty(name="OPENWEATHERMAP_APIKEY")   // read value from Environment variable
    // You can export a environment variable in linux:
    // export OPENWEATHERMAP_APIKEY=yourOwnApiKey
    private String _weatherApiKey;

    @Getter
    private OpenWeatherData weatherData;

    @PostConstruct
    void init() {
        try {
            weatherData = _weatherService.findByCityName(
                    "Edmonton", _weatherApiKey, "metric");

        } catch (Exception ex) {
            ex.printStackTrace();
            Messages.addGlobalError("Error fetching weather data with exception {0}", ex.getMessage());
        }
    }
}
