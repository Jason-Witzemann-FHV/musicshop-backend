package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import lombok.Value;

@Value
public class ReleaseDTO {

    String id;
    String title;
    String medium;
    int stock;
    double price;

    public static ReleaseDTO fromDomain(Release rel) {
        return new ReleaseDTO(
                rel.releaseId().toString(),
                rel.title(),
                rel.medium().name(),
                rel.stock(),
                rel.price());
    }
}
