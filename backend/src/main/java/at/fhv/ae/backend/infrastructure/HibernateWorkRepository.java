package at.fhv.ae.backend.infrastructure;


import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.model.work.RecordingId;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
public class HibernateWorkRepository implements WorkRepository {

    private final EntityManager em;

    @Override
    public List<Recording> findRecordings(List<RecordingId> recordingIds) {
        return em.createQuery("select distinct r from Recording r where r.recordingId in :recordingIds", Recording.class)
                .setParameter("recordingIds", recordingIds)
                .getResultList();
    }
}
