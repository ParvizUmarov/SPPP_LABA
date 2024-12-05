package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Barber;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class BarberRepo implements Repo<Barber> {

    @PersistenceContext
    private EntityManager em;
    private final static int QUERY_LIMIT = 10;

    @Override
    public Barber getByArg(String arg) {
        try {
            return em.createQuery("SELECT b FROM Barber b WHERE b.name = :arg", Barber.class)
                    .setParameter("arg", arg)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean add(Barber object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String name) {
        var barber = getByArg(name);
        em.remove(barber);
        return true;
    }

    @Override
    public boolean update(Object newValue, Object oldValue) {
        var barber = getByArg(oldValue.toString());
        if (barber != null) {
            barber.setName(newValue.toString());
            em.merge(barber);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Barber> getAll() {
        var query = em.createNamedQuery("getallbarber", Barber.class);
        query.setMaxResults(QUERY_LIMIT);
        return query.getResultList();
    }
}
