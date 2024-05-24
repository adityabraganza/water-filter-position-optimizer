import objects.person.Person;

import java.util.Arrays;
import java.util.List;

public class SamplePopulation {
    private List<Person> sample_population;

    public SamplePopulation(List<Person> sample_population){
        this.sample_population = sample_population;
    }

    public List<Person> getSample_population() {
        return sample_population;
    }

    public double total_water(){
        double return_double = 0;

        for (Person person: sample_population){
            return_double += person.getWater_needed();
        }

        return return_double;
    }

    @Override
    public String toString() {
        return "SamplePopulation{" +
                "sample_population=" + Arrays.toString(sample_population.toArray()) +
                '}';
    }
}
