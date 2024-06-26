package daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import persistence.model.Event;

import java.util.List;

public class EventDAO extends AbstractDAO {

    private static EventDAO instance;
    private static EntityManagerFactory emf;

    public EventDAO(EntityManagerFactory emf, Class<Event> entityClass) {
        super(emf, entityClass);
    }

    public static EventDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EventDAO(emf, Event.class);
        }
        return instance;
    }

    public List<Event> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Event> query = em.createQuery("SELECT e FROM Event e", Event.class);
            return query.getResultList();
        }
    }
}
