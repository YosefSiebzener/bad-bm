package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.observer.ObservableInterface;
import edu.touro.mco152.bm.observer.ObserverInterface;
import jakarta.persistence.EntityManager;

public class PersistenceObserver implements ObserverInterface {

    @Override
    public void addSelfToObservable(ObservableInterface oi) {
        oi.registerObserver(this);
    }

    @Override
    public void update(DiskRun run) {
        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();
    }
}
