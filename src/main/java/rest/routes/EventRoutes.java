package rest.routes;

import controllers.EventController;
import daos.EventDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventRoutes {
    EventDAO eventDAO = EventDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));
    public EndpointGroup eventRoutes() {
        return () -> path("/events", () -> {

            // Create an event.
            post("/", ctx -> {
                try {
                    EventController.create(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Get all events
            get("/", ctx -> {
                try {
                    EventController.readAll(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Get a specific event by id
            get("/{id}", ctx -> {
                try {
                    EventController.readById(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Update a specific event by id
            put("/{id}", ctx -> {
                try {
                    EventController.update(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Delete existing event by id
            delete("/{id}", ctx -> {
                try {
                    EventController.delete(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Filter events by category
            get("/categories/{category}", ctx -> {
                try {
                    EventController.readByCategory(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Filter events by status
            get("/status/{status}", ctx -> {
                try {
                    EventController.readByStatus(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });
        });
    }


    public EndpointGroup registrationRoutes() {
        return () -> path("/events", () -> {

            // GET /registrations/:id: Get all registrations for an event.
            post("/", ctx -> {
                try {
                    EventController.create(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // GET /registration/:id: Get a single registration.
            get("/", ctx -> {
                try {
                    EventController.readAll(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // POST /registrations/:id: Register a user for an event.
            get("/{id}", ctx -> {
                try {
                    EventController.readById(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // DELETE /registrations/:id: Cancel a user's registration for an event.
            put("/{id}", ctx -> {
                try {
                    EventController.update(eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });
        });
    }

}