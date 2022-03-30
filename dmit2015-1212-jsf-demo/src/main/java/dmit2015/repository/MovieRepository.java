package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }

    public List<Movie> listByReleaseDateDescending() {
        return getEntityManager()
                .createQuery("""
                SELECT m
                FROM Movie m
                ORDER BY m.releaseDate DESC
                """, Movie.class)
                .getResultList();
    }

}

