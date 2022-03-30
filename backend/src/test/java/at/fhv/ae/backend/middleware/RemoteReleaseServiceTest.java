package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.RecordingDTO;
import at.fhv.ae.backend.middleware.rmi.ReleaseSearchServiceImpl;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.*;

class RemoteReleaseServiceTest {

    private ReleaseService releaseService;
    private ReleaseSearchService remoteReleaseService;


    @BeforeEach
    void setupMocksAndTestClass() throws RemoteException {
        releaseService = mock(ReleaseService.class);
        remoteReleaseService = new ReleaseSearchServiceImpl(releaseService);
    }

    @Test
    void given_nothing_when_searched_for_release_then_call_application_service_and_finds_nothing() throws IllegalArgumentException {
        UUID testId = UUID.randomUUID();
        // Nullpointer, because Application layer throws error, but Application layer is mocked, therefore it returned null and caused NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> remoteReleaseService.getDetails(testId));
    }


    @Test
    void given_nothing_when_getting_result_then_confirm_call_on_application_layer() throws RemoteException, IllegalArgumentException {
        // Arrange
        UUID testId = UUID.randomUUID();
        ArrayList<String> artists = new ArrayList<>();
        artists.add("Hello");

        ArrayList<String> genres = new ArrayList<>();
        genres.add("World");

        ArrayList<RecordingDTO> recordings = new ArrayList<>();
        recordings.add(new RecordingDTO(
                "Some title",
                artists,
                genres,
                2000,
                66
        ));

        DetailedReleaseDTO testDTO = new DetailedReleaseDTO(
                "TestTitle",
                3.23,
                5,
                "CD",
                recordings
        );

        // Act
        when(releaseService.detailedInformation(testId)).thenReturn(testDTO);
        remoteReleaseService.getDetails(testId);

        // Assert
        verify(releaseService).detailedInformation(testId);
    }
}
