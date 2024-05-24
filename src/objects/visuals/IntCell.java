package objects.visuals;

public class IntCell {
    static int largest_character_count = 0;

    private int value;

    private int character_count = 0;

    public IntCell(){
        new IntCell(0);
    }

    public IntCell(int value){
        this.value = value;

        int temp_int = value;

        while(temp_int / 10 != 0){
            temp_int = temp_int / 10;
            character_count++;
        }

        if (character_count > largest_character_count){
            largest_character_count = character_count;
        }
    }

    public int get_value(){
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
