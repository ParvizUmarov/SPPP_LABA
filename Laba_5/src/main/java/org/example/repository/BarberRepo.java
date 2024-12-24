package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Barber;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BarberRepo {

    Barber getByArg(String arg);

    void add(Barber object);

    void delete(String name);

    void update(Object newValue, Object oldValue);

    List<Barber> getAll();

    @Repository
    class Base implements BarberRepo {

        @PersistenceContext
        private EntityManager em;
        private final static int QUERY_LIMIT = 10;

        @Override
        public Barber getByArg(String arg) {
            try {
                var resultFromQuery = em.createQuery("SELECT b FROM Barber b WHERE b.name = :arg", Barber.class)
                        .setParameter("arg", arg);

                if (resultFromQuery == null) {
                    throw new IllegalArgumentException("barber not found");
                }

                return resultFromQuery.getSingleResult();
            } catch (Exception e) {
                return null;
            }
        }

        @Transactional
        @Override
        public void add(Barber object) {
            var barber = getByArg(object.getName());

            if (barber != null) {
                throw new IllegalArgumentException("Barber with name " + object.getName() + " is already exist");
            }
            em.persist(object);
        }

        @Transactional
        @Override
        public void delete(String name) {
            var barberList = getByArg(name);
            em.remove(barberList);
        }

        @Transactional
        @Override
        public void update(Object newValue, Object oldValue) {
            var barber = getByArg(oldValue.toString());
            if (barber != null) {
                barber.setName(newValue.toString());
                em.merge(barber);
            } else {
                throw new IllegalArgumentException("Barber doest exist");
            }
        }

        @Override
        public List<Barber> getAll() {
            var query = em.createNamedQuery("getallbarber", Barber.class);
            query.setMaxResults(QUERY_LIMIT);
            return query.getResultList();
        }
    }

}
