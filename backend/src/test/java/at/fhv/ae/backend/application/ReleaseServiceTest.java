package at.fhv.ae.backend.application;


import at.fhv.ae.backend.application.impl.ReleaseServiceImpl;
import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.work.*;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReleaseServiceTest {

    private ReleaseService releaseService;
    private ReleaseRepository releaseRepository;
    private WorkRepository workRepository;

    @BeforeEach
    void prepareMocksAndTarget() {
        releaseRepository = mock(ReleaseRepository.class);
        workRepository = mock(WorkRepository.class);
        releaseService = new ReleaseServiceImpl(releaseRepository, workRepository);
    }

    @Test
    void given_one_releaseId_when_asking_for_detailed_info_then_return_dto() {
        // Arrange
        // Ids
        var releaseId = new ReleaseId(UUID.randomUUID());
        var recordingId1 = new RecordingId(UUID.randomUUID());
        var recordingId2 = new RecordingId(UUID.randomUUID());
        var recordingId3 = new RecordingId(UUID.randomUUID());
        List<Supplier> supplierList = List.of(new Supplier("Amazon", "Somewhere 12"));
        var recordingIds = List.of(recordingId1, recordingId2, recordingId3);

        // Recording Test Data
        var r1 = new Recording(
                recordingId1,
                "Suffering",
                5,
                2000,
                new Work("Suffer"),
                List.of(new Artist("DJ Khaled")),
                List.of(Genre.POP));
        var r2 = new Recording(
                recordingId2,
                "From",
                2,
                2001,
                new Work("From"),
                List.of(new Artist("Just Khaled")),
                List.of(Genre.RAP));
        var r3 = new Recording(
                recordingId3,
                "Success",
                1,
                2002,
                new Work("Success"),
                List.of(new Artist("DJ")),
                List.of(Genre.ROCK));

        // Optional release
        var release = Optional.of(new Release(
                releaseId,
                5,
                "Suffering from Success",
                Medium.CD,
                15.44,
                new Label("ABC Music", "ABC"),
                supplierList,
                recordingIds
        ));

        // Mock Returns
        when(releaseRepository.findById(releaseId)).thenReturn(release);
        when(workRepository.findRecordings(release.get().recordingIds())).thenReturn(List.of(r1, r2, r3));

        // Act
        releaseService.detailedInformation(releaseId);

        // Assert
        verify(releaseRepository).findById(releaseId);
        verify(workRepository).findRecordings(recordingIds);
    }
}
