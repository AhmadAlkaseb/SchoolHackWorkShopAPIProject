package controllers;

import daos.EventDAO;
import dtos.EventDTO;
import exceptions.APIException;
import io.javalin.http.Handler;
import persistence.model.Event;
import rest.routes.Role;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventController {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    private static EventDTO createEventDTOFromEvent(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .time(event.getTime())
                .duration(event.getDuration())
                .capacity(event.getCapacity())
                .location(event.getLocation())
                .instructor(event.getInstructor())
                .price(event.getPrice())
                .category(String.valueOf(event.getCategory()))
                .image(event.getImage())
                .status(String.valueOf(event.getStatus()))
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .deletedAt(event.getDeletedAt())
                .build();
    }

    public static Handler create(EventDAO eventDAO) {
        return ctx -> {
            Event event = ctx.bodyAsClass(Event.class);
            if (event != null) {
                Event createdEvent = (Event) eventDAO.create(event);
                ctx.json(createEventDTOFromEvent(createdEvent));
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
                ctx.json(createEventDTOFromEvent(foundEvent));
            } else {
                throw new APIException(404, "The id you are looking for does not exist. " + timestamp);
            }
        };
    }

    public static Handler readAll(EventDAO eventDAO) {
        return ctx -> {
            List<Event> eventList = eventDAO.readAll();
            if (eventList != null) {
                List<EventDTO> eventDTOList = new ArrayList<>();
                for (Event e : eventList) {
                    eventDTOList.add(createEventDTOFromEvent(e));
                }
                ctx.json(eventDTOList);
            } else {
                throw new APIException(404, "No events available. " + timestamp);
            }
        };
    }

    public static Handler update(EventDAO eventDAO) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Event updatedEvent = ctx.bodyAsClass(Event.class);

            Event foundEvent = (Event) eventDAO.getById(id);

            if (foundEvent != null) {
                if (updatedEvent.getTitle() != null && !updatedEvent.getTitle().isEmpty()) {
                    foundEvent.setTitle(updatedEvent.getTitle());
                }
                if (updatedEvent.getDescription() != null && !updatedEvent.getDescription().isEmpty()) {
                    foundEvent.setDescription(updatedEvent.getDescription());
                }
                if (updatedEvent.getDate() != null) {
                    foundEvent.setDate(updatedEvent.getDate());
                }
                if (updatedEvent.getTime() != null) {
                    foundEvent.setTime(updatedEvent.getTime());
                }
                if (updatedEvent.getDuration() != 0.0) {
                    foundEvent.setDuration(updatedEvent.getDuration());
                }
                if (updatedEvent.getCapacity() != 0.0) {
                    foundEvent.setCapacity(updatedEvent.getCapacity());
                }
                if (updatedEvent.getLocation() != null && !updatedEvent.getLocation().isEmpty()) {
                    foundEvent.setLocation(updatedEvent.getLocation());
                }
                if (updatedEvent.getInstructor() != null && !updatedEvent.getInstructor().isEmpty()) {
                    foundEvent.setInstructor(updatedEvent.getInstructor());
                }
                if (updatedEvent.getPrice() != 0.0) {
                    foundEvent.setPrice(updatedEvent.getPrice());
                }
                if (updatedEvent.getImage() != null && !updatedEvent.getImage().isEmpty()) {
                    foundEvent.setImage(updatedEvent.getImage());
                }
                if (updatedEvent.getCreatedAt() != null) {
                    foundEvent.setCreatedAt(updatedEvent.getCreatedAt());
                }
                if (updatedEvent.getUpdatedAt() != null) {
                    foundEvent.setUpdatedAt(updatedEvent.getUpdatedAt());
                }
                if (updatedEvent.getDeletedAt() != null) {
                    foundEvent.setDeletedAt(updatedEvent.getDeletedAt());
                }
                eventDAO.update(foundEvent);
                ctx.json(foundEvent);
            } else {
                throw new APIException(404, "No event found with id: " + id);
            }
        };
    }

    public static Handler delete(EventDAO eventDAO) {
        return ctx -> {
            int roomId = Integer.parseInt(ctx.pathParam("id"));
            Event foundEvent = (Event) eventDAO.getById(roomId);
            int deletedRow = eventDAO.delete(roomId);
            if (deletedRow != 0) {
                ctx.json(createEventDTOFromEvent(foundEvent));
            } else {
                throw new APIException(404, "The id you are looking for does not exist. " + timestamp);
            }
        };
    }

    public static Handler readByCategory(EventDAO eventDAO) {
        return ctx -> {
            String categoryStr = ctx.pathParam("category");
            Event.Category category = Event.Category.valueOf(categoryStr.toLowerCase());

            List<Event> foundEvents = eventDAO.readAll();
            List<Event> filteredEvents = foundEvents
                    .stream()
                    .filter(event -> event.getCategory() == category)
                    .collect(Collectors.toList());

            if (!filteredEvents.isEmpty()) {
                ctx.json(covertEventListToEventDTOList(filteredEvents));
            } else {
                throw new APIException(404, "No events found for the category: " + categoryStr);
            }
        };
    }

    public static Handler readByStatus(EventDAO eventDAO) {
        return ctx -> {
            String statusStr = ctx.pathParam("status");
            Event.Status status = Event.Status.valueOf(statusStr.toLowerCase());

            List<Event> foundEvents = eventDAO.readAll();
            List<Event> filteredEvents = foundEvents
                    .stream()
                    .filter(event -> event.getStatus().equals(status))
                    .collect(Collectors.toList());

            if (!filteredEvents.isEmpty()) {
                ctx.json(covertEventListToEventDTOList(filteredEvents));
            } else {
                throw new APIException(404, "The status you are looking for does not exist. " + timestamp);
            }
        };
    }

    private static List<EventDTO> covertEventListToEventDTOList(List<Event> eventList) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        for (Event e : eventList) {
            eventDTOList.add(createEventDTOFromEvent(e));
        }
        return eventDTOList;
    }
}