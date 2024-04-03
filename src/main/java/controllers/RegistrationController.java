package controllers;

import daos.EventDAO;
import daos.RegistrationDAO;
import dtos.EventDTO;
import dtos.RegistrationDTO;
import exceptions.APIException;
import io.javalin.http.Handler;
import persistence.model.Registration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegistrationController {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    public static Handler readAll(RegistrationDAO registrationDAO) {
        return ctx -> {
            List<Registration> registrations = registrationDAO.readAll();

            // Lav dtoerne her



            if (registrations != null) {
                ctx.json(registrations);
            } else {
                throw new APIException(404, "No registrations available. " + timestamp);
            }
        };
    }

    public static Handler getById(RegistrationDAO registrationDAO){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Registration registration = (Registration) registrationDAO.getById(id);
            //RegistrationDTO registrationDTO = ()
            if(registration != null){
                ctx.json(registration);
            }else{
                throw new APIException(401, "Registration with ID: "+id+" was not found");
            }
        };
    }

}
