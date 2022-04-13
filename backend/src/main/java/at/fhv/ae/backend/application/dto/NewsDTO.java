package at.fhv.ae.backend.application.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class NewsDTO {

    String title;
    String message;
    LocalDateTime dateOfEvent;
    String topic;


}
