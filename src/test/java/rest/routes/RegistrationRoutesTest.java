package rest.routes;

import daos.EventDAO;
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

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
    private static ApplicationConfig app;
    private static int port = 7007;
    private static RegistrationRoutes registrationRoutes = new RegistrationRoutes(emf);
    private UserDAO userDAO = UserDAO.getInstance(emf);
    private RegistrationDAO registrationDAO = RegistrationDAO.getInstance(emf);
    private EventDAO eventDAO = EventDAO.getInstance(emf);
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

    private static Registration registration1;
    private static Registration registration2;
    private static Registration registration3;
    private static Registration registration4;
    private static Registration registration5;


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:7007/api";

//        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(port)
                .setExceptionHandlers()
                .setRoute(registrationRoutes.registrationRoutes());
//                .setRoute(authenticationRoutes.getAuthRoutes())
//                .setRoute(authenticationRoutes.authBefore())
//                .checkSecurityRoles();

    }

    @AfterAll
    static void tearDown() {
        emf.close();
        app.stopServer();
    }

    @BeforeEach
    void setUpBeforeEach() {

        Role admin = new Role("admin");
        Role student = new Role("student");
        Role instructor = new Role("instructor");

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(admin);
            em.persist(instructor);
            em.persist(student);
            em.getTransaction().commit();
        }

//        user1 = userDAO.create(new User("Hans", "hans@mail.com", "password", 12345678));
//        user2 = userDAO.create(new User("Martin", "martin@mail.com", "password", 12345678));
//        user3 = userDAO.create(new User("Tom", "tom@mail.com", "password", 12345678));
//        user4 = userDAO.create(new User("Louise", "louise@mail.com", "password", 12345678));
//        user5 = userDAO.create(new User("Helle", "helle@mail.com", "password", 12345678));

        user1 = new User("Hans", "hans@mail.com", "password", 12345678);

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user1);
            em.getTransaction().commit();
        }

        event1 = (Event) eventDAO.create(new Event("Yoga for Beginners", Event.Category.workshop, "A relaxing introduction to yoga.", LocalDate.of(2024, 4, 3), LocalTime.of(18, 0), 1.5, 20, "Room 101", "John Doe", 10.0, "yoga.jpg", Event.Status.active));
//        event2 = (Event) eventDAO.create(new Event("Advanced Pottery", Event.Category.course, "Sculpt and mold your way to mastery.", LocalDate.of(2024, 4, 4), LocalTime.of(17, 0), 2.0, 15, "Art Studio", "Jane Smith", 20.0, "pottery.jpg", Event.Status.active));
//        event3 = (Event) eventDAO.create(new Event("Beginner's Guitar", Event.Category.talk, "Strum the strings with ease.", LocalDate.of(2024, 4, 5), LocalTime.of(19, 0), 1.0, 10, "Music Hall", "Alex Johnson", 15.0, "guitar.jpg", Event.Status.active));
//        event4 = (Event) eventDAO.create(new Event("Digital Photography", Event.Category.workshop, "Capture the world in your lens.", LocalDate.of(2024, 4, 6), LocalTime.of(16, 0), 3.0, 25, "Photo Lab", "Maria Garcia", 30.0, "photography.jpg", Event.Status.active));
//        event5 = (Event) eventDAO.create(new Event("Master Chef: Baking", Event.Category.course, "Bake like a pro.", LocalDate.of(2024, 4, 7), LocalTime.of(15, 0), 2.5, 30, "Kitchen", "Emily Davis", 25.0, "baking.jpg", Event.Status.active));

//        registration1 = (Registration) registrationDAO.create(new Registration(event1, user1));
//        registration2 = (Registration) registrationDAO.create(new Registration(event2, user2));
//        registration3 = (Registration) registrationDAO.create(new Registration(event3, user3));
//        registration4 = (Registration) registrationDAO.create(new Registration(event4, user4));
//        registration5 = (Registration) registrationDAO.create(new Registration(event5, user5));

    }

    @AfterEach
    void tearDownAfterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Registration ").executeUpdate();
            em.createQuery("DELETE FROM Event ").executeUpdate();
            em.createQuery("DELETE FROM User ").executeUpdate();
            em.createQuery("DELETE FROM Role ").executeUpdate();
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
        Registration reg = registrationDAO.getRegistrationByUserIdAndEventId(user1.getId(), event1.getId());
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