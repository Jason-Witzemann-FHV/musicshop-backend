package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.model.work.RecordingId;

import java.util.List;

public interface WorkRepository {

    List<Recording> findRecordings(List<RecordingId> recordingIds);
}
