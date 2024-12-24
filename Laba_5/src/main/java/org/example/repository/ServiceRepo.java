package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Services;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServiceRepo {

    Services getByArg(String arg);

    void add(Services object);

    void delete(String name);

    void update(int newPrice, String name);

    List<Services> getAll();

    @Repository
    class Base implements ServiceRepo {


        @PersistenceContext
        private EntityManager em;
        private final static int QUERY_LIMIT = 10;

        @Override
        public Services getByArg(String arg) {
            try {
                var resultFromQuery = em.createQuery("SELECT s FROM Services s WHERE s.name = :arg", Services.class)
                        .setParameter("arg", arg);
                return resultFromQuery.getSingleResult();
            } catch (Exception e) {
                return null;
            }
        }

        @Transactional
        @Override
        public void add(Services object) {
            var services = getByArg(object.getName());

            if (services != null) {
                throw new IllegalArgumentException("Service with name " + object.getName() + " is already exist");
            }
            em.persist(object);
        }

        @Transactional
        @Override
        public void delete(String name) {
            var service = getByArg(name);

            if (service == null) {
                throw new IllegalArgumentException("Service doesn't exist");
            }

            em.remove(service);
        }

        @Transactional
        @Override
        public void update(int newPrice, String name) {
            var service = getByArg(name);
            if (service != null) {
                service.setPrice(newPrice);
                em.merge(service);
            } else {
                throw new IllegalArgumentException("service doesn't exist");
            }

        }

        @Override
        public List<Services> getAll() {
            var query = em.createNamedQuery("getallservice", Services.class);
            query.setMaxResults(QUERY_LIMIT);
            return query.getResultList();
        }

    }

}
