package daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EventDAOTest {

    private static EntityManagerFactory emf;
    private static EventDAO eventDAO;

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        eventDAO = EventDAO.getInstance(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {
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
    public void afterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Event").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Testing that entity manager factory is not null.")
    public void testingEntityManagerFactory() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    @DisplayName("Testing that eventDAO is not null.")
    public void test() {
        // Given

        // When

        // Then
        assertNotNull(eventDAO);
    }

    @Test
    @DisplayName("Creating an event.")
    public void createEvent() {
        // Given
        Event expectedEvent = new Event("Yoga Class",
                Event.Category.course,
                "Join us for a relaxing yoga session",
                LocalDate.of(2024, 4, 10),
                LocalTime.of(9, 0), 1.5, 20,
                "Community Center", "Yoga Instructor Name",
                10.99, "yoga_image.jpg",
                Event.Status.active);

        // When
        Event createdEvent = (Event) eventDAO.create(new Event("Yoga Class",
                Event.Category.course,
                "Join us for a relaxing yoga session",
                LocalDate.of(2024, 4, 10),
                LocalTime.of(9, 0), 1.5, 20,
                "Community Center", "Yoga Instructor Name",
                10.99, "yoga_image.jpg",
                Event.Status.active));

        // Then
        assertNotNull(createdEvent);
        assertEquals(expectedEvent.getTitle(), createdEvent.getTitle());
        assertEquals(expectedEvent.getCategory(), createdEvent.getCategory());
        assertEquals(expectedEvent.getDescription(), createdEvent.getDescription());
        assertEquals(expectedEvent.getDate(), createdEvent.getDate());
        assertEquals(expectedEvent.getTime(), createdEvent.getTime());
        assertEquals(expectedEvent.getLocation(), createdEvent.getLocation());
        assertEquals(expectedEvent.getInstructor(), createdEvent.getInstructor());
        assertEquals(expectedEvent.getPrice(), createdEvent.getPrice());
        assertEquals(expectedEvent.getImage(), createdEvent.getImage());
    }

    @Test
    @DisplayName("Reading all events.")
    public void readAllEvents() {
        // Given
        int expectedSize = 1;

        // When
        List<Event> events = eventDAO.readAll();

        // Then
        assertFalse(events.isEmpty());
        assertEquals(expectedSize, events.size());
    }

    @Test
    @DisplayName("Reading an specific event by id")
    public void readEventById() {
        // Given
        int expectedId = 1;

        // When
        Event event = (Event) eventDAO.getById(1);

        // Then
        assertNotNull(event);
        assertEquals(expectedId, event.getId());
    }

    @Test
    @DisplayName("Updating existing event.")
    public void updateEvent() {
        // Given
        Event expectedEvent;
        try (EntityManager em = emf.createEntityManager()) {
            expectedEvent = em.find(Event.class, 1);
        }
        String newTitle = "Yoga Class";
        expectedEvent.setTitle(newTitle);

        // When
        int updatedResult = eventDAO.update(expectedEvent);

        // Then
        assertTrue(updatedResult > 0);
    }

    @Test
    @DisplayName("Deleting specific event by id")
    public void deleteEventById() {
        // Given
        int expectedResult = 1;

        // When
        int deletedResult = eventDAO.delete(1);

        // Then
        assertTrue(deletedResult > 0);
        assertEquals(expectedResult, deletedResult);
    }
}