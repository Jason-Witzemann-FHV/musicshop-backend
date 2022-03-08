package at.fhv.ae.domain.customer;

public class BankAccount {

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