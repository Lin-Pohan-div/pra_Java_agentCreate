public enum TrafficLight {

    RED, YELLOW, GREEN;

    public int waitSeconds() {
        return switch (this) {
            case RED -> 30;
            case YELLOW -> 5;
            case GREEN -> 20;
        };
    }

    public static TrafficLight longestWait() {
        TrafficLight best = RED;
        for (TrafficLight light : values()) {
            if (light.waitSeconds() > best.waitSeconds()) {
                best = light;
            }
        }
        return best;
    }
}
