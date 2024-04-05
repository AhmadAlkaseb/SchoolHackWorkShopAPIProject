package rest.routes;

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


import static org.hamcrest.Matchers.*;

class RegistrationRoutesTest {

    private static EntityManagerFactory emf;
    private static ApplicationConfig app;
    private static int port = 7007;
    private static RegistrationRoutes registrationRoutes = new RegistrationRoutes();


    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static User user5;

    private static Event event1;
    private static Event event2;
    private static Event event3;
    private static Event event4;
    private static Event event5;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:7007/api";

        emf = HibernateConfig.getEntityManagerFactoryConfig(true);

        app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(port)
                .setRoute(registrationRoutes.registrationRoutes());


//        user1.addEvent(event2);
//        user3.addEvent(event4);
//        user5.addEvent(event1);


    }

    @AfterAll
    static void tearDown() {
        emf.close();
        app.stopServer();
    }

    @BeforeEach
    void setUpBeforeEach() {

        user1 = new User("Hans", "hans@mail.com", "password", 12345678);
        user2 = new User("Martin", "martin@mail.com", "password", 12345678);
        user3 = new User("Tom", "tom@mail.com", "password", 12345678);
        user4 = new User("Louise", "louise@mail.com", "password", 12345678);
        user5 = new User("Helle", "helle@mail.com", "password", 12345678);

        event1 = new Event("Yoga for Beginners", Event.Category.workshop, "A relaxing introduction to yoga.", LocalDate.of(2024, 4, 3), LocalTime.of(18, 0), 1.5, 20, "Room 101", "John Doe", 10.0, "yoga.jpg", Event.Status.active);
        event2 = new Event("Advanced Pottery", Event.Category.course, "Sculpt and mold your way to mastery.", LocalDate.of(2024, 4, 4), LocalTime.of(17, 0), 2.0, 15, "Art Studio", "Jane Smith", 20.0, "pottery.jpg", Event.Status.active);
        event3 = new Event("Beginner's Guitar", Event.Category.talk, "Strum the strings with ease.", LocalDate.of(2024, 4, 5), LocalTime.of(19, 0), 1.0, 10, "Music Hall", "Alex Johnson", 15.0, "guitar.jpg", Event.Status.active);
        event4 = new Event("Digital Photography", Event.Category.workshop, "Capture the world in your lens.", LocalDate.of(2024, 4, 6), LocalTime.of(16, 0), 3.0, 25, "Photo Lab", "Maria Garcia", 30.0, "photography.jpg", Event.Status.active);
        event5 = new Event("Master Chef: Baking", Event.Category.course, "Bake like a pro.", LocalDate.of(2024, 4, 7), LocalTime.of(15, 0), 2.5, 30, "Kitchen", "Emily Davis", 25.0, "baking.jpg", Event.Status.active);

        user1.addEvent(event1);
        user2.addEvent(event2);
        user3.addEvent(event3);
        user4.addEvent(event4);
        user5.addEvent(event5);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            em.persist(user5);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDownAfterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Truncate tabeller - rigtig tabel navn skal benyttes - alt indhold slettes.
            em.createNativeQuery("TRUNCATE TABLE registrations CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE events CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE users CASCADE").executeUpdate();

            // Reset sequence
//            em.createNativeQuery("ALTER SEQUENCE registrations_id_seq RESTART WITH 1").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Retrieval of all registrations method")
    public void test1() {

        int expectedSize = 5; //der er 8 registreringer i beforeAll
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getDescription();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations")
                .then()
                .statusCode(200)
                .body("[0].userId", equalTo(expectedUserId))
                .body("[0].eventName", equalTo(expectedEventName))
                .body("size()", is(expectedSize))
                .extract().response().prettyPrint();
    }

    @Test
    @DisplayName("Retrieval of all registrations method")
    public void test2() {

        int expectedSize = 1; //der er 8 registreringer i beforeAll
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getDescription();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations/all-by-eventid/1")
                .then()
                .statusCode(200)
                .body("[0].userId", equalTo(expectedUserId))
                .body("[0].eventName", equalTo(expectedEventName))
                .body("size()", is(expectedSize))
                .extract().response().prettyPrint();
    }

    @Test
    @DisplayName("Retrieval of a single registration by event id and user id")
    public void test3() {

        int expectedSize = 5; //der er 5 registreringer i beforeAll
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getDescription();

        System.out.println(expectedUserId);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations/372")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().prettyPrint();

        System.out.println(expectedUserId);

    }
}