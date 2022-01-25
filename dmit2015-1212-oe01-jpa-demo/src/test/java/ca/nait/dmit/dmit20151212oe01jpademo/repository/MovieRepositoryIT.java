package ca.nait.dmit.dmit20151212oe01jpademo.repository;

import ca.nait.dmit.dmit20151212oe01jpademo.entity.Movie;
import common.config.ApplicationConfig;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // Allows use to specify the order of the test using @Order annotation
@TestInstance(TestInstance.Lifecycle.PER_CLASS)         // Allows us to share data between test methods
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
class MovieRepositoryIT {

    @Inject
    private MovieRepository _movieRepository;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
//                .addAsLibraries(pomFile.resolve("groupId:artifactId:version").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.210").withTransitivity().asFile())
//                 .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:9.4.1.jre11").withTransitivity().asFile())
                 .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.4.0.0.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core-jakarta:5.6.4.Final").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-spatial:5.6.4.Final").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hibernate.validator:hibernate-validator:7.0.2.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(Movie.class, MovieRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Order(1)
    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldCreate() {
        Movie newMovie = new Movie();
        newMovie.setGenre("Horror");
        newMovie.setPrice(BigDecimal.valueOf(19.99));
        newMovie.setRating("NC-17");
        newMovie.setTitle("The Return of the Java Master");
        newMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
        _movieRepository.add(newMovie);

        Optional<Movie> optionalMovie = _movieRepository.findById(newMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals(newMovie.getTitle(), existingMovie.getTitle());
        assertEquals(newMovie.getGenre(), existingMovie.getGenre());
        assertEquals(newMovie.getPrice(), existingMovie.getPrice());
        assertEquals(newMovie.getRating(), existingMovie.getRating());
        assertEquals(newMovie.getReleaseDate(), existingMovie.getReleaseDate());
    }

    @Order(2)
    @Test
    void shouldFindOne() {
        final Long movieId = 3L;  // for Ghostbusters 2
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals("Ghostbusters 2", existingMovie.getTitle());
        assertEquals("Comedy", existingMovie.getGenre());
        assertEquals(9.99, existingMovie.getPrice().doubleValue());
        assertEquals("PG", existingMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1986-2-23", formatter).toString(), existingMovie.getReleaseDate().toString());

    }

    @Order(3)
    @Test
    void shouldFindAll() {
        List<Movie> queryResultList = _movieRepository.findAll();
        assertEquals(4, queryResultList.size());

        Movie firstMovie = queryResultList.get(0);
        assertEquals("When Harry Met Sally", firstMovie.getTitle());
        assertEquals("Romantic Comedy", firstMovie.getGenre());
        assertEquals(7.99, firstMovie.getPrice().doubleValue());
        assertEquals("G", firstMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1989-02-12", formatter).toString(), firstMovie.getReleaseDate().toString());

        Movie lastMovie = queryResultList.get(queryResultList.size() - 1);
        assertEquals("Rio Bravo", lastMovie.getTitle());
        assertEquals("Western", lastMovie.getGenre());
        assertEquals(7.99, lastMovie.getPrice().doubleValue());
        assertEquals("PG-13", lastMovie.getRating());
        assertEquals(LocalDate.parse("1959-04-15", formatter).toString(), lastMovie.getReleaseDate().toString());

        queryResultList.forEach(System.out::println);
    }

    @Order(4)
    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldUpdate() {
        final Long movieId = 3L;  // for Ghostbusters 2
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        existingMovie.setTitle("Ghostbusters 2016");
        existingMovie.setPrice(BigDecimal.valueOf(16.99));
        existingMovie.setReleaseDate(LocalDate.parse("2016-07-15"));
        _movieRepository.update(existingMovie);

        Optional<Movie> optionalUpdatedMovie = _movieRepository.findById(movieId);
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.get();
        assertNotNull(updatedMovie);
        assertEquals(existingMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(existingMovie.getPrice(), updatedMovie.getPrice());
        assertEquals(existingMovie.getReleaseDate(), updatedMovie.getReleaseDate());
        assertEquals(existingMovie.getGenre(), updatedMovie.getGenre());
    }

    @Order(5)
    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldDelete() {
        final Long movieId = 3L;  // for Ghostbusters 2
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        _movieRepository.remove(existingMovie.getId());
        optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isEmpty());
    }

}