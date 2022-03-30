package dmit2015.view;

import dmit2015.restclient.Movie;
import dmit2015.restclient.MovieService;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
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

    @Getter
    private Map<String, Movie> movieMap;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            movieMap = _movieService.list();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}