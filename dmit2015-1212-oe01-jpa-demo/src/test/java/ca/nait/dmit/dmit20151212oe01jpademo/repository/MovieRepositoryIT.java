package ca.nait.dmit.dmit20151212oe01jpademo.repository;

import ca.nait.dmit.dmit20151212oe01jpademo.entity.Movie;
import common.config.ApplicationConfig;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
public class MovieRepositoryIT {

    @Inject
    private MovieRepository _movieRepository;

    static Movie currentMovie;  // the Movie that is currently being added, find, update, or delete

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
//                .addAsLibraries(pomFile.resolve("groupId:artifactId:version").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.210").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:8.4.1.jre11").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.1.0.0").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core-jakarta:5.6.5.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(Movie.class, MovieRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Order(2)
    @Test
    void shouldCreate() {
        currentMovie = new Movie();
        currentMovie.setGenre("Horror");
        currentMovie.setPrice(BigDecimal.valueOf(19.99));
        currentMovie.setRating("NC-17");
        currentMovie.setTitle("The Return of the Java Master");
        currentMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
        _movieRepository.add(currentMovie);

        Optional<Movie> optionalMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals(currentMovie.getTitle(), existingMovie.getTitle());
        assertEquals(currentMovie.getGenre(), existingMovie.getGenre());
        assertEquals(currentMovie.getPrice(), existingMovie.getPrice());
        assertEquals(currentMovie.getRating(), existingMovie.getRating());
        assertEquals(currentMovie.getReleaseDate(), existingMovie.getReleaseDate());
    }

    @Order(3)
    @Test
    void shouldFindOne() {
        final Long movieId = currentMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals(currentMovie.getTitle(), existingMovie.getTitle());
        assertEquals(currentMovie.getGenre(), existingMovie.getGenre());
        assertEquals(currentMovie.getPrice().doubleValue(), existingMovie.getPrice().doubleValue());
        assertEquals(currentMovie.getRating(), existingMovie.getRating());
        assertEquals(currentMovie.getReleaseDate(), existingMovie.getReleaseDate());
        long createdDateTimeDifference = currentMovie.getCreatedDateTime().until(existingMovie.getCreatedDateTime(), ChronoUnit.MINUTES);
        assertEquals(0, createdDateTimeDifference);
        long lastModifedDateTimeDifferce = currentMovie.getLastModifiedDateTime().until(existingMovie.getLastModifiedDateTime(), ChronoUnit.MINUTES);
        assertEquals(0, lastModifedDateTimeDifferce);
    }

    @Order(1)
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
    @Test
    void shouldUpdate() {
        currentMovie.setGenre("Comedy");
        currentMovie.setTitle("JDK 16 Release ");
        currentMovie.setRating("PG-13");
        currentMovie.setPrice(BigDecimal.valueOf(16.99));
        currentMovie.setReleaseDate(LocalDate.parse("2021-03-16"));
        _movieRepository.update(currentMovie);

        Optional<Movie> optionalUpdatedMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.get();
        assertNotNull(updatedMovie);
        assertEquals(currentMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(currentMovie.getGenre(), updatedMovie.getGenre());
        assertEquals(currentMovie.getPrice().doubleValue(), updatedMovie.getPrice().doubleValue());
        assertEquals(currentMovie.getRating(), updatedMovie.getRating());
        assertEquals(currentMovie.getReleaseDate(), updatedMovie.getReleaseDate());
        long createdDateTimeDifference = currentMovie.getCreatedDateTime().until(updatedMovie.getCreatedDateTime(), ChronoUnit.MINUTES);
        assertEquals(0, createdDateTimeDifference);
        long lastModifedDateTimeDifferce = currentMovie.getLastModifiedDateTime().until(updatedMovie.getLastModifiedDateTime(), ChronoUnit.MINUTES);
        assertEquals(0, lastModifedDateTimeDifferce);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        final Long movieId = currentMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        _movieRepository.remove(existingMovie.getId());
        optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isEmpty());
    }
}