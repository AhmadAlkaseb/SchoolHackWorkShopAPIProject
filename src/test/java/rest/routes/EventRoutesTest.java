package rest.routes;

import daos.UserDAO;
import dtos.EventDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.User;
import rest.config.ApplicationConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class EventRoutesTest {
    private static EntityManagerFactory emf;
    private static EventRoutes eventRoutes;
    private static ApplicationConfig applicationConfig;
    private static String userToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsQGxhaHkuZGsiLCJwaG9uZSI6NTM3MDI1MTAsInJvbGVzIjoic3R1ZGVudCIsImlzcyI6IlRlYW1MQUhZIiwibmFtZSI6InRlc3QiLCJpZCI6MTUwLCJleHAiOjE3MTIyNTE3OTgsImVtYWlsIjoibEBsYWh5LmRrIn0.qEA5-lUuGxslfO7_ZMpdXJT9iOCgeF7p9FweXfHa3W4";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:7007/api";
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        eventRoutes = new EventRoutes(emf);
        applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(eventRoutes.eventRoutes())
                .setRoute(AuthenticationRoutes.getAuthRoutes())
                .setRoute(AuthenticationRoutes.authBefore())
                .checkSecurityRoles();
    }

    @AfterAll
    public static void closeDown() {
        emf.close();
    }

    @BeforeEach
    public void upload() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new Event("Yoga Class",
                    Event.Category.course,
                    "Join us for a relaxing yoga session",
                    LocalDate.of(2024, 4, 10),
                    LocalTime.of(9, 0), 1.5, 20,
                    "Community Center", "Yoga Instructor Name",
                    10.99, "yoga_image.jpg",
                    Event.Status.active));
            em.getTransaction().commit();
        }
    }

    @AfterEach
    public void clear() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Event").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Testing that entity manager factory is not null")
    public void testingEntityManagerFactory() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    @DisplayName("Testing that eventRoutes is not null")
    public void testingEventRoutes() {
        // Given

        // When

        // Then
        assertNotNull(eventRoutes);
    }

    @Test
    @DisplayName("Testing that javalin is not null")
    public void testingJavalin() {
        // Given

        // When

        // Then
        assertNotNull(applicationConfig);
    }

    @Test
    @DisplayName("Retrieving all events.")
    public void testRetrievingAllEvents() {
        int expectedRoomsSize = 1;

        List<EventDTO> listFound = RestAssured
                .given()
                .when()
                .get("/events")
                .then()
                .extract()
                .body()
                .jsonPath().getList("", EventDTO.class);

        assertFalse(listFound.isEmpty());
        assertEquals(expectedRoomsSize, listFound.size());
    }

    @Test
    @DisplayName("Retrieving specific event by id.")
    public void testingSpecificEvent() {
        RestAssured
                .given()
                .when()
                .get("/events/{id}", 1)
                .then().assertThat()
                .body("id", equalTo(1))
                .body("title", equalTo("Yoga Class"))
                .body("category", equalTo("course"))
                .body("description", equalTo("Join us for a relaxing yoga session"))
                .body("date", contains(2024, 4, 10))
                .body("time", contains(9, 0))
                .body("duration", equalTo(1.5f))
                .body("capacity", equalTo(20))
                .body("location", equalTo("Community Center"))
                .body("instructor", equalTo("Yoga Instructor Name"))
                .body("price", equalTo(10.99f))
                .body("image", equalTo("yoga_image.jpg"))
                .body("status", equalTo("active"))
                .body("createdAt", contains(2024, 4, 4))
                .body("updatedAt", contains(2024, 4, 4))
                .body("deletedAt", nullValue());
    }
}