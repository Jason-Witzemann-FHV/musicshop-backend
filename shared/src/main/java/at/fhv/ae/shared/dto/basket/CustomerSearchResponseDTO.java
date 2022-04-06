package at.fhv.ae.shared.dto.basket;

import at.fhv.ae.shared.dto.customer.Customer;
import lombok.Value;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Value
public class CustomerSearchResponseDTO implements Serializable  {

    ObjectId id;
    String givenName;
    String familyName;
    String address;

    public static CustomerSearchResponseDTO fromCustomer(Customer customer) {
        var address = customer.getAddress();
        var addressString = address.getStreetAddress() + " "
                + address.getHouseNumber() + " - "
                + address.getPostalCode() + " "
                + address.getAddressLocality() + ", "
                + address.getAddressCountry();
        return new CustomerSearchResponseDTO(customer.getId(), customer.getGivenName(), customer.getFamilyName(), addressString);
    }
}
