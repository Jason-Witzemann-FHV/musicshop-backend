package at.fhv.ae.playlist.domain;

import at.fhv.ae.playlist.application.PlaylistReleaseDTO;

import java.util.List;

public interface PlaylistRepository {

    List<PlaylistReleaseDTO> playlist();
}
