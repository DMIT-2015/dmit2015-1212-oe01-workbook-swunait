package dmit2015.repository;

import common.config.ApplicationConfig;

import dmit2015.entity.TodoItem;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4

class TodoItemRepositoryIT {

    @Inject
    private TodoItemRepository _todoRepository;

    static TodoItem currentTodoItem;  // the TodoItem that is currently being added, find, update, or delete

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
//                .addAsLibraries(pomFile.resolve("groupId:artifactId:version").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.h2database:h2:1.4.200").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:9.4.1.jre11").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.4.0.0.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(TodoItem.class, TodoItemRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Order(2)
    @Test
    void shouldCreate() {
        currentTodoItem = new TodoItem();
        currentTodoItem.setName("Create Arquillian IT");
        currentTodoItem.setComplete(true);
        _todoRepository.create(currentTodoItem);

        Optional<TodoItem> optionalTodoItem = _todoRepository.findOptional(currentTodoItem.getId());
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertNotNull(existingTodoItem);
        assertEquals(currentTodoItem.getName(), existingTodoItem.getName());
        assertEquals(currentTodoItem.isComplete(), existingTodoItem.isComplete());

    }

    @Order(3)
    @Test
    void shouldFindOne() {
        final Long todoId = currentTodoItem.getId();
        Optional<TodoItem> optionalTodoItem = _todoRepository.findOptional(todoId);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertNotNull(existingTodoItem);
        assertEquals(currentTodoItem.getName(), existingTodoItem.getName());
        assertEquals(currentTodoItem.isComplete(), existingTodoItem.isComplete());

    }

    @Order(1)
    @Test
    void shouldFindAll() {
        List<TodoItem> queryResultList = _todoRepository.list();
        assertEquals(3, queryResultList.size());

        TodoItem firstTodoItem = queryResultList.get(0);
        assertEquals("Todo 1", firstTodoItem.getName());
        assertEquals(false, firstTodoItem.isComplete());

        TodoItem lastTodoItem = queryResultList.get(queryResultList.size() - 1);
        assertEquals("Todo 3", lastTodoItem.getName());
        assertEquals(false, lastTodoItem.isComplete());
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        currentTodoItem.setName("Update JPA Arquillian IT");
        currentTodoItem.setComplete(false);
        _todoRepository.update(currentTodoItem);

        Optional<TodoItem> optionalUpdatedTodoItem = _todoRepository.findOptional(currentTodoItem.getId());
        assertTrue(optionalUpdatedTodoItem.isPresent());
        TodoItem updatedTodoItem = optionalUpdatedTodoItem.get();
        assertNotNull(updatedTodoItem);
        assertEquals(currentTodoItem.getName(), updatedTodoItem.getName());
        assertEquals(currentTodoItem.isComplete(), updatedTodoItem.isComplete());

    }

    @Order(5)
    @Test
    void shouldDelete() {
        final Long todoId = currentTodoItem.getId();
        Optional<TodoItem> optionalTodoItem = _todoRepository.findOptional(todoId);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertNotNull(existingTodoItem);
        _todoRepository.remove(existingTodoItem.getId());
        optionalTodoItem = _todoRepository.findOptional(todoId);
        assertTrue(optionalTodoItem.isEmpty());
    }
}

