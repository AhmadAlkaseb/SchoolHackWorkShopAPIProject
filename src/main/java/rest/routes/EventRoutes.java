package rest.routes;

import controllers.EventController;
import daos.EventDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EventRoutes {
    private EventDAO eventDAO;

    public EventRoutes(EntityManagerFactory emf) {
        this.eventDAO = EventDAO.getInstance(emf);
    }

    public EndpointGroup eventRoutes() {
        return () -> path("/events", () -> {

            // Create an event.
            post("/", ctx -> {
                        try {
                            EventController.create(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.INSTRUCTOR, Role.ADMIN, Role.ANYONE
            );

            // Get all events
            get("/", ctx -> {
                        try {
                            EventController.readAll(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.ANYONE
            );

            // Get a specific event by id
            get("/{id}", ctx -> {
                        try {
                            EventController.readById(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.ANYONE
            );

            // Update a specific event by id
            put("/{id}", ctx -> {
                        try {
                            EventController.update(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.INSTRUCTOR, Role.ADMIN, Role.ANYONE
            );

            // Delete existing event by id
            delete("/{id}", ctx -> {
                        try {
                            EventController.delete(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.ADMIN, Role.ANYONE
            );

            // Filter events by category
            get("/categories/{category}", ctx -> {
                        try {
                            EventController.readByCategory(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.ANYONE, Role.ANYONE
            );

            // Filter events by status
            get("/status/{status}", ctx -> {
                        try {
                            EventController.readByStatus(eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }
                    , Role.ANYONE, Role.ANYONE
            );
        });
    }
}