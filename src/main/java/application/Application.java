package application;

import daos.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.Role;
import persistence.model.User;
import rest.config.ApplicationConfig;
import rest.routes.AuthenticationRoutes;
import rest.routes.EventRoutes;
import rest.routes.RegistrationRoutes;
import rest.routes.UserRoutes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        AuthenticationRoutes authenticationRoutes = new AuthenticationRoutes(emf);
        EventRoutes eventRoutes = new EventRoutes(emf);
        UserRoutes userRoutes = new UserRoutes(emf);
        RegistrationRoutes regRoutes = new RegistrationRoutes(emf);
        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(eventRoutes.eventRoutes())
                .setRoute(userRoutes.userRoutes())
                .setRoute(regRoutes.registrationRoutes())
                .setRoute(authenticationRoutes.getAuthRoutes())
                .setRoute(authenticationRoutes.authBefore())
                .checkSecurityRoles();
    }
}
