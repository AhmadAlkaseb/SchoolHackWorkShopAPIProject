package rest.routes;

import controllers.UserController;
import daos.UserDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoutes
{
    UserDAO userDAO = UserDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));

    public EndpointGroup userRoutes()
    {
        return () -> path("/users", () ->
        {

            // Get all users.
            get("/", ctx ->
            {
                try
                {
                    UserController.getAllUsers(userDAO).handle(ctx);
                }
                catch (APIException e)
                {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Get a single user.
            get("/{id}", ctx ->
            {
                try
                {
                    UserController.getUserById(userDAO).handle(ctx);
                }
                catch (APIException e)
                {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Create a new user.
            post("/", ctx ->
            {
                try
                {
                    UserController.createUser(userDAO).handle(ctx);
                }
                catch (APIException e)
                {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Update a user.
            put("/{id}", ctx ->
            {
                try
                {
                    UserController.updateUser(userDAO).handle(ctx);
                }
                catch (APIException e)
                {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });

            // Delete a user
            delete("/{id}", ctx ->
            {
                try
                {
                    UserController.deleteUser(userDAO).handle(ctx);
                }
                catch (APIException e)
                {
                    ctx.status(e.getStatusCode()).result(e.getMessage());
                }
            });
        });
    }













    // Get all users.
    // Get a single user.
    // Create a new user.
    // Update a user.
    // Delete a user.
}
