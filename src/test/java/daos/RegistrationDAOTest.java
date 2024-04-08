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
    private static RegistrationDAO registrationDAO;
    private static UserDAO userDAO;
    private static EventDAO eventDAO;

    private static Registration registration1;
    private static Registration foundRegistration;
    private static User user1;
    private static User user2;

    private static Event event1;
    private static Event event2;



    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);

        registrationDAO = RegistrationDAO.getInstance(emf);
        userDAO = UserDAO.getInstance(emf);
        eventDAO = EventDAO.getInstance(emf);
    }

    @AfterAll
    static void tearDown() {
        emf.close();
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


        user1 = userDAO.create(new User("Hans", "hans@mail.com", "password", 12345678));
        event1 = (Event) eventDAO.create(new Event("Yoga for Beginners", Event.Category.workshop, "A relaxing introduction to yoga.", LocalDate.of(2024, 4, 3), LocalTime.of(18, 0), 1.5, 20, "Room 101", "John Doe", 10.0, "yoga.jpg", Event.Status.active));


//        registration1 = new Registration(event1,user1);
//
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            em.persist(registration1);
//            em.getTransaction().commit();
//        }
//
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            foundRegistration = em.find(Registration.class,1);
//            em.getTransaction().commit();
//        }

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
    void getInstance() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    void userId() {
        assertEquals(3,user1.getId());
    }

    @Test
    void eventId() {
        assertEquals(2,event1.getId());
    }

    @Test
    void readAll() {
        //given
        int expectedSize = 0;
        //when
        List<Registration> registrationList = registrationDAO.readAll();
        //then
        assertEquals(expectedSize, registrationList.size() );
    }
}
