package at.fhv.ae.javafxfrontend;

import java.util.UUID;

public class QuantityColumnInfo {

    private final UUID productId;
    private final int min;
    private final int stock;
    private int value;

    public QuantityColumnInfo(UUID productId, int min, int stock, int initial) {

        this.productId = productId;
        this.min = min;
        this.stock = stock;
        this.value = initial;
    }

    public UUID productId() {
        return productId;
    }

    public int min() {
        return min;
    }

    public int stock() {
        return stock;
    }

    public int value() {
        return value;
    }

    public void setValue(int v) {
        if(v < min)
            throw new IllegalArgumentException();

        // can't increase quantity if it's already over the stock
        if(stock < value && v > value)
            throw new IllegalArgumentException();

        value = v;
    }
}
