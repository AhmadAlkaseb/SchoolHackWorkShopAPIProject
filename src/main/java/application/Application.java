package application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.User;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

        User user1 = new User("Hans", "test", "test", 123);
        User user2 = new User("Tom", "test", "test", 123);
        User user3 = new User("lasse", "sjdjd", "asdsad", 1212);
        Event event = new Event("DAT1", "DAT1", LocalDate.of(1998, 3, 2), ZonedDateTime.now(), 3, 3, "test", "test", 30.03, "test");

        user1.addEvent(event);
        user2.addEvent(event);

        // Lav en bruger
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
//            Event event1 = em.find(Event.class, 1);
//            user3.addEvent(event1);
            em.persist(user3);
            em.persist(user2);
            em.persist(user1);
            em.persist(event);
            em.getTransaction().commit();
        }


        // hent data fra database
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT e FROM Event e JOIN e.users u where e.id=1", Event.class);
            List<Event> events = query.getResultList();
            events.stream() // Stream<Event>
                    .flatMap(e -> e.getUsers().stream()) // Stream<User>
                    .distinct() // Fjerner duplikater, hvis samme bruger er tilknyttet flere events
                    .forEach(System.out::println); // Udskriver hver bruger
        }

        // Lav et event
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            em.persist(event);
//            em.getTransaction().commit();
//        }

        // Lav et event_user
//        try (EntityManager em = emf.createEntityManager()) {
            /*em.getTransaction().begin();
            EventUser eventUser = new EventUser(event, user1);
            em.persist(eventUser);
            em.getTransaction().commit();*/
//        }
    }
}
