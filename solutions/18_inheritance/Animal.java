public class Animal {

    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    public String speak() {
        return "...";
    }

    public String describe() {
        return name + " says " + speak();
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
        return "Mew";
    }
}
