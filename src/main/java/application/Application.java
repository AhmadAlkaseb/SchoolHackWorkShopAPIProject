package application;

import controllers.RegistrationController;
import daos.RegistrationDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.Registration;
import rest.config.ApplicationConfig;
import rest.routes.EventRoutes;
import rest.routes.requests.RegistrationRoutes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        EventRoutes eventRoutes = new EventRoutes();
        RegistrationRoutes registrationRoutes = new RegistrationRoutes();

        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(eventRoutes.eventRoutes())
                .setRoute(registrationRoutes.registrationRoutes());

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();



        RegistrationDAO dao = RegistrationDAO.getInstance(emf);
        RegistrationController.readAll(dao);


//        // Create three events
//        Event event1 = new Event("Workshop on Machine Learning", Event.Category.workshop, "Learn the basics of ML", LocalDate.of(2024, 4, 10), LocalTime.of(9, 0), 3, 50, "Conference Hall A", "John Doe", 49.99, "ml_workshop.jpg", Event.Status.active);
//        Event event2 = new Event("Introduction to Java Programming", Event.Category.course, "Learn Java from scratch", LocalDate.of(2024, 5, 15), LocalTime.of(13, 30), 4, 30, "Room B", "Jane Smith", 59.99, "java_intro.jpg", Event.Status.inactive);
//        Event event3 = new Event("Tech Talk: Artificial Intelligence", Event.Category.talk, "Latest trends in AI", LocalDate.of(2024, 6, 20), LocalTime.of(18, 0), 2, 100, "Auditorium", "Mark Johnson", 0.00, "ai_tech_talk.jpg", Event.Status.canceled);
//
//        // Persist the events
//        em.persist(event1);
//        em.persist(event2);
//        em.persist(event3);
//
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
    }
}
