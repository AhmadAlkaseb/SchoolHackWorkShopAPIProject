package controllers;

import daos.EventDAO;
import daos.RegistrationDAO;
import daos.UserDAO;
import dtos.RegistrationDTO;
import exceptions.APIException;
import io.javalin.http.Handler;
import jakarta.persistence.EntityNotFoundException;
import persistence.model.Event;
import persistence.model.Registration;
import persistence.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationController {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());


    private static RegistrationDTO convertToDTO(Registration reg){
        return RegistrationDTO.builder()
                .regId(reg.getId())
                .userId(reg.getUser().getId())
                .userName(reg.getUser().getName())
                .eventId(reg.getEvent().getId())
                .eventName(reg.getEvent().getDescription())
                .build();
    }

    public static Handler readAll(RegistrationDAO registrationDAO) {
        return ctx -> {
            List<Registration> registrations = registrationDAO.readAll();
            List<RegistrationDTO> registrationDTOS = new ArrayList<>();

            for (Registration r : registrations){
                registrationDTOS.add(convertToDTO(r));
            }

            if (registrationDAO != null) {
                ctx.json(registrationDTOS);
            } else {
                throw new APIException(404, "No registrations available. " + timestamp);
            }
        };
    }

    public static Handler getById(RegistrationDAO registrationDAO){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Registration registration = (Registration) registrationDAO.getById(id);

            if(registration != null){
                ctx.json(registration);
            }else{
                throw new APIException(401, "Registration with ID: "+id+" was not found");
            }
        };
    }

    public static Handler registerUserToEvent(UserDAO userDAO, EventDAO eventDAO) {
        return ctx ->{
            int eventId = Integer.parseInt(ctx.pathParam("id"));
            int userId = ctx.bodyAsClass(Integer.class);

            User user = (User) userDAO.getById(userId);
            Event event = (Event) eventDAO.getById(eventId);

            if(user != null && event != null) {
                user.addEvent(event);
                userDAO.update(user);
            }else{
                throw new EntityNotFoundException();
            }
        };
    }

    public static Handler deleteUserFromEvent(RegistrationDAO registrationDAO) {
        return ctx -> {
            int eventId = Integer.parseInt(ctx.pathParam("id"));
            int userId = ctx.bodyAsClass(Integer.class);

            Registration registration = registrationDAO.getRegistrationByNameAndEvent(userId, eventId);

            RegistrationDTO dto = convertToDTO(registration);

            if(registration != null && registration != null) {
                registrationDAO.delete(registration.getId());
                ctx.json(dto);
            }else{
                throw new EntityNotFoundException();
            }
        };
    }
}
