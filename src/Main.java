import objects.person.*;

import objects.visuals.DoubleCell;
import objects.visuals.IntCell;
import resources.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        int[] number_of_filters = new int[Constants.longest_reach + 1];
        SamplePopulation[][] population_data = generate_random_population(Constants.grid_rows, Constants.grid_columns);

        for (int i = 0; i < number_of_filters.length; i++){
            number_of_filters[i] = print_pattern(i, population_data);
        }

        for (int i = 0; i < number_of_filters.length; i++){
            System.out.println("For a reach radius of " + i + " there are " + number_of_filters[i] + " filters required");
        }

        DoubleCell[][] sample_population_pretty = new DoubleCell[population_data.length][population_data[0].length];

        for (int i = 0; i < population_data.length; i++){
            for (int j = 0; j < population_data[0].length; j++){
                sample_population_pretty[i][j] = new DoubleCell(population_data[i][j].total_water());
            }
        }

        for (DoubleCell[] rows: sample_population_pretty){
            System.out.println(Arrays.toString(rows));
        }
    }

    public static int print_pattern(int reach_radius, SamplePopulation[][] sample_population){
        int total_filters = 0;
        IntCell[][] values;

        if (reach_radius == 0){
            values = optimized_placements(sample_population);
        } else {
            values = optimized_placements(sample_population, reach_radius);
        }

        for (IntCell[] rows: values){
            System.out.println(Arrays.toString(rows));
            for (IntCell cell: rows){
                total_filters += cell.get_value();
            }
        }

        System.out.println();

        return total_filters;
    }

    public static double water_needed_by_area(SamplePopulation[][] population_data, int reach_radius, int core_row, int core_column){
        double return_value = 0;

        for (int i = core_row - reach_radius; (i <= core_row + reach_radius) && i < population_data.length; i++){
            for (int j = core_column - reach_radius; (j < core_column + reach_radius) && j < population_data[0].length; j++){
                return_value += population_data[i][j].total_water();
            }
        }

        return return_value;
    }
    public static IntCell[][] optimized_placements(SamplePopulation[][] population_data, int reach_radius){
        IntCell[][] return_arr = new IntCell[population_data.length][population_data[0].length];
        int reach_diameter = 2 * reach_radius;

        for (int i = 0; i < population_data.length; i++){
            for (int j = 0; j < population_data[0].length; j++){
                return_arr[i][j] = new IntCell();
            }
        }

        boolean row_is_divisible = population_data.length % (reach_diameter + 1) == 0;
        boolean column_is_divisible = population_data[0].length % (reach_diameter + 1) == 0;

        if (row_is_divisible && column_is_divisible){
            for (int i = reach_radius; i < population_data.length; i += reach_diameter){
                for (int j = reach_radius; j < population_data[0].length; j += reach_diameter){
                    return_arr[i][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, i, j))/Constants.water_produced)) + 1);
                }
            }
        } else if (row_is_divisible){
            for (int i = reach_radius; i < population_data.length - (population_data.length % (reach_diameter + 1)); i += reach_diameter){
                for (int j = reach_radius; j < population_data[0].length; j += reach_diameter){
                    return_arr[i][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, i, j))/Constants.water_produced)) + 1);
                }
            }

            int temp_val = population_data.length - 1;
            for (int j = reach_radius; j < population_data[0].length; j += reach_diameter){
                return_arr[temp_val][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, temp_val, j))/Constants.water_produced)) + 1);
            }
        } else if (column_is_divisible){
            for (int i = reach_radius; i < population_data.length; i += reach_diameter){
                for (int j = reach_radius; j < population_data[0].length - (population_data.length % (reach_diameter + 1)); j += reach_diameter){
                    return_arr[i][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, i, j))/Constants.water_produced)) + 1);
                }
            }

            int temp_val = population_data[0].length - 1;
            for (int j = reach_radius; j < population_data.length - (population_data.length % (reach_diameter + 1)); j += reach_diameter){
                return_arr[j][temp_val] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, temp_val, j))/Constants.water_produced)) + 1);
            }
        } else{
            for (int i = reach_radius; i < population_data.length; i += reach_diameter){
                for (int j = reach_radius; j < population_data[0].length - (population_data.length % (reach_diameter + 1)); j += reach_diameter){
                    return_arr[i][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, i, j))/Constants.water_produced)) + 1);
                }
            }

            for (int i = reach_radius; i < population_data[0].length - (population_data.length % (reach_diameter + 1)); i += reach_diameter){
                for (int j = reach_radius; j < population_data.length - (population_data.length % (reach_diameter + 1)); j += reach_diameter){
                    return_arr[i][j] = new IntCell(((int) ((water_needed_by_area(population_data, reach_radius, i, j))/Constants.water_produced)) + 1);
                }
            }
        }

        return return_arr;
    }
    public static IntCell[][] optimized_placements(SamplePopulation[][] population_data){
        IntCell[][] return_arr = new IntCell[population_data.length][population_data[0].length];

        for (int i = 0; i < population_data.length; i++){
            for (int j = 0; j < population_data[0].length; j++){
                double total_water = population_data[i][j].total_water();
                return_arr[i][j] = new IntCell(((int) (total_water/Constants.water_produced)) + 1);
            }
        }

        for (SamplePopulation[] sample_population_row: population_data){
            ArrayList<Double> water_needed_array = new ArrayList<>();

            for (SamplePopulation sample_population_cell: sample_population_row){
                water_needed_array.add(sample_population_cell.total_water());
            }

            System.out.println(Arrays.toString(water_needed_array.toArray()));
        }

        return return_arr;
    }
    public static SamplePopulation[][] generate_random_population(int row_count, int column_count){
        SamplePopulation[][] return_arr = new SamplePopulation[row_count][column_count];

        for (int i = 0; i < return_arr.length; i++){
            for (int j = 0; j < return_arr[0].length; j++){
                return_arr[i][j] = new SamplePopulation(generate_random_population_sample());
            }
        }

        return return_arr;
    }

    public static List<Person> generate_random_population_sample(){
        List<Person> return_arr = new ArrayList<>();

        for (int i = 0; i < ((int) Math.round(Math.random() * 40) + 10); i++) {
            int random_person_type = (int) Math.round(Math.random() * 5);

            switch (random_person_type) {
                case 0 -> return_arr.add(new AdultFemale());
                case 1 -> return_arr.add(new AdultMale());
                case 2 -> return_arr.add(new ChildFemale());
                case 3 -> return_arr.add(new ChildMale());
                case 4 -> return_arr.add(new InfantFemale());
                case 5 -> return_arr.add(new InfantMale());
            }
        }
        return return_arr;
    }
}