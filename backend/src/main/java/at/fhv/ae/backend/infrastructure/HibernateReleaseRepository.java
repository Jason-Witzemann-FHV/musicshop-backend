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
        return em.createNativeQuery("select rel.releaseIdInternal, rel.label_labelIdInternal, rel.medium, rel.price, rel.id, rel.stock, rel.title "
                + "from Release rel "
                + "inner join Release_recordingIds rel_recordingId on rel.releaseIdInternal=rel_recordingId.Release_releaseIdInternal "
                + "inner join Recording rec on (rec.id=rel_recordingId.id) "
                + "inner join Recording_Artist rec_artist on rec.recordingIdInternal=rec_artist.Recording_recordingIdInternal "
                + "inner join Artist artist on rec_artist.artists_artistIdInternal=artist.artistIdInternal "
                + "where (lower(rel.title) like lower(('%'||?||'%'))) and (lower(artist.name) like lower(('%'||?||'%')))", Release.class)
                .setParameter(1, title)
                .setParameter(2, artist)
                .getResultList();

        // sollte eigentlich funktioniera tuts aber ned :)
        /*return em.createQuery("select rel from Release rel "
                        + "join rel.recordingIds recId "
                        + "join Recording rec on rec.recordingId = recId.id "
                        + "join rec.artists a "
                        + "where lower(rel.title) like lower(concat('%', :title, '%')) "
                        + "and lower(a.name) like lower(concat('%', :artist, '%')) "
                , Release.class)
                .setParameter("title", title)
                .setParameter("artist", artist)
                .getResultList();*/
    }
}
