public class Hello {

    public static String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static String greet() {
        return greet("Javalings");
    }
}
