package at.fhv.ae.backend.infrastructure.hibernate;


import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.WorkRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;


@Stateless
public class HibernateWorkRepository implements WorkRepository {

    private final EntityManager em = ServiceRegistry.entityManager();

    @Override
    public List<Recording> findRecordings(List<RecordingId> recordingIds) {
        return em.createQuery("select distinct r from Recording r where r.recordingId in :recordingIds", Recording.class)
                .setParameter("recordingIds", recordingIds)
                .getResultList();
    }
}
