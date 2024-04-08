package daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import persistence.model.Registration;

import java.util.List;

public class RegistrationDAO extends AbstractDAO{
    private static RegistrationDAO instance;
    private static EntityManagerFactory emf;

    public RegistrationDAO(EntityManagerFactory emf, Class<Registration> entityClass) {
        super(emf, entityClass);
    }

    public static RegistrationDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RegistrationDAO(emf, Registration.class);
        }
        return instance;
    }

    public List<Registration> readAllByEventId(int eventId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Registration> query = em.createQuery("SELECT r From Registration r JOIN r.event WHERE r.event.id =:eventId", Registration.class);
            query.setParameter("eventId", eventId);
            return query.getResultList();
        }
    }

    public List<Registration> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Registration> query = em.createQuery("SELECT r From Registration r", Registration.class);
            return query.getResultList();
        }
    }

    public Registration getRegistrationByUserIdAndEventId(int userId, int eventId){

        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Registration> query = em.createQuery("Select r From Registration r " +
                    "join r.user " +
                    "join r.event where r.user.id=:userId and r.event.id=:eventId", Registration.class);
            query.setParameter("userId",userId);
            query.setParameter("eventId",eventId);
            return query.getSingleResult();
        }
    }
}