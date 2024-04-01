package application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.EventUser;
import persistence.model.User;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

        User user1 = new User("Test", "test", "test", 123);
        User user2 = new User("Test", "test", "test", 123);
        Event event = new Event("test", "test", LocalDate.of(1998, 3, 2), ZonedDateTime.now(), 3, 3, "test", "test", 30.03, "test");

        user1.addEvent(event);
        user2.addEvent(event);


        // Lav en bruger
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery()


            em.persist(user2);
            em.getTransaction().commit();
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
