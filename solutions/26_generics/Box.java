import java.util.function.Function;

public class Box<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public <R> R map(Function<T, R> mapper) {
        return mapper.apply(value);
    }
}
