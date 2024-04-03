package rest.routes;

import dtos.UserDTO;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import persistence.config.HibernateConfig;
import persistence.model.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

class AuthenticationRoutesTest {
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);

    }

    @BeforeEach
    void beforeEachTest() {
        //User lasse = new User("Lasse", "lasse@mail.com","1234abcd",22334455, );
        var em = emf.createEntityManager();
        //em.persist(lasse);
    }
    @Test
    public void login() {
        UserDTO user = new UserDTO("lasse@mail.com","1234abcd");
        given()
                .contentType(ContentType.JSON)
                .body(user).
        when()
                .get("/login").
        then()
                .body("name", equalTo("Lasse"));
    }

    @AfterEach
    void tearDown() {

    }
}