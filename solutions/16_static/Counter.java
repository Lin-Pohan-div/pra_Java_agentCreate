public class Counter {

    private static int instances = 0;

    private int value = 0;

    public Counter() {
        instances++;
    }

    public void increment() {
        value++;
    }

    public int value() {
        return value;
    }

    public static int instances() {
        return instances;
    }
}
