# SchoolHackWorkShopAPIProject

Link til at gøre links kortere: https://free-url-shortener.rb.gy/

### Udarbejdet af

Ahmad, Hanni, Lasse & Youssef

### Link til DM:

https://rb.gy/vbx7ci

### Link til ERD:

https://rb.gy/t9pwu6

### User stories & status

**Student Stories**  
As a user, I want to see all the events/workshops that are going to be held.  
**STATUS:** *DONE*  
As a user, I want to see the details of a specific event/workshop.  
**STATUS:** *DONE*  
As a user, I want to register for an event/workshop.  
**STATUS:** *DONE*  
As a user, I want to cancel my registration for an event/workshop.  
**STATUS:** *DONE*  
As a user, I want to be able to reset my password.
(How can you make sure that the user is the one who is resetting the password?)  
**STATUS:** *DONE*

**Instructor Stories**  
As an instructor, I want to create a new event/workshop.  
**STATUS:** *DONE*    
As an instructor, I want to update an event/workshop.  
**STATUS:** *DONE*  
As an instructor, I want to cancel an event/workshop.  
**STATUS:** *DONE*  
As an instructor, I want to see all the events/workshops that I am teaching.  
**STATUS:** *DONE*  
As an instructor, I want to see all the registrations for an event/workshop.  
**STATUS:** *DONE*

**Admin Stories**  
As an admin, I want to see all the users.  
**STATUS:** *DONE*  
As an admin, I want to see all the events/workshops.  
**STATUS:** *DONE*

**General Stories**  
Implement a way to filter events by category and status.  
**STATUS:** *DONE*

### API Documentation

    HTTP method           Rest Ressource                                       Role                           Response body                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 Exception & status    Comment
    
    Events
    GET                   api/events                                           anyone                         response: [{"id":600,"title":"Advanced Pottery","category":"course","description":"Sculpt and mold your way to mastery.","date":[2024,4,4],"time":[17,0],"duration":2.0,"capacity":15,"location":"Art Studio","instructor":"Jane Smith","price":20.0,"image":"pottery.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null},{"id":601,"title":"Beginner's Guitar","category":"talk","description":"Strum the strings with ease.","date":[2024,4,5],"time":[19,0],"duration":1.0,"capacity":10,"location":"Music Hall","instructor":"Alex Johnson","price":15.0,"image":"guitar.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}]       APIException & 404    Retrieve all events
    GET                   api/events/{id}                                      anyone                         response: {"id":600,"title":"Advanced Pottery","category":"course","description":"Sculpt and mold your way to mastery.","date":[2024,4,4],"time":[17,0],"duration":2.0,"capacity":15,"location":"Art Studio","instructor":"Jane Smith","price":20.0,"image":"pottery.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}                                                                                                                                                                                                                                                                                                                                                   APIException & 404    Retrieve an event by id
    GET                   api/events/categories/{category}                     anyone                         response: {"id":601,"title":"Beginner's Guitar","category":"talk","description":"Strum the strings with ease.","date":[2024,4,5],"time":[19,0],"duration":1.0,"capacity":10,"location":"Music Hall","instructor":"Alex Johnson","price":15.0,"image":"guitar.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}                                                                                                                                                                                                                                                                                                                                                           APIException & 404    Retrieve an event by category
    GET                   api/events/status/{status}                           anyone                         response: [{"id":600,"title":"Advanced Pottery","category":"course","description":"Sculpt and mold your way to mastery.","date":[2024,4,4],"time":[17,0],"duration":2.0,"capacity":15,"location":"Art Studio","instructor":"Jane Smith","price":20.0,"image":"pottery.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null},{"id":601,"title":"Beginner's Guitar","category":"talk","description":"Strum the strings with ease.","date":[2024,4,5],"time":[19,0],"duration":1.0,"capacity":10,"location":"Music Hall","instructor":"Alex Johnson","price":15.0,"image":"guitar.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}]       APIException & 404    Retrieve an event by status
    POST                  api/events                                           instructor / admin             response: {"id":604,"title":"Introduction to Python Programming","category":"workshop","description":"Learn Python basics and fundamentals","date":[2024,5,20],"time":[9,0],"duration":4.0,"capacity":50,"location":"Conference Room B","instructor":"Alice Smith","price":29.99,"image":"python_workshop.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}                                                                                                                                                                                                                                                                                                              APIException & 500    Add a new event
    PUT                   api/events/{id}                                      instructor / admin             response: {"id":600,"title":"Test","category":"course","description":"Sculpt and mold your way to mastery.","date":[2024,4,4],"time":[17,0],"duration":2.0,"capacity":15,"location":"Art Studio","instructor":"Jane Smith","price":20.0,"image":"pottery.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}                                                                                                                                                                                                                                                                                                                                                               APIException & 404    Update an event by id
    DELETE                api/events/{id}                                      admin                          response: {"id":604,"title":"Introduction to Python Programming","category":"workshop","description":"Learn Python basics and fundamentals","date":[2024,5,20],"time":[9,0],"duration":4.0,"capacity":50,"location":"Conference Room B","instructor":"Alice Smith","price":29.99,"image":"python_workshop.jpg","status":"active","createdAt":[2024,4,5],"updatedAt":[2024,4,5],"deletedAt":null}                                                                                                                                                                                                                                                                                                              APIException & 404    Delete an event by id

    Users
    GET           api/users                                             Admin                                              APIException & 404    Retrieve all users
    GET           api/users/{id}                                        Admin                                              APIException & 404    Retrieve a single user by ID
    POST          api/users                                             Student / admin                                    APIException & 500    Create a new user
    PUT           api/users/{id}                                        Student/Admin                                      APIException & 404    Update a user by ID
    DELETE        api/users/{id}                                        Student/Admin                                      APIException & 404    Delete a user by ID
 
    Registrations
    GET           api/registrations/                                    instructor / admin                                 APIException & 404    Retrieve all registrations
    GET           api/registrations/{id}                                instructor / admin                                 APIException & 404    Retrieve registration by id
    POST          api/registrations/add_user_to_event/{eventid}         user / instructor / admin                          APIException & 404    Registrating user to event by event id in path and user id in body 
    DELETE        api/registrations/delete_user_from_event/{eventid}    user / instructor / admin                          APIException & 404    Delete a registration by event id in path and user id in body

    Authentication



### Test
Tests of daos: https://rb.gy/f8jk0z  
Tests of endpoints: https://rb.gy/qgesnr