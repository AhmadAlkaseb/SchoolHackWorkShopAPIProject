package rest.routes;

import dtos.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.Event;
import persistence.model.User;
import rest.config.ApplicationConfig;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.equalTo;

class UserRoutesTest {
    private static EntityManagerFactory emf;
    private static ApplicationConfig app;
    private static int port = 7007;
    private static UserRoutes userRoutes;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:7007/api/users";
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        userRoutes = new UserRoutes(emf);
        app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(port)
                .setExceptionHandlers()
                .checkSecurityRoles()
                .setRoute(userRoutes.userRoutes());
    }

    @AfterAll
    static void tearDown() {
        emf.close();
        app.stopServer();
    }

    @BeforeEach
    void setUpBeforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new User("Hans", "hans@mail.com", "password", 12345678));
            em.persist(new User("Martin", "martin@mail.com", "password", 12345678));
            em.persist(new User("Tom", "tom@mail.com", "password", 12345678));
            em.persist(new User("Louise", "louise@mail.com", "password", 12345678));
            em.persist(new Event("Yoga for Beginners", Event.Category.workshop, "A relaxing introduction to yoga.", LocalDate.of(2024, 4, 3), LocalTime.of(18, 0), 1.5, 20, "Room 101", "John Doe", 10.0, "yoga.jpg", Event.Status.active));
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDownAfterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User ").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    public void addUserToEvent() {
        User user = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .post("/{eventid}/{userid}", 1, 1)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        System.out.println("User: " + user);
    }

    @Test
    @DisplayName("get all users")
    public void tes1() {
        int expectedSize = 4;
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", equalTo(expectedSize));
    }

    @Test
    @DisplayName("get user by specific id ")
    public void tes2() {
        int expectedId = 2;
        int setId = 2;

        // Test af user rute
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}", setId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(expectedId));

        // Extract UserDTO from route
        UserDTO foundUser = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}", setId)
                .then()
                .statusCode(200)
                .extract().as(UserDTO.class);

        // Extract response from route
        String response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}", setId)
                .then()
                .statusCode(200)
                .extract().asString();
    }

    @Test
    @DisplayName("create a user")
    public void tes3() {
        String setBody = "{\"name\": \"UserTest\", \"email\": \"1234\", \"password\": \"1234\", \"phone\": \"1234\"}";
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(setBody)
                .when()
                .post("/")
                .then()
                .statusCode(200)
                .body("name", equalTo("UserTest"));
    }

    @Test
    @DisplayName("update a user")
    public void tes4() {
        String setBody = "{\"name\": \"test\"}";
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(setBody)
                .when()
                .put("/{id}", 3)
                .then()
                .statusCode(200)
                .body("name", equalTo("test"));
    }

    @Test
    @DisplayName("delete user by specific id")
    public void tes5() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/{id}", 4)
                .then()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("name", equalTo("Louise"));
    }
}