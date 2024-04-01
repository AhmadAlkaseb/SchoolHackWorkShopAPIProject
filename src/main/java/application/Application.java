package application;

import jakarta.persistence.EntityManagerFactory;
import persistence.config.HibernateConfig;
import rest.config.ApplicationConfig;
import rest.routes.Routes;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        Routes routes = new Routes();

        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(routes.eventRoutes());
    }
}
