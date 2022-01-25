package ca.nait.dmit.dmit20151212oe01jpademo.repository;

import java.util.List;
import java.util.Optional;

import ca.nait.dmit.dmit20151212oe01jpademo.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
@Transactional
public class MovieRepository {

    @PersistenceContext(unitName = "oracle-jpa-pu")
    private EntityManager em;

    public void add(Movie newMovie) {
        em.persist(newMovie);
    }

    public void update(Movie updatedMovie) {
        Optional<Movie> optionalMovie = findById(updatedMovie.getId());
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setPrice(updatedMovie.getPrice());
            existingMovie.setRating(updatedMovie.getRating());
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            em.merge(existingMovie);
            em.flush();
        }
    }

    public void remove(Long movieID) {
        Optional<Movie> optionalMovie = findById(movieID);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            em.remove(existingMovie);
            em.flush();
        }
    }

    public Optional<Movie> findById(Long movieID) {
        Optional<Movie> optionalMovie = Optional.empty();
        try {
            Movie querySingleResult = em.find(Movie.class, movieID);
            if (querySingleResult != null) {
                optionalMovie = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalMovie;
    }

    public List<Movie> findAll() {
        return em.createQuery(
                "SELECT m FROM Movie m "
                , Movie.class)
                .getResultList();
    }

    public List<Movie> findAllOrderByTitle() {
        return em.createQuery(
                "SELECT m FROM Movie m ORDER BY m.title"
                , Movie.class)
                .getResultList();
    }
}