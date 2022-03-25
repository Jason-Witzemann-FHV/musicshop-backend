package at.fhv.ae.shared.dto.release;

import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;

@Value
public class DetailedReleaseRemoteDTO implements Serializable {
    String title;
    double price;
    int stock;
    String medium;
    ArrayList<RecordingRemoteDTO> recordings;
}
