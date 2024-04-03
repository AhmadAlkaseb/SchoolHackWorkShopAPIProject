package rest.routes;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    anyone, student, instructor, admin
}
