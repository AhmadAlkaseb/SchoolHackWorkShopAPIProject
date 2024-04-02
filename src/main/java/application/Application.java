package application;

import jakarta.persistence.EntityManagerFactory;
import persistence.config.HibernateConfig;
import rest.config.ApplicationConfig;
import rest.routes.AuthenticationRoutes;
import rest.routes.EventRoutes;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        EventRoutes eventRoutes = new EventRoutes();
        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(eventRoutes.eventRoutes())
                .setRoute(AuthenticationRoutes.getAuthRoutes());
    }
}
