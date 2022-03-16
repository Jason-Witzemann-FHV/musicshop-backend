package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
public class HibernateReleaseRepository implements ReleaseRepository {

    private final EntityManager em;

    @Override
    public Release findById(ReleaseId id) {
        return em.createQuery("select r from Release r where r.releaseId = :releaseId", Release.class)
                .setParameter("releaseId", id)
                .getSingleResult();
    }

    @Override
    public List<Release> query(String title, String artist) {
        return em.createQuery("select rel from Release rel "
                + "join rel.recordingIds recId "
                + "join Recording rec on recId = rec.recordingId "
                + "where lower(rel.title) like lower(concat('%', :title, '%')) "
                + "and lower(rec.artist) like lower(concat('%', :artist, '%'))"
                , Release.class)
                .setParameter("title", title)
                .setParameter("artist", artist)
                .getResultList();
    }
}
