package daos;

import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.Registration;
import persistence.model.Role;
import persistence.model.User;
import rest.config.ApplicationConfig;
import rest.routes.RegistrationRoutes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDAOTest {
    private static EntityManagerFactory emf;
    private static ApplicationConfig app;
    private static RegistrationDAO registrationDAO;
    private static int port = 7007;
    private static RegistrationRoutes registrationRoutes = new RegistrationRoutes(emf);
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

        registrationDAO = new RegistrationDAO(emf,Registration.class);

        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
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
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            em.createQuery("DELETE FROM Registration ").executeUpdate();
//            em.createQuery("DELETE FROM Event ").executeUpdate();
//            em.createQuery("DELETE FROM User ").executeUpdate();
//            em.getTransaction().commit();
//        }
    }


    @Test
    void getInstance() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    void readAllByEvent() {



    }

    @Test
    void readAll() {
        //given
        int expectedSize = 5;
        //when
        List<Registration> registrationList = registrationDAO.readAll();
        //then
        assertEquals(expectedSize, registrationList.size() );
    }

    @Test
    void getRegistrationByNameAndEvent() {
    }
}
