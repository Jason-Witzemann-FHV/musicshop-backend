package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.domain.model.work.*;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HibernateWorkRepositoryTest {

    private final WorkRepository workRepository = ServiceRegistry.workRepository();

    private final EntityManager em = ServiceRegistry.entityManager();

    @Test
    void given_many_recordings_when_getting_some_then_get_correct_recordings() {
        var id1 = new RecordingId(UUID.randomUUID());
        var id2 = new RecordingId(UUID.randomUUID());
        var id3 = new RecordingId(UUID.randomUUID());
        List<RecordingId> seachFor = List.of(id1, id2);

        var r1 = new Recording(
                    id1,
                    "Suffering",
                    5,
                    2000,
                    new Work("Suffer"),
                    List.of(new Artist("DJ Khaled")),
                    List.of(Genre.POP));
        var r2 = new Recording(
                    id2,
                    "From",
                    2,
                    2001,
                    new Work("From"),
                    List.of(new Artist("Just Khaled")),
                    List.of(Genre.RAP));
        var r3 = new Recording(
                    id3,
                    "Success",
                    1,
                    2002,
                    new Work("Success"),
                    List.of(new Artist("DJ")),
                    List.of(Genre.ROCK));

        List<Recording> recordings = List.of(r1, r2, r3);
        List<Recording> expected = List.of(r1, r2);

        var transaction = em.getTransaction();
        transaction.begin();
        for (var r:recordings) {
            em.persist(r);
        }
        em.flush();
        List<Recording> actual = workRepository.findRecordings(seachFor);
        transaction.rollback();

        assertFalse(actual.isEmpty());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void given_many_recordings_when_getting_one_then_get_correct_recording() {

        var id1 = new RecordingId(UUID.randomUUID());
        var id2 = new RecordingId(UUID.randomUUID());
        var id3 = new RecordingId(UUID.randomUUID());
        List<RecordingId> seachFor = List.of(id3);

        var r1 = new Recording(
                id1,
                "Suffering",
                5,
                2000,
                new Work("Suffer"),
                List.of(new Artist("DJ Khaled")),
                List.of(Genre.POP));
        var r2 = new Recording(
                id2,
                "From",
                2,
                2001,
                new Work("From"),
                List.of(new Artist("Just Khaled")),
                List.of(Genre.RAP));
        var r3 = new Recording(
                id3,
                "Success",
                1,
                2002,
                new Work("Success"),
                List.of(new Artist("DJ")),
                List.of(Genre.ROCK));

        List<Recording> recordings = List.of(r1, r2, r3);
        List<Recording> expected = List.of(r3);

        var transaction = em.getTransaction();
        transaction.begin();
        for (var r:recordings) {
            em.persist(r);
        }
        em.flush();
        List<Recording> actual = workRepository.findRecordings(seachFor);
        transaction.rollback();

        assertFalse(actual.isEmpty());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void given_one_recording_when_getting_more_then_return_only_one() {
        var id1 = new RecordingId(UUID.randomUUID());
        var id2 = new RecordingId(UUID.randomUUID());
        var id3 = new RecordingId(UUID.randomUUID());
        List<RecordingId> seachFor = List.of(id1, id2);

        var r1 = new Recording(
                id1,
                "Suffering",
                5,
                2000,
                new Work("Suffer"),
                List.of(new Artist("DJ Khaled")),
                List.of(Genre.POP));

        List<Recording> recordings = List.of(r1);
        List<Recording> expected = List.of(r1);

        var transaction = em.getTransaction();
        transaction.begin();
        for (var r:recordings) {
            em.persist(r);
        }
        em.flush();
        List<Recording> actual = workRepository.findRecordings(seachFor);
        transaction.rollback();

        assertFalse(actual.isEmpty());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void given_recordings_when_getting_wrong_then_get_empty_list() {

        var id1 = new RecordingId(UUID.randomUUID());
        var id2 = new RecordingId(UUID.randomUUID());
        var id3 = new RecordingId(UUID.randomUUID());
        List<RecordingId> seachFor = List.of(id3);

        var r1 = new Recording(
                id1,
                "Suffering",
                5,
                2000,
                new Work("Suffer"),
                List.of(new Artist("DJ Khaled")),
                List.of(Genre.POP));
        var r2 = new Recording(
                id2,
                "From",
                2,
                2001,
                new Work("From"),
                List.of(new Artist("Just Khaled")),
                List.of(Genre.RAP));

        List<Recording> recordings = List.of(r1, r2);
        List<Recording> expected = Collections.emptyList();

        var transaction = em.getTransaction();
        transaction.begin();
        for (var r:recordings) {
            em.persist(r);
        }
        em.flush();
        List<Recording> actual = workRepository.findRecordings(seachFor);
        transaction.rollback();

        assertTrue(actual.isEmpty());
    }
}
