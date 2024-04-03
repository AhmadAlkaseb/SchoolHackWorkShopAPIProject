package rest.routes;

import controllers.RegistrationController;
import daos.EventDAO;
import daos.RegistrationDAO;
import daos.UserDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RegistrationRoutes {
    RegistrationDAO regDAO = RegistrationDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));
    EventDAO eventDAO = EventDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));
    UserDAO userDAO = UserDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));

    public EndpointGroup registrationRoutes() {
        return () -> path("/registrations", () -> {

            // GET /registrations/:id: Get all registrations for an event.
            get("/", ctx -> {
                try {
                    RegistrationController.readAll(regDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // GET /registration/:id: Get a single registration.
            get("/{id}", ctx -> {
                try {
                    RegistrationController.getById(regDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // POST /registrations/:id: Register a user for an event.
            post("/add_user_to_event/{eventid}", ctx -> {
                try {
                    RegistrationController.registerUserToEvent(userDAO, eventDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // DELETE /registrations/:id: Cancel a user's registration for an event.
            delete("/delete_user_from_event/{eventid}", ctx -> {
                try {
                    RegistrationController.deleteUserFromEvent(regDAO).handle(ctx);
                } catch (APIException e) {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });
        });
    }
}