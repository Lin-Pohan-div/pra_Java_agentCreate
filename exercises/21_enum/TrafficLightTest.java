import dev.javaling.testkit.TestKit;

public class TrafficLightTest {

    public static void main(String[] args) {
        TestKit.expectEquals("RED.waitSeconds()", 30, TrafficLight.RED.waitSeconds());
        TestKit.expectEquals("YELLOW.waitSeconds()", 5, TrafficLight.YELLOW.waitSeconds());
        TestKit.expectEquals("GREEN.waitSeconds()", 20, TrafficLight.GREEN.waitSeconds());

        TestKit.expectEquals("longestWait()",
                TrafficLight.RED, TrafficLight.longestWait());
        TestKit.expectEquals("values().length", 3, TrafficLight.values().length);
        TestKit.done();
    }
}
