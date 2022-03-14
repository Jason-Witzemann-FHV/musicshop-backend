package at.fhv.ae.shared.dto.customer;

import lombok.Data;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
public class Customer implements Serializable {

    private static final long serialVersionUID = 5555_5555_5555_5555L;

    private ObjectId id;
    private Address address;
    private BankAccount bankAccount;
    private String birthDate;
    private CreditCard creditCard;
    private String email;
    private String eyecolor;
    private String familyName;
    private String gender;
    private String givenName;
    private int height;
    private String mobileNo;
    private String phoneNo;
    private String taxId;
}