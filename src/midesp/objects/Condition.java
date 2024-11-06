package midesp.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {
    private int[] conditions;

    public Condition() {
        
    }
    
    public int[] getConditions() {
        return conditions;
    }

    public void binConditions(int binSize) {
        for (int i = 0; i < conditions.length; i++) {
            conditions[i] = (int) Math.ceil((double)conditions[i] / binSize);
        }
    }
    
    // expected format: one line per person, one col per condition, space-separated
	public static List<Condition> readConditions(Path conditionsFile, int binSize) throws IOException {
        // Read the file and parse it into a 2D array where each row is a line from the file
        int[][] conditions = Files.lines(conditionsFile)
                .map(line -> Arrays.stream(line.split(" "))
                                .mapToInt(Integer::parseInt)
                                .toArray())
                .toArray(int[][]::new);

        // Get the number of rows and columns
        int numRows = conditions.length;
        int numCols = numRows > 0 ? conditions[0].length : 0;

        // Create a list to store each Condition object (one per column)
        List<Condition> conditionList = new ArrayList<>();

        // Iterate over columns to create Condition objects
        for (int col = 0; col < numCols; col++) {
            // Create an array for the current column
            int[] columnData = new int[numRows];
            for (int row = 0; row < numRows; row++) {
                columnData[row] = conditions[row][col];
            }
            // Create a Condition object for the column, set its data, and bin it
            Condition condition = new Condition();
            condition.conditions = columnData;
            condition.binConditions(binSize);

            // Add the Condition object to the list
            conditionList.add(condition);
        }
        return conditionList;
    }
}
