package objects.person;

public class Person {
    private final double water_needed;

    public Person(double water_needed){
        this.water_needed = water_needed;
    }
    public double getWater_needed() {
        return water_needed;
    }

    @Override
    public String toString() {
        return "Person{" +
                "water_needed=" + water_needed +
                '}';
    }
}
