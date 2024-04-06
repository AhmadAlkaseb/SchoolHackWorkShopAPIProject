package rest.routes;

import daos.RegistrationDAO;
import daos.UserDAO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.Registration;
import persistence.model.Role;
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

    RegistrationDAO dao = RegistrationDAO.getInstance(emf);


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
                .setExceptionHandlers()
                .setRoute(registrationRoutes.registrationRoutes())
                .setRoute(AuthenticationRoutes.getAuthRoutes())
                .setRoute(AuthenticationRoutes.authBefore())
                .checkSecurityRoles();


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

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Registration ").executeUpdate();
            em.createQuery("DELETE FROM Event ").executeUpdate();
            em.createQuery("DELETE FROM User ").executeUpdate();
            em.getTransaction().commit();
        }

        UserDAO dao = UserDAO.getInstance(emf);

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

        Role admin = new Role("admin");

        user1.addEvent(event1);
        user1.addRole(admin);

        user2.addEvent(event2);
        user3.addEvent(event3);
        user4.addEvent(event4);
        user5.addEvent(event5);

        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        dao.create(user4);
        dao.create(user5);
    }

    @AfterEach
    void tearDownAfterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Registration ").executeUpdate();
            em.createQuery("DELETE FROM Event ").executeUpdate();
            em.createQuery("DELETE FROM User ").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Retrieval of all registrations")
    public void test1() {



        int expectedSize = 5; //der er 8 registreringer i beforeAll
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getDescription();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations")
                .then().log().all()
                .statusCode(200)
                .body("[0].userId", equalTo(expectedUserId))
                .body("[0].eventName", equalTo(expectedEventName))
                .body("size()", is(expectedSize))
                .extract().response().prettyPrint();
    }

    @Test
    @DisplayName("Retrieval of all registrations by specific event")
    public void test2() {

        int expectedSize = 1; //der er 8 registreringer i beforeAll
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getDescription();


        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations/all-by-eventid/{event-id}", event1.getId())
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
        String expectedEventName = event1.getDescription();
        Registration reg = dao.getRegistrationByNameAndEvent(user1.getId(), event1.getId());
        User user = user1;
        Event event = event1;
        double eventDuration = event.getDuration();

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/registrations/{id}", reg.getId())
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(reg.getId()))
                .body("id", equalTo(reg.getId()))
                .body("event.id", equalTo(event.getId()))
                .body("event.title", equalTo(event.getTitle()))
                .body("event.category", equalTo(event.getCategory().toString()))
                .body("event.description", equalTo(event.getDescription()))
                .body("event.duration", equalTo((float) event.getDuration())) //caster som float da den aktuelle fra databasen er float. i java koden er intitetens variabel double
                .body("event.capacity", equalTo(event.getCapacity()))
                .body("event.location", equalTo(event.getLocation()))
                .body("event.instructor", equalTo(event.getInstructor()))
                .body("user.id", equalTo(user.getId()))
                .body("user.name", equalTo(user.getName()))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.phone", equalTo(user.getPhone()))
                .extract().response().prettyPrint();
    }
}