package rest.routes;

import daos.UserDAO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import persistence.config.HibernateConfig;
import persistence.model.User;
import rest.config.ApplicationConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRoutesTest {

    private static EntityManagerFactory emf;
    private static ApplicationConfig app;
    private static int port = 7007;
    private static UserDAO userDAO;
    private static UserRoutes userRoutes;
    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static User user5;


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:7007/api";

        emf = HibernateConfig.getEntityManagerFactoryConfig(true);

        userDAO = UserDAO.getInstance(emf);
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

        user1 = new User("Hans", "hans@mail.com", "password", 12345678);
        user2 = new User("Martin", "martin@mail.com", "password", 12345678);
        user3 = new User("Tom", "tom@mail.com", "password", 12345678);
        user4 = new User("Louise", "louise@mail.com", "password", 12345678);
        user5 = new User("Helle", "helle@mail.com", "password", 12345678);

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            em.persist(user5);
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
    @DisplayName("check user id")
    public void test1() {
//      given:
//        int expected = 1;
//        int actual = user1.getId();
//
//        assertEquals(expected,actual);
    }


    @Test
    @DisplayName("get all users")
    public void tes2(){

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
//                .body("[0].userId", equalTo(expectedUserId))
//                .body("[0].eventName", equalTo(expectedEventName))
//                .body("size()", is(expectedSize))
                .extract().response().prettyPrint();
    }

}