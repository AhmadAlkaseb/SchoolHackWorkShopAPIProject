package daos;

import dtos.RegistrationDTO;
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

//    registrations only ID's
//    public List<Registration> readAll2() {
//        try (EntityManager em = emf.createEntityManager()) {
//            TypedQuery<Registration> query = em.createQuery("SELECT r FROM Registration r", Registration.class);
//            return query.getResultList();
//        }
//    }


//    public List<RegistrationDTO> readAll() {
//        try (EntityManager em = emf.createEntityManager()) {
//
//            String jpql = "SELECT new dtos.RegistrationDTO(r.id, u.id, u.name, e.id, e.description) " +
//                    "FROM Registration r " +
//                    "JOIN r.user u " +
//                    "JOIN r.event e";
//
//            TypedQuery<RegistrationDTO> query = em.createQuery(jpql, RegistrationDTO.class);
//            return query.getResultList();
//        }
//    }

    public List<Registration> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Registration> query = em.createQuery("SELECT r From Registration r", Registration.class);
            return query.getResultList();
        }
    }
}