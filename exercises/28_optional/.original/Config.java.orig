// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Config {

    private final Map<String, String> values;

    public Config(Map<String, String> values) {
        this.values = new HashMap<>(values);
    }

    public Optional<String> get(String key) {
        // TODO: 沒有這個 key 時回傳 Optional.empty()
        return Optional.ofNullable(values.get(key))
    }

    public String getOrDefault(String key, String fallback) {
        return get(key).orElse(fallback);
    }

    public String require(String key) {
        // TODO: 沒有這個 key 時丟出 IllegalArgumentException，訊息要包含 key
        return get(key).get();
    }
}
