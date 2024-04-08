package rest.routes;

import daos.RegistrationDAO;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RegistrationRoutesTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
    private static ApplicationConfig app;
    private static int port = 7007;
    private static RegistrationRoutes registrationRoutes = new RegistrationRoutes(emf);
    private RegistrationDAO registrationDAO = RegistrationDAO.getInstance(emf);
    private static User user1;
    private static Event event1;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:7007/api/registrations";
        app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(port)
                .setExceptionHandlers()
                .setRoute(registrationRoutes.registrationRoutes());
        //.checkSecurityRoles();
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
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(admin);
            em.persist(instructor);
            em.persist(student);
            em.getTransaction().commit();
        }

        user1 = new User("Hans", "hans@mail.com", "password", 12345678);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user1);
            em.getTransaction().commit();
        }

        event1 = new Event("Yoga for Beginners", Event.Category.workshop, "A relaxing introduction to yoga.", LocalDate.of(2024, 4, 3), LocalTime.of(18, 0), 1.5, 20, "Room 101", "John Doe", 10.0, "yoga.jpg", Event.Status.active);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(event1);
            em.getTransaction().commit();
        }


        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Event event = em.find(Event.class, 1);
            User user = em.find(User.class, 1);
            Registration registration = new Registration(1,event, user);
            em.persist(registration);
            em.getTransaction().commit();
        }
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
    public void test0() {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new Registration(em.find(Event.class, 1), em.find(User.class, 1)));
            em.getTransaction().commit();
        }
        /*int userid = 1;
        String response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userid)
                .when()
                .post("/add_user_to_event/{eventid}", 1)
                .then()
                .assertThat()
                .extract()
                .asString();

        System.out.println("Response: " + response);*/
    }

    @DisplayName("Retrieval of all registrations")
    public void test1() {
        int expectedSize = 5;
        int expectedUserId = user1.getId();
        String expectedEventName = event1.getTitle();

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