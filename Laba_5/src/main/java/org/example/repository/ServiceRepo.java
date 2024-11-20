package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Services;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public class ServiceRepo implements Repo<Services> {

    @PersistenceContext
    private EntityManager em;
    private final static int QUERY_LIMIT = 10;

    @Override
    public Services getByArg(String arg) {
        return em.find(Services.class, arg);
    }

    @Override
    public boolean add(Services object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String name) {
       var service = getByArg(name);
       em.remove(service);
       return true;
    }

    @Override
    public boolean update(Object newValue, Object oldValue) {
        var service = getByArg(oldValue.toString());
        if (service != null) {
            service.setName(newValue.toString());
            em.merge(service);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Services> getAll() {
        var query = em.createNamedQuery("getallservice", Services.class);
        query.setMaxResults(QUERY_LIMIT);
        return query.getResultList();
    }
}
