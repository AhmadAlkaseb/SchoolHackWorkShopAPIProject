package rest.routes;

import controllers.RegistrationController;
import daos.EventDAO;
import daos.RegistrationDAO;
import daos.UserDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RegistrationRoutes {
    RegistrationDAO regDAO;
    EventDAO eventDAO;
    UserDAO userDAO;

    public RegistrationRoutes(EntityManagerFactory emf) {
        regDAO = RegistrationDAO.getInstance(emf);
        eventDAO = EventDAO.getInstance(emf);
        userDAO = UserDAO.getInstance(emf);
    }

    public EndpointGroup registrationRoutes() {
        return () ->
                path("/registrations", () -> {
                    //before(AuthController.authenticate());
                    // GET /registrations/ Get all registrations.
                    get("/", ctx -> {
                        try {
                            RegistrationController.readAll(regDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }, Role.ANYONE);

                    // GET /registrations/:id: Get all registrations for an event.
                    get("/all-by-eventid/{event-id}", ctx -> {
                        try {
                            RegistrationController.readAllByEvent(regDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }, Role.ANYONE);

                    // GET /registrations/:id: Get a single registration.
                    get("/{id}", ctx -> {
                        try {
                            RegistrationController.getById(regDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }, Role.ANYONE);

                    // POST /registrations/:id: Register a user for an event.
                    post("/add_user_to_event/{eventid}", ctx -> {
                        try {
                            RegistrationController.registerUserToEvent(userDAO, eventDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }, Role.ANYONE);

                    // DELETE /registrations/:id: Cancel a user's registration for an event.
                    delete("/delete_user_from_event/{eventid}", ctx -> {
                        try {
                            RegistrationController.deleteUserFromEvent(regDAO).handle(ctx);
                        } catch (APIException e) {
                            ctx.status(e.getStatusCode()).result(e.getMessage());
                        }
                    }, Role.ANYONE);
                });
    }
}