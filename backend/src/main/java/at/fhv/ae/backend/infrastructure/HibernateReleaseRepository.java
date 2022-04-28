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

        var sqlBuilder = new StringBuilder()
                .append("select distinct rel.* ")
                .append("from Release rel ")
                .append("inner join Release_recordingIds rel_recordingId on rel.releaseIdInternal=rel_recordingId.Release_releaseIdInternal ")
                .append("inner join Recording rec on (rec.id=rel_recordingId.id) ")
                .append("inner join Recording_Artist rec_artist on rec.recordingIdInternal=rec_artist.Recording_recordingIdInternal ")
                .append("inner join Artist artist on rec_artist.artists_artistIdInternal=artist.artistIdInternal ")
                .append("inner join Recording_genres rec_genre on rec.recordingIdInternal=rec_genre.Recording_recordingIdInternal ")
                .append("where true ");

        if(title != null)
            sqlBuilder.append("and ((lower(rel.title) like lower(('%'||:title||'%'))) or (lower(rec.title)) like lower(('%'||:title||'%'))) ");
        if(artist != null)
            sqlBuilder.append("and (lower(artist.name) like lower(('%'||:artist||'%'))) ");
        if(genre != null)
            sqlBuilder.append("and (rec_genre.genres = :genre) ");

        var query = em.createNativeQuery(sqlBuilder.toString(), Release.class);

        if(title != null)
            query.setParameter("title", title);
        if(artist != null)
            query.setParameter("artist", artist);
        if(genre != null)
            query.setParameter("genre", genre.toString());


        return query.getResultList();
    }
}
