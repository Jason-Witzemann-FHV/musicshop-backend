package at.fhv.ae.shared.dto.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1111_1111_1111_1111L;

    private String addressCountry;
    private String addressLocality;
    private String houseNumber;
    private String postalCode;
    private String streetAddress;
}