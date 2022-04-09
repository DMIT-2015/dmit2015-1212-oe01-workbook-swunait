package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import dmit2015.security.LoggingInterceptor;
import dmit2015.security.MovieSecurityInterceptor;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
@Interceptors(MovieSecurityInterceptor.class)
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }

    @Inject
    private SecurityContext _securityContext;

    public void create(Movie newMovie) {
        String username = _securityContext.getCallerPrincipal().getName();
        newMovie.setUsername(username);
        super.create(newMovie);
    }

    public void delete(Movie existingMovie) {
        super.remove(existingMovie);
    }

    public List<Movie> list() {
        List<Movie> resultList = new ArrayList<>();
        if (_securityContext.getCallerPrincipal() == null ) {
            resultList = super.list();
        } else {
            String username = _securityContext.getCallerPrincipal().getName();
            resultList = getEntityManager().createQuery(
                            "SELECT m FROM Movie m WHERE m.username = :usernameValue ", Movie.class)
                    .setParameter("usernameValue", username).getResultList();
        }
        return resultList;
    }
}

