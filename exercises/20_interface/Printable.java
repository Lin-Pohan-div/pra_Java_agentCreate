public interface Printable {

    // TODO: 這是一個抽象方法：只有宣告、沒有本體，結尾用分號
    public String label()

    default String framed() {
        return "[" + label() + "]";
    }

    static Printable of(String text) {
        return () -> text;
    }
}

class Tag implements Printable {

    private final String text;

    public Tag(String text) {
        this.text = text;
    }

    @Override
    public String label() {
        // TODO: 回傳 "<" + text + ">"，外層中括號由 framed() 負責
        return "<" + text;
    }
}
