# SchoolHackWorkShopAPIProject

Link til at gøre links kortere: https://free-url-shortener.rb.gy/

### Udarbejdet af

Ahmad, Hanni, Lasse & Youssef

### Link til DM:

https://rb.gy/vbx7ci

### Link til ERD:

https://rb.gy/t9pwu6

### User stories & status

Student Stories
As a user, I want to see all the events/workshops that are going to be held.
STATUS:
As a user, I want to see the details of a specific event/workshop.
STATUS:
As a user, I want to register for an event/workshop.
STATUS:
As a user, I want to cancel my registration for an event/workshop.
STATUS:
As a user, I want to be able to reset my password.
(How can you make sure that the user is the one who is resetting the password?)
STATUS:

Instructor Stories  
As an instructor, I want to create a new event/workshop.
STATUS:
As an instructor, I want to update an event/workshop.
STATUS:
As an instructor, I want to cancel an event/workshop.
STATUS:
As an instructor, I want to see all the events/workshops that I am teaching.
STATUS:
As an instructor, I want to see all the registrations for an event/workshop.STATUS:

Admin Stories
As an admin, I want to see all the users.
STATUS:
As an admin, I want to see all the events/workshops.
STATUS:

General Stories
Implement a way to filter events by category and status.
STATUS:

### API Documentation

    HTTP method | Rest Ressource                                     | Role                              | Response body | Exception & status  | Comment
    
    Events
    GET           api/events                                           anyone                                              APIException & 404    Retrieve all events
    GET           api/events/{id}                                      anyone                                              APIException & 404    Retrieve an event by id
    GET           api/events/categories/{category}                     anyone                                              APIException & 404    Retrieve an event by category
    GET           api/events/status/{status}                           anyone                                              APIException & 404    Retrieve an event by status
    POST          api/events                                           instructor / admin                                  APIException & 500    Add a new event
    PUT           api/events/{id}                                      instructor / admin                                  APIException & 404    Update an event by id
    DELETE        api/events/{id}                                      admin                                               APIException & 404    Delete an event by id

    Users
    GET           api/users                                             Admin                                               APIException & 404    Retrieve all users
    GET           api/users/{id}                                        Admin                                               APIException & 404    Retrieve a single user by ID
    POST          api/users                                             Student / admin                                     APIException & 500    Create a new user
    PUT           api/users/{id}                                        Student/Admin                                       APIException & 404    Update a user by ID
    DELETE        api/users/{id}                                        Student/Admin                                       APIException & 404    Delete a user by ID
 
    Registrations
    GET           api/registrations/                                    instructor / admin                                  APIException & 404    Retrieve all registrations
    GET           api/registrations/{id}                                instructor / admin                                  APIException & 404    Retrieve registration by id
    POST          api/registrations/add_user_to_event/{eventid}         user / instructor / admin                           APIException & 404    Registrating user to event by event id in path and user id in body 
    DELETE        api/registrations/delete_user_from_event/{eventid}    user / instructor / admin                           APIException & 404    Delete a registration by event id in path and user id in body

    Authentication



### Test

Link:

Test coverage of:

EventRoutes class:
Status:

AuthenticationRoutes class:
Status:

UserRoutes class:
Status:

RegistrationsRoutes class:
Status: