package application;

import jakarta.persistence.EntityManagerFactory;
import persistence.config.HibernateConfig;
import rest.config.ApplicationConfig;
import rest.routes.EventRoutes;
import rest.routes.UserRoutes;
import rest.routes.RegistrationRoutes;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        EventRoutes eventRoutes = new EventRoutes();
        UserRoutes userRoutes = new UserRoutes();
        RegistrationRoutes regRoutes = new RegistrationRoutes();
        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(eventRoutes.eventRoutes())
                .setRoute(userRoutes.userRoutes())
                .setRoute(regRoutes.registrationRoutes());
    }
}
