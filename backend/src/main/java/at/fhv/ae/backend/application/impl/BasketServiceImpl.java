package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    private final ReleaseRepository releaseRepository;

    @Override
    public void addItemToBasket(UUID releaseId, int quantity) {
        ReleaseId id = new ReleaseId(releaseId);
        Release release = releaseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("given Release with ID " + releaseId + " does not exist in Database!"));
        basketRepository.addItemToBasket(release, quantity);
    }

    @Override
    public void changeQuantityOfItem(UUID releaseId, int newQuantity) {
        ReleaseId id = new ReleaseId(releaseId);
        Release release = releaseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("given Release with ID " + releaseId + " does not exist in Database!"));
        basketRepository.changeQuantityOfItem(release, newQuantity);
    }

    @Override
    public void removeItemFromBasket(UUID releaseId) {
        ReleaseId id = new ReleaseId(releaseId);
        Release release = releaseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("given Release with ID " + releaseId + " does not exist in Database!"));
        basketRepository.removeItemFromBasket(release);
    }

    @Override
    public List<BasketItemDisplayDTO> itemsInBasket() {
        return basketRepository.itemsInBasket()
                .entrySet()
                .stream()
                .map(entry -> BasketItemDisplayDTO.fromDomain(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public int amountOfItemsInBasket() {
        return basketRepository.amountOfItemsInBasket();
    }
}
