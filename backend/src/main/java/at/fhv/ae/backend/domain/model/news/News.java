package at.fhv.ae.backend.domain.model.news;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class News {
    String topic;
    String title;
    String body;
    LocalDateTime dateOfEvent;
}
