package at.fhv.ae.infrastructure;

import at.fhv.ae.domain.customer.Customer;
import at.fhv.ae.domain.repositories.CustomerRepository;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteCustomerRepositoryImpl extends UnicastRemoteObject implements CustomerRepository {

    private static final String connectionStr
            = "mongodb://AdminSammy:admin@10.0.40.161:27017/customer?authSource=admin";

    private final MongoCollection<Customer> customers;

    public RemoteCustomerRepositoryImpl() throws RemoteException {
        super();

        CodecRegistry cr = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        customers = MongoClients.create(connectionStr)
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
}
