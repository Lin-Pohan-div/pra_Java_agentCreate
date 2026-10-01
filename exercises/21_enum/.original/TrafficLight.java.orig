public enum TrafficLight {

    RED, YELLOW, GREEN;

    public int waitSeconds() {
        // TODO: 用 switch 運算式：紅燈 30、黃燈 5、綠燈 20
        return switch (this) {
            case RED -> 30;
            case YELLOW -> 5;
            case GREEN
        };
    }

    public static TrafficLight longestWait() {
        // TODO: 找出等待秒數最長的那個燈（應該是 RED）
        TrafficLight best = RED;
        for (TrafficLight light : values()) {
            if (light.waitSeconds() < best.waitSeconds()) {
                best = light;
            }
        }
        return best;
    }
}
