package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import lombok.Value;

@Value
public class ReleaseDTO {

    String title;
    String medium;
    int stock;
    double price;

    public static ReleaseDTO fromDomain(Release release) {
        return new ReleaseDTO(release.title(), release.medium().name(), release.stock(), release.price());
    }
}
