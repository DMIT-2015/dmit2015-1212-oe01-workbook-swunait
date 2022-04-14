package dmit2015.view;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.omnifaces.util.Messages;

import java.io.Serializable;

@Named("currentMovieCreateController")
@ViewScoped
public class MovieCreateController implements Serializable {

    @Inject
    private MovieRepository _movieRepository;

    @Getter
    private Movie newMovie = new Movie();

    public String onCreateNew() {
        String nextPage = "";
        try {
            _movieRepository.create(newMovie);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}