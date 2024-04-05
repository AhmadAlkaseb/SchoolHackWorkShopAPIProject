package rest.routes;

import dtos.EventDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
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
                .then()
                .assertThat()
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

                // Husk at sætte datoen for i dag
                .body("createdAt", contains(2024, 4, 5))
                .body("updatedAt", contains(2024, 4, 5))
                .body("deletedAt", nullValue());
    }

    @Test
    @DisplayName("Retrieving specific events by category.")
    public void testingSpecificEventsByCategory() {
        int expectedRoomsSize = 1;
        List<EventDTO> listFound = RestAssured
                .given()
                .when()
                .get("/events/categories/{category}", "course")
                .then()
                .extract()
                .body()
                .jsonPath().getList("", EventDTO.class);
        assertFalse(listFound.isEmpty());
        assertEquals(expectedRoomsSize, listFound.size());
    }

    @Test
    @DisplayName("Retrieving specific events by status.")
    public void testingSpecificEventsByStatus() {
        int expectedRoomsSize = 1;
        List<EventDTO> listFound = RestAssured
                .given()
                .when()
                .get("/events/status/{status}", "active")
                .then()
                .extract()
                .body()
                .jsonPath().getList("", EventDTO.class);
        assertFalse(listFound.isEmpty());
        assertEquals(expectedRoomsSize, listFound.size());
    }

    @Test
    @DisplayName("Add a new event.")
    public void testingAddNewEvent() {

        String requestBody = "{\n" +
                "  \"title\": \"Introduction to Python Programming\",\n" +
                "  \"category\": \"workshop\",\n" +
                "  \"description\": \"Learn Python basics and fundamentals\",\n" +
                "  \"date\": \"2024-05-20\",\n" +
                "  \"time\": \"09:00\",\n" +
                "  \"duration\": 4,\n" +
                "  \"capacity\": 50,\n" +
                "  \"location\": \"Conference Room B\",\n" +
                "  \"instructor\": \"Alice Smith\",\n" +
                "  \"price\": 29.99,\n" +
                "  \"image\": \"python_workshop.jpg\",\n" +
                "  \"status\": \"active\"\n" +
                "}";

        EventDTO eventDTOFound =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(200) // Ensure the status code is 200 (OK)
                        .body("title", equalTo("Introduction to Python Programming"))
                        .body("category", equalTo("workshop"))
                        .body("description", equalTo("Learn Python basics and fundamentals"))
                        .body("date", contains(2024, 5, 20))
                        .body("time", contains(9, 0))
                        .body("duration", equalTo(4.0f))
                        .body("capacity", equalTo(50))
                        .body("location", equalTo("Conference Room B"))
                        .body("instructor", equalTo("Alice Smith"))
                        .body("price", equalTo(29.99f))
                        .body("image", equalTo("python_workshop.jpg"))
                        .body("status", equalTo("active"))
                        .extract()
                        .response()
                        .as(EventDTO.class);
        assertNotNull(eventDTOFound);
    }

    @Test
    @DisplayName("Update existing event by id")
    public void testingUpdateExistingEvent() {
        EventDTO eventDTOFound =
                RestAssured
                        .given()
                        .when()
                        .delete("/events/{id}", 1)
                        .then()
                        .assertThat()
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

                        // Husk at sætte datoen for i dag
                        .body("createdAt", contains(2024, 4, 5))
                        .body("updatedAt", contains(2024, 4, 5))
                        .extract()
                        .response()
                        .as(EventDTO.class);
        assertNotNull(eventDTOFound);
    }
}