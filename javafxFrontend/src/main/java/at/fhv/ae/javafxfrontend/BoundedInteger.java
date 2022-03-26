package at.fhv.ae.javafxfrontend;

import java.util.function.Supplier;

public class BoundedInteger {

    private final Supplier<Integer> min;
    private final Supplier<Integer> max;
    private int value;

    public BoundedInteger(Supplier<Integer> min, Supplier<Integer> max, int initial) {
        this.min = min;
        this.max = max;
        this.value = initial;
    }


    public int min() {
        return min.get();
    }

    public int max() {
        return max.get();
    }

    public int value() {
        return value;
    }

    public void setValue(int v) {
        if(v < min.get() || v > max.get())
            throw new IllegalArgumentException();
        value = v;
    }
}
