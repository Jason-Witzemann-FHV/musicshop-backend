package at.fhv.ae.customer.infrastructure;

import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.dto.customer.Customer;
import at.fhv.ae.shared.repository.CustomerRepository;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RemoteCustomerRepositoryImpl extends UnicastRemoteObject implements CustomerRepository {

    private static final String CONNECTION_STR = "mongodb://AdminSammy:admin@10.0.40.161:27017/customer?authSource=admin";

    private final MongoCollection<Customer> customers;

    public RemoteCustomerRepositoryImpl() throws RemoteException {
        super();

        CodecRegistry cr = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        this.customers = MongoClients.create(CONNECTION_STR)
                .getDatabase("customer")
                .getCollection("customers", Customer.class)
                .withCodecRegistry(cr);
    }

    @Override
    public Customer find(String id) {

        return customers
                .find(new BsonDocument("_id", new BsonObjectId(new ObjectId(id))))
                .limit(1).cursor().tryNext();

    }

    @Override
    public List<CustomerSearchResponseDTO> findByName(String firstName, String lastName) {
        Pattern firstNamePattern = Pattern.compile(firstName, Pattern.CASE_INSENSITIVE);
        Pattern lastNamePattern =  Pattern.compile(lastName, Pattern.CASE_INSENSITIVE);
        Bson firstNameFilter = Filters.regex("givenName", firstNamePattern);
        Bson lastNameFilter = Filters.regex("familyName", lastNamePattern);
        return customers
                .find(Filters.and(firstNameFilter, lastNameFilter))
                .limit(20)
                .map(CustomerSearchResponseDTO::fromCustomer)
                .into(new ArrayList<>());

    }
}
