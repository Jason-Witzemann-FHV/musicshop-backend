package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HibernateReleaseRepository implements ReleaseRepository {

    private final EntityManager em;

    @Override
    public Optional<Release> findById(ReleaseId id) {
        return em.createQuery("select r from Release r where r.releaseId = :releaseId", Release.class)
                .setParameter("releaseId", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Release> query(String title, String artist, Genre genre) {

        var string = new StringBuilder();
        string.append("select distinct rel.* ");
        string.append("from Release rel ");
        string.append("inner join Release_recordingIds rel_recordingId on rel.releaseIdInternal=rel_recordingId.Release_releaseIdInternal ");
        string.append("inner join Recording rec on (rec.id=rel_recordingId.id) ");
        string.append("inner join Recording_Artist rec_artist on rec.recordingIdInternal=rec_artist.Recording_recordingIdInternal ");
        string.append("inner join Artist artist on rec_artist.artists_artistIdInternal=artist.artistIdInternal ");
        string.append("inner join Recording_genres rec_genre on rec.recordingIdInternal=rec_genre.Recording_recordingIdInternal ");
        string.append("where ((lower(rel.title) like lower(('%'||:title||'%'))) or (lower(rec.title)) like lower(('%'||:title||'%'))) ");
        string.append("and (lower(artist.name) like lower(('%'||:artist||'%'))) ");

        if (genre != null) {
            string.append("and rec_genre.genres = :genre");
        }

        var query = em.createNativeQuery(string.toString(), Release.class);
        query.setParameter("title", title);
        query.setParameter("artist", artist);

        if (genre != null) {
            query.setParameter("genre", genre.toString());
        }

        return query.getResultList();
    }
}
