package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Value;

import java.util.UUID;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BasketItemDisplayDTO {
    UUID releaseId;
    String title;
    int quantity;
    int stock;
    String medium;
    double price;

    public static BasketItemDisplayDTO fromDomain(Release release, int quantity) {
        return new BasketItemDisplayDTO(release.releaseId().id(), release.title(), quantity, release.stock(), release.medium().friendlyName(), release.price());
    }
}
