public class Animal {

    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    public String speak() {
        return "...";
    }

    public String describe() {
        // TODO: 應該是 "Rex says Woof"，注意只講一次
        return name + " says " + speak() + speak();
    }
}

class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public String speak() {
        return "Woof";
    }
}

class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }

    @Override
    public String speak() {
        return "Mew"
    }
}
