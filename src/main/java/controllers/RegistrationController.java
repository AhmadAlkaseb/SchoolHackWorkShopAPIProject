package controllers;

import daos.EventDAO;
import daos.RegistrationDAO;
import dtos.EventDTO;
import dtos.RegistrationDTO;
import exceptions.APIException;
import io.javalin.http.Handler;
import persistence.model.Registration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationController {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    public static Handler readAll(EventDAO eventDAO) {
        return ctx -> {
            List<RegistrationDTO> registrations = RegistrationDAO.readAll();
            if (registrations != null) {
                ctx.json(registrations);
            } else {
                throw new APIException(404, "No events available. " + timestamp);
            }
        };
    }
}
