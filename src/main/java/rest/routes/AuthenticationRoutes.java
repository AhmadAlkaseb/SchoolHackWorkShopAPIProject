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
                post("/login", ctx -> {
                    try {
                        AuthController.login(authDAO).handle(ctx);
                    } catch (APIException e) {
                        ctx.status(e.getStatusCode()).result(e.getMessage());
                    }
                });
                post("/logout", ctx -> {
                    try {
                        AuthController.logout(authDAO).handle(ctx);
                    } catch (APIException e) {
                        //Can something go wrong when logging out?
                    }
                });
                post("/register", ctx -> {
                    try {
                        AuthController.register(authDAO).handle(ctx);

                    } catch (APIException e){

                    }
                });
                post("/reset-password", ctx -> {
                    try {
                        AuthController.resetPassword(authDAO).handle(ctx);
                    } catch (Exception e) {

                    }
                });
            });
        };
    }
    private static enum Role implements RouteRole {ANYONE, USER, ADMIN}
}
