package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import lombok.Value;

import java.util.UUID;

@Value
public class BasketItemDisplayDTO {
    UUID releaseId;
    String title;
    int quantity;
    String medium;
    double price;

    public static BasketItemDisplayDTO fromDomain(Release release, int quantity) {
        return new BasketItemDisplayDTO(release.releaseId().id(), release.title(), quantity, release.medium().friendlyName(), release.price());
    }
}
