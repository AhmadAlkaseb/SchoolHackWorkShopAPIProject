package daos;

import jakarta.persistence.EntityManagerFactory;
import persistence.model.User;

public class UserDAO extends AbstractDAO {
    private static UserDAO instance;
    private static EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf, Class<User> entityClass) {
        super(emf, entityClass);
    }

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO(emf, User.class);
        }
        return instance;
    }
}
