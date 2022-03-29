package at.fhv.ae.backend.application;

import at.fhv.ae.backend.domain.model.work.Genre;

import java.util.List;

public interface GenreInfoService {

    List<Genre> knownGenres();
}
