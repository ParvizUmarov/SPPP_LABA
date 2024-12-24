package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Services;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface ServiceRepo {

    Services getByArg(String arg);
    boolean add(Services object);
    boolean delete(String name);
    boolean update(Object newValue, Object oldValue);
    List<Services> getAll();

    @Repository
    class Base implements ServiceRepo {


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
        public List<Services> getAll() {
            var query = em.createNamedQuery("getallservice", Services.class);
            query.setMaxResults(QUERY_LIMIT);
            return query.getResultList();
        }
    }

}
