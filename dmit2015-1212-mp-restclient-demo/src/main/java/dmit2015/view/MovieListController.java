package dmit2015.view;

import dmit2015.restclient.GeoLocation;
import dmit2015.restclient.GeoLocationService;
import dmit2015.restclient.Movie;
import dmit2015.restclient.MovieService;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("currentMovieListController")
@ViewScoped
public class MovieListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private MovieService _movieService;

//    @Inject
//    @ConfigProperty(name="IPGEOLOCATION_API")
//    private String ipGeoLocationApiKey;
//
//    @Inject
//    @RestClient
//    private GeoLocationService _geoLocationService;
//
//    @Getter
//    private GeoLocation currentGeoLocation;

    @Getter
    private Map<String, Movie> movieMap;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
//            String remoteIP = Faces.getRemoteAddr();
//            if (remoteIP.equals("127.0.0.1")) {
//                // Call location API without IP address
//                currentGeoLocation = _geoLocationService.withoutIPAddress(ipGeoLocationApiKey);
//            } else {
//                // Call location API with IP address
//                currentGeoLocation = _geoLocationService.withIPAddress(ipGeoLocationApiKey,remoteIP);
//            }

            movieMap = _movieService.list();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}