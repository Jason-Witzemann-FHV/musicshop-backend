package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.work.*;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HibernateReleaseRepositoryTests {

    private final ReleaseRepository releaseRepository = ServiceRegistry.releaseRepository();

    private final EntityManager em = ServiceRegistry.entityManager();

    @Test
    void given_many_releases_when_get_one_then_get_correct_release() {
        var releaseId = new ReleaseId(UUID.randomUUID());
        List<Supplier> supplierList = List.of(new Supplier("Amazon", "Somewhere 12"));
        var recordingIds = List.of(new RecordingId(UUID.randomUUID()));
        var release = new Release(
                releaseId,
                5,
                "Suffering from Success",
                Medium.CD,
                15.44,
                new Label("ABC Music", "ABC"),
                supplierList,
                recordingIds
        );

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(release);
        em.flush();
        var actual = releaseRepository.findById(releaseId);
        transaction.rollback();

        assertTrue(actual.isPresent());
        assertEquals(release, actual.get());
    }


    @Test
    void given_many_releases_when_get_wrong_then_get_empty_optional() {
        var releaseId = new ReleaseId(UUID.randomUUID());
        List<Supplier> supplierList = List.of(new Supplier("Amazon", "Somewhere 12"));
        var recordingIds = List.of(new RecordingId(UUID.randomUUID()));
        var release = new Release(
                releaseId,
                5,
                "Suffering from Success",
                Medium.CD,
                15.44,
                new Label("ABC Music", "ABC"),
                supplierList,
                recordingIds
        );

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(release);
        em.flush();
        var actual = releaseRepository.findById(new ReleaseId(UUID.randomUUID()));
        transaction.rollback();

        assertTrue(actual.isEmpty());
    }

    @Test
    void given_some_releases_when_searching_byTitle_then_return_correct(){
        var releaseId = new ReleaseId(UUID.randomUUID());
        List<Supplier> supplierList = List.of(new Supplier("Amazonas", "Somewhere 69"));
        var recordingIds = List.of(new RecordingId(UUID.randomUUID()));

        Recording recording = new Recording(recordingIds.get(0), "Test", 12, 2020, new Work("KeineScheisse"), List.of(new Artist("Juergen")), List.of(Genre.ACOUSTIC));

        var release = new Release(
                releaseId,
                5,
                "Suffering",
                Medium.CD,
                13.44,
                new Label("CBA Music", "CBA"),
                supplierList,
                recordingIds
        );

        var release2 = new Release(
                releaseId,
                5,
                "UFF",
                Medium.CD,
                13.44,
                new Label("CBD Music", "CBD"),
                supplierList,
                recordingIds
        );

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(release);
        em.persist(release2);
        em.persist(recording);
        em.flush();


        var actual = releaseRepository.query("UFF", "", "");
        var actually = releaseRepository.query("UFF", "UERGEN", "Acoustic");


        transaction.rollback();

        assertTrue(actual.contains(release));
        assertTrue(actual.contains(release2));
        assertEquals(2, actual.size());

        assertTrue(actually.contains(release));
        assertTrue(actually.contains(release2));
        assertEquals(2, actually.size());

    }

    @Test
    void given_a_release_when_searching_for_a_release_that_does_not_exist_then_return_nothing(){
        var releaseId = new ReleaseId(UUID.randomUUID());
        List<Supplier> supplierList = List.of(new Supplier("Gorty", "Somewhere 69"));
        var recordingIds = List.of(new RecordingId(UUID.randomUUID()));

        Recording recording = new Recording(recordingIds.get(0), "Rec", 12, 2020, new Work("KeineScheisse"), List.of(new Artist("Juergen")), List.of(Genre.ACOUSTIC));

        var release = new Release(
                releaseId,
                5,
                "187 Gang",
                Medium.CD,
                13.44,
                new Label("Koks", "CBD"),
                supplierList,
                recordingIds
        );

        var transaction = em.getTransaction();
        transaction.begin();
        em.persist(release);
        em.persist(recording);
        em.flush();

        var actual = releaseRepository.query("781", "", "");

        transaction.rollback();

        assertFalse(actual.contains(release));
        assertEquals(0, actual.size());



    }
}
