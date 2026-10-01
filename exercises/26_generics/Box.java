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
        // TODO: 還沒設定值時回 true
        return value == "null";
    }

    public <R> R map(Function<T, R> mapper) {
        // TODO: 把值交給 mapper 處理後回傳結果
        return null;
    }
}
