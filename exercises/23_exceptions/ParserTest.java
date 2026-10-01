import dev.javaling.testkit.TestKit;

public class ParserTest {

    public static void main(String[] args) {
        TestKit.expectEquals("parse(\"42\")", 42, Parser.parse("42"));
        TestKit.expectEquals("parse(\"-7\")", -7, Parser.parse("-7"));

        TestKit.expectThrowsMessage("parse(\"abc\") 應丟出帶有說明的例外",
                IllegalArgumentException.class, "不是數字", () -> Parser.parse("abc"));
        TestKit.expectThrowsMessage("parse(\"12x\") 應丟出帶有說明的例外",
                IllegalArgumentException.class, "不是數字", () -> Parser.parse("12x"));

        TestKit.expectEquals("parseOrDefault(\"abc\", 5)", 5, Parser.parseOrDefault("abc", 5));
        TestKit.expectEquals("parseOrDefault(\"42\", 5)", 42, Parser.parseOrDefault("42", 5));
        TestKit.done();
    }
}
