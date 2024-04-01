package controllers;

import daos.EventDAO;
import exceptions.APIException;
import io.javalin.http.Handler;
import persistence.model.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    public static Handler create(EventDAO eventDAO) {
        return ctx -> {
            Event event = ctx.bodyAsClass(Event.class);

            if (event != null) {
                Event createdEvent = (Event) eventDAO.create(event);
                ctx.json(createdEvent);
            } else {
                throw new APIException(500, "Received data was incorrect. " + timestamp);
            }
        };
    }

    public static Handler readById(EventDAO eventDAO) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Event foundEvent = (Event) eventDAO.getById(id);
            if (foundEvent != null) {
                ctx.json(foundEvent);
            } else {
                throw new APIException(404, "The id you are looking for does not exist. " + timestamp);
            }
        };
    }

    public static Handler readAll(EventDAO eventDAO) {
        return ctx -> {
            if (eventDAO.readAll() != null) {
                ctx.json(eventDAO.readAll());
            } else {
                throw new APIException(404, "No events available . " + timestamp);
            }
        };
    }

    public static Handler update(EventDAO eventDAO) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Event updatedEvent = ctx.bodyAsClass(Event.class);

            Event foundEvent = (Event) eventDAO.getById(id);

            if (foundEvent != null) {
                //Opdatere oplysningerne, men hvis oplysningen er null, så skal den ikke opdateres.
                if (updatedEvent.getPrice() != 0.0) {
                    foundEvent.setPrice(updatedEvent.getPrice());
                }
                eventDAO.update(foundEvent);
                ctx.json(foundEvent);
            } else {
                throw new APIException(404, "No events available . " + timestamp);
            }
        };
    }

    public static Handler delete(EventDAO eventDAO) {
        return ctx -> {
            int roomId = Integer.parseInt(ctx.pathParam("id"));
            Event foundEvent = (Event) eventDAO.getById(roomId);
            int i = eventDAO.delete(roomId);
            if (i != 0) {
                ctx.status(200).json(foundEvent);
            } else {
                throw new APIException(404, "The id you are looking for does not exist. " + timestamp);
            }
        };
    }
}

