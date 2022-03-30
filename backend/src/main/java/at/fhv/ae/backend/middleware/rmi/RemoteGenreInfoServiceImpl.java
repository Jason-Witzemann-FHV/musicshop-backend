package at.fhv.ae.backend.middleware.rmi;

import at.fhv.ae.backend.application.GenreInfoService;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.shared.rmi.RemoteGenreInfoService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class RemoteGenreInfoServiceImpl extends UnicastRemoteObject implements RemoteGenreInfoService {

    private final GenreInfoService genreInfoService;

    public RemoteGenreInfoServiceImpl(GenreInfoService genreInfoService) throws RemoteException {
        this.genreInfoService = genreInfoService;
    }

    @Override
    public List<String> knownGenres() throws RemoteException {
        return genreInfoService.knownGenres()
                .stream()
                .map(Genre::friendlyName)
                .collect(Collectors.toList());
    }
}
