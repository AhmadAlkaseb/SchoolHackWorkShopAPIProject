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

    Events
    HTTP method | Rest Ressource                    | Role        | Response body | Exception & status  | Comment
    GET           api/events                          student       Link            APIException & 404    Retrieve all events
    GET           api/events/{id}                     student       Link            APIException & 404    Retrieve an event by id
    GET           api/events/categories/{category}    student       Link            APIException & 404    Retrieve an event by category
    GET           api/events/status/{status}          student       Link            APIException & 404    Retrieve an event by status
    POST          api/events                          instructor    Link            APIException & 500    Add a new event
    PUT           api/events/{id}                     instructor    Link            APIException & 404    Update an event by id
    DELETE        api/events/{id}                     admin         Link            APIException & 404    Delete an event by id

    Users
    HTTP method | Rest Ressource                    | Role           | Response body | Exception & status  | Comment
    GET           api/users                           Admin                            APIException & 404    Retrieve all users
    GET           api/users/{id}                      Admin                            APIException & 404    Retrieve a single user by ID
    POST          api/users                           Student                          APIException & 500    Create a new user
    PUT           api/users/{id}                      Student/Admin                    APIException & 404    Update a user by ID
    DELETE        api/users/{id}                      Student/Admin                    APIException & 404    Delete a user by ID

    Registrations
    HTTP method | Rest Ressource | Role     | Response body | Exception & status  | Comment

    Authentication
    HTTP method | Rest Ressource | Role     | Response body | Exception & status  | Comment

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