package at.fhv.ae.domain.customer;

import java.io.Serializable;

public class CreditCard implements Serializable {

    private String cvc;
    private String number;
    private String type;

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}