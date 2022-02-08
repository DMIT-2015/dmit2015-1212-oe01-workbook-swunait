package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }

}
    