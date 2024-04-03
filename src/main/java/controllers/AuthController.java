package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import daos.AuthDAO;
import dtos.TokenDTO;
import dtos.UserDTO;
import com.nimbusds.jose.*;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import persistence.model.User;

public class AuthController {
    private static ObjectMapper om = new ObjectMapper();
    public static Handler login(AuthDAO authDAO) {
        return ctx -> {
            ObjectNode node = om.createObjectNode();
            try {
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                User verifiedUser = authDAO.verifyUser(user.getEmail(), user.getPassword());
                String token = TokenController.createToken(new UserDTO(verifiedUser));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, user.getEmail()));
            } catch (EntityNotFoundException | ValidationException e){
                ctx.status(401);
                ctx.json(node.put("msg", e.getMessage()));
            }
        };
    }
    public static Handler logout(AuthDAO authDAO) {
        return ctx -> ctx.req().logout();
    }
    public static Handler register(AuthDAO authDAO) {
        return ctx -> {
            ObjectNode node = om.createObjectNode();
            try {
                User user = ctx.bodyAsClass(User.class);
                User createdUser = (User) authDAO.create(user);
                String token = TokenController.createToken(new UserDTO(createdUser));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, user.getEmail()));
            } catch(EntityExistsException e){
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(node.put("msg","User already exist"));
            }
        };
    }
    public static Handler resetPassword(AuthDAO authDAO) {
        return ctx -> {
            ObjectNode node = om.createObjectNode();
            try {
                User user = ctx.bodyAsClass(User.class);
                if(authDAO.doesUserExist(user.getEmail())){
                    //use another method to generate  temporary token
                    String token = TokenController.createToken(new UserDTO(user));
                    
                }
            } catch (EntityNotFoundException e){
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(node.put("msg", e.getMessage()));
            }
        };
    }
}
