import dev.javaling.testkit.TestKit;

import java.util.Map;
import java.util.Optional;

public class ConfigTest {

    public static void main(String[] args) {
        Config config = new Config(Map.of("host", "localhost", "port", "8080"));

        TestKit.expectEquals("get 存在的 key",
                Optional.of("localhost"), config.get("host"));
        TestKit.expectTrue("get 不存在的 key 應該是 empty", config.get("nope").isEmpty());

        TestKit.expectEquals("getOrDefault 命中", "8080", config.getOrDefault("port", "80"));
        TestKit.expectEquals("getOrDefault 沒命中", "80", config.getOrDefault("nope", "80"));

        TestKit.expectEquals("require 命中", "localhost", config.require("host"));
        TestKit.expectThrowsMessage("require 缺設定時要丟出有說明的例外",
                IllegalArgumentException.class, "nope", () -> config.require("nope"));
        TestKit.done();
    }
}
