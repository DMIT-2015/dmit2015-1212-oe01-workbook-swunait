package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;

import java.util.ArrayList;
import java.util.List;

//@ApplicationScoped
//@Transactional
//@Interceptors(MovieSecurityInterceptor.class)
@Stateless
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }

    @Inject
    private SecurityContext _securityContext;

    @RolesAllowed("Sales")
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

