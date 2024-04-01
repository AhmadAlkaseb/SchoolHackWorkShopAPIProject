package rest.routes;

import controllers.EventController;
import daos.EventDAO;
import io.javalin.apibuilder.EndpointGroup;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventRoutes {

    EventDAO eventDAO = EventDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));

    public EndpointGroup roomRoutes() {
        return () -> {
            post("/events", EventController.create(eventDAO));
            get("/events", EventController.readAll(eventDAO));
            get("/events/{id}", EventController.readById(eventDAO));
            put("/events/{id}", EventController.update(eventDAO));
            delete("/events/{id}", EventController.delete(eventDAO));
        };
    }
}
