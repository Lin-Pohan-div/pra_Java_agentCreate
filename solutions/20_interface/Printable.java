public interface Printable {

    public String label();

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
        return "<" + text + ">";
    }
}
