package rest.routes.requests;

import controllers.EventController;
import controllers.RegistrationController;
import daos.EventDAO;
import daos.RegistrationDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RegistrationRoutes {
    RegistrationDAO regDAO = RegistrationDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));

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
//
//            // POST /registrations/:id: Register a user for an event.
//            get("/{id}", ctx -> {
//                try {
//                    EventController.readById(eventDAO).handle(ctx);
//                } catch (APIException e) {
//                    ctx.status(e.getStatusCode()).result(e.getMessage());
//                }
//            });
//
//            // DELETE /registrations/:id: Cancel a user's registration for an event.
//            delete("/{id}", ctx -> {
//                try {
//                    EventController.update(eventDAO).handle(ctx);
//                } catch (APIException e) {
//                    ctx.status(e.getStatusCode()).result(e.getMessage());
//                }
//            });
        });
    }

}