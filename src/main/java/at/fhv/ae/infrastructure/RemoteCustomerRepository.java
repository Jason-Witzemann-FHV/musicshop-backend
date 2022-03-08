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
import java.util.Optional;

public class RemoteCustomerRepository extends UnicastRemoteObject implements CustomerRepository {

    private static final String connectionStr
            = "mongodb://AdminSammy:admin@10.0.40.161:27017/customer?authSource=admin";

    private final MongoCollection<Customer> customers;

    private static final int rmiPort = 8765;

    public RemoteCustomerRepository() throws RemoteException {
        super(rmiPort);

        CodecRegistry cr = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        customers = MongoClients.create(connectionStr)
                .getDatabase("customer")
                .getCollection("customers", Customer.class)
                .withCodecRegistry(cr);
    }

    @Override
    public Optional<Customer> find(String id) {

        return Optional.ofNullable(customers
                .find(new BsonDocument("_id", new BsonObjectId(new ObjectId(id))))
                .limit(1).cursor().tryNext());

    }
}
