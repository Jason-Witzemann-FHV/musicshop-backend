package at.fhv.ae.shared.dto.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreditCard implements Serializable {

    private static final long serialVersionUID = 4444_4444_4444_4444L;

    private String cvc;
    private String number;
    private String type;
}