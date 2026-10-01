public class Counter {

    // TODO: 這個欄位要放「已經建立幾個 Counter 物件」，需要 static
    private int instances = 0;

    // TODO: 這個欄位要放「這一個物件自己的數值」，不應該 static
    private static int value = 0;

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
