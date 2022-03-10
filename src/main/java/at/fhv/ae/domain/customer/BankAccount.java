package at.fhv.ae.domain.customer;

import java.io.Serializable;

public class BankAccount implements Serializable {

    private Bank bank;
    private String iban;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}