package rest.routes;

import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.User;
import rest.config.ApplicationConfig;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

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

        user1 = new User("Hans", "hans@mail.com", "password", 12345678, User.Role.admin);
        user2 = new User("Martin", "martin@mail.com", "password", 12345678, User.Role.instructor);
        user3 = new User("Tom", "tom@mail.com", "password", 12345678, User.Role.student);
        user4 = new User("Louise", "louise@mail.com", "password", 12345678, User.Role.student);
        user5 = new User("Helle", "helle@mail.com", "password", 12345678, User.Role.student);

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

        user1.addEvent(event2);
        user3.addEvent(event4);
        user5.addEvent(event1);
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

            // Antager at der er en metode til at slette data fra specifikke tabeller
            em.createQuery("DELETE FROM Registration").executeUpdate();
            em.createQuery("DELETE FROM Event").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Retrieval of all registrations method")
    public void test1(){

    }

}