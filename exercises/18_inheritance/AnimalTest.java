import dev.javaling.testkit.TestKit;

public class AnimalTest {

    public static void main(String[] args) {
        Animal dog = new Dog("Rex");
        TestKit.expectEquals("Dog.describe()", "Rex says Woof", dog.describe());
        TestKit.expectEquals("Cat.describe()", "Kit says Mew", new Cat("Kit").describe());
        TestKit.expectEquals("Animal.speak()", "...", new Animal("Thing").speak());

        // 多型：用父類別型別參考子類別物件
        Animal[] pets = {new Dog("A"), new Cat("B")};
        TestKit.expectEquals("多型陣列", "A says Woof", pets[0].describe());
        TestKit.expectEquals("多型陣列", "B says Mew", pets[1].describe());
        TestKit.done();
    }
}
