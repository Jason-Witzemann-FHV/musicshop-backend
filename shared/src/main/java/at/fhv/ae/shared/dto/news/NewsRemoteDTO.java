package at.fhv.ae.shared.dto.news;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class NewsRemoteDTO implements Serializable {
    String title;
    String message;
    LocalDateTime dateOfEvent;
    String topic;
    long publishedTimeStamp;
}
