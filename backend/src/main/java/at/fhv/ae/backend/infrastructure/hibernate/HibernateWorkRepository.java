package at.fhv.ae.backend.infrastructure.hibernate;


import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
@AllArgsConstructor
@NoArgsConstructor
public class HibernateWorkRepository implements WorkRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Recording> findRecordings(List<RecordingId> recordingIds) {
        return em.createQuery("select distinct r from Recording r where r.recordingId in :recordingIds", Recording.class)
                .setParameter("recordingIds", recordingIds)
                .getResultList();
    }
}
