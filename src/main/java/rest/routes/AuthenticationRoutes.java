package rest.routes;

import controllers.AuthController;
import daos.AuthDAO;
import daos.UserDAO;
import exceptions.APIException;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import persistence.config.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;
public class AuthenticationRoutes {
    private static AuthDAO authDAO = AuthDAO.getInstance(HibernateConfig.getEntityManagerFactoryConfig(false));
    public static EndpointGroup getAuthRoutes() {
        return () -> {
            path("/auth", () -> {
                //Login route
                post("/login", ctx -> {
                    try {
                        AuthController.login(authDAO).handle(ctx);
                    } catch (APIException e) {
                        ctx.status(e.getStatusCode()).result(e.getMessage());
                    }
                }
                ,Role.anyone
                );
                //Logout route
                post("/logout", ctx -> {
                    try {
                        AuthController.logout(authDAO).handle(ctx);
                    } catch (APIException e) {
                        ctx.status(e.getStatusCode()).result(e.getMessage());
                    }
                }
                ,Role.student,Role.instructor,Role.admin
                );
                //Register new user route
                post("/register", ctx -> {
                    try {
                        AuthController.register(authDAO).handle(ctx);
                    } catch (APIException e){
                        ctx.status(e.getStatusCode()).result(e.getMessage());
                    }
                }
                ,Role.anyone
                );
                //todo: not done
                //request with email
                //create new temporary token and route from that token
                //response with that temporary token
                //let user post new password, unless token is expired
                post("/reset-password", ctx -> {
                    try {
                        AuthController.resetPassword(authDAO).handle(ctx);
                    } catch (Exception e) {

                    }
                });
            });
        };
    }
}
