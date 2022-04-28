package at.fhv.ae.backend.infrastructure.inmemory;

import at.fhv.ae.backend.middleware.TokenRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
@Startup
public class HashMapTokenRepository implements TokenRepository {

    private Map<String, String> tokenStore; // userId is key, Token is values

    @PostConstruct
    void init() {
        tokenStore = new HashMap<>();
    }


    @Override
    public void setToken(String userId, String token) {
        tokenStore.put(userId, token);
    }

    @Override
    public Optional<String> userIdOfToken(String token) {
        return tokenStore.entrySet()
                .stream()
                .filter( entry -> entry.getValue().equals(token))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
