package at.fhv.ae.shared.dto.release;

import lombok.Value;

import java.io.Serializable;

@Value
public class ReleaseSearchResultDTO implements Serializable {

    String id;
    String title;
    String medium;
    int stock;
    double price;
}
