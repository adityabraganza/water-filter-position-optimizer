package objects.visuals;

public class DoubleCell {
    static int largest_character_count = 0;

    private double value;

    private int character_count;

    public DoubleCell(){
        new IntCell(0);
    }

    public DoubleCell(double value){
        this.value = ((int) (value * 10)) /10;

        character_count = String.valueOf(this.value).toCharArray().length;

        if (character_count > largest_character_count){
            largest_character_count = character_count;
        }
    }

    public double get_value(){
        return this.value;
    }

    @Override
    public String toString() {

        StringBuilder string_builder = new StringBuilder();

        if (character_count < largest_character_count){
            string_builder.append(" ".repeat(Math.max(0, (largest_character_count - character_count))));
        }

        string_builder.append(value);

        return string_builder.toString();
    }
}
