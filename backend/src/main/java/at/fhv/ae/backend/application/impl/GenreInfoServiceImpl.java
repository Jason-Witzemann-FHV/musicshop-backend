package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.GenreInfoService;
import at.fhv.ae.backend.domain.model.work.Genre;

import java.util.List;

public class GenreInfoServiceImpl implements GenreInfoService {
    @Override
    public List<Genre> knownGenres() {
        return List.of(Genre.values());
    }
}
