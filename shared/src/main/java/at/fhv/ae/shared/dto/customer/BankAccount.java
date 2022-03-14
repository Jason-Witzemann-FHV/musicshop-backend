package at.fhv.ae.shared.dto.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 3333_3333_3333_3333L;

    private Bank bank;
    private String iban;
}