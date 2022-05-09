package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.BasketRepository;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class BasketServiceImpl implements BasketService {

    @EJB
    private BasketRepository basketRepository;

    @EJB
    private ReleaseRepository releaseRepository;

    @EJB
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addItemToBasket(String userId, UUID releaseId, int quantity) {
        Release release =  releaseById(releaseId);
        var user = getUserById(userId);
        basketRepository.addItemToBasket(user.userId(), release, quantity);
    }

    @Override
    @Transactional
    public void changeQuantityOfItem(String userId, UUID releaseId, int newQuantity) {
        Release release =  releaseById(releaseId);
        var user = getUserById(userId);
        basketRepository.changeQuantityOfItem(user.userId(), release, newQuantity);
    }

    @Override
    @Transactional
    public void removeItemFromBasket(String userId, UUID releaseId) {
        Release release = releaseById(releaseId);
        var user = getUserById(userId);
        basketRepository.removeItemFromBasket(user.userId(), release);
    }

    @Override
    @Transactional
    public List<BasketItemDisplayDTO> itemsInBasket(String userId) {
        var user = getUserById(userId);
        return basketRepository.itemsInBasket(user.userId())
                .entrySet()
                .stream()
                .map(entry -> BasketItemDisplayDTO.fromDomain(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int amountOfItemsInBasket(String userId) {
        var user = getUserById(userId);
        return basketRepository.amountOfItemsInBasket(user.userId());
    }

    @Override
    @Transactional
    public void clearBasket(String userId) {
        var user = getUserById(userId);
        basketRepository.clearBasket(user.userId());
    }

    @Transactional
    private User getUserById(String userId) {
        return userRepository.userById(new UserId(userId)).orElseThrow(() -> new IllegalArgumentException("user with id " + userId + " was not found!"));
    }

    @Transactional
    private Release releaseById(UUID releaseId) {
        return releaseRepository.findById(new ReleaseId(releaseId)).orElseThrow(() -> new IllegalArgumentException("given Release with ID " + releaseId + " does not exist in Database!"));
    }

}
