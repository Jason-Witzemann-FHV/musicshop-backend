package at.fhv.ae.shared.dto.release;

import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;

@Value
public class RecordingRemoteDTO implements Serializable {
    String title;
    ArrayList<String> artists;
    ArrayList<String> genres;
    int year;
    int duration;
}
