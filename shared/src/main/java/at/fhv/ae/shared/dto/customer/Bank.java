package at.fhv.ae.shared.dto.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class Bank implements Serializable {

    private static final long serialVersionUID = 2222_2222_2222_2222L;

    private String bankCode;
    private String bic;
    private String city;
    private String desc;
}