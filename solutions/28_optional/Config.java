import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Config {

    private final Map<String, String> values;

    public Config(Map<String, String> values) {
        this.values = new HashMap<>(values);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(values.get(key));
    }

    public String getOrDefault(String key, String fallback) {
        return get(key).orElse(fallback);
    }

    public String require(String key) {
        Optional<String> found = get(key);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("缺少必要的設定: " + key);
        }
        return found.get();
    }
}
