package dmit2015.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import dmit2015.entity.TodoItem;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://github.com/rest-assured/rest-assured
 * https://github.com/rest-assured/rest-assured/wiki/Usage
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/restassured-tutorial
 * https://eclipse-ee4j.github.io/jsonb-api/docs/user-guide.html
 * https://github.com/FasterXML/jackson-databind
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoItemResource2RestAssuredIT {

    String todoResourceUrl = "http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/TodoItems";
    String testDataResourceLocation;

    @Order(1)
    @Test
    void shouldListAll() throws JsonProcessingException {
        Response response = given()
//                .accept(ContentType.JSON)
                .when()
                .get(todoResourceUrl)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        List<TodoItem> todos = jsonb.fromJson(jsonBody, new ArrayList<TodoItem>(){}.getClass().getGenericSuperclass());

        assertEquals(3, todos.size());
        TodoItem firstTodoItem = todos.get(0);
        assertEquals("Todo 1", firstTodoItem.getName());
        assertFalse(firstTodoItem.isComplete());

        TodoItem lastTodoItem = todos.get(todos.size() - 1);
        assertEquals("Todo 3", lastTodoItem.getName());
        assertFalse(lastTodoItem.isComplete());

    }

    @Order(2)
    @Test
    void shouldCreate() throws JsonProcessingException {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("Create REST Assured Integration Test");
        newTodoItem.setComplete(false);

        Jsonb jsonb = JsonbBuilder.create();
        String jsonBody = jsonb.toJson(newTodoItem);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(todoResourceUrl)
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @Test
    void shouldFineOne() throws JsonProcessingException {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        assertNotNull(existingTodoItem);
        assertEquals("Create REST Assured Integration Test", existingTodoItem.getName());
        assertFalse(existingTodoItem.isComplete());
    }

    @Order(4)
    @Test
    void shouldUpdate() throws JsonProcessingException {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        assertNotNull(existingTodoItem);
        existingTodoItem.setName("Updated Name");
        existingTodoItem.setComplete(true);

        String jsonRequestBody = jsonb.toJson(existingTodoItem);
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(200);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

}

