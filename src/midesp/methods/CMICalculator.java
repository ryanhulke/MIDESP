package midesp.methods;

import infodynamics.measures.discrete.ConditionalMutualInformationCalculatorDiscrete;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import midesp.objects.Condition;
import midesp.objects.Phenotype;
import midesp.objects.SNP;

public class CMICalculator {
    // assumes discrete phenotypes and conditions
    public static double calcCMI(List<Condition> conditions, Phenotype pheno, SNP... snps){
        
        int[] phenotypes = pheno.getDiscPhenotype(); // binary phenotype
        
        int[][] conditionsArr = new int[conditions.size()][conditions.get(0).getConditions().length];
        
        for (int i = 0; i < conditions.size(); i++) {
            conditionsArr[i] = conditions.get(i).getConditions();
        }

        // map combinations of conditions to unique indices
        int[] mappedConditions = mapColumnsToUniqueIndices(conditionsArr); 
        
        // get snps into 2D array
        int[][] geno = new int[snps.length][];
        for (int i = 0; i < snps.length; i++) {
            geno[i] = snps[i].getGenotypes();
        }

        // map snps to unique indices
        int[] mappedGeno = mapColumnsToUniqueIndices(transpose(geno));

        // get number of different snp combinations
        int base1 = snps.length * 3; // 3 genotypes per snp
     
        int numPhenotypes = 2; // binary phenotype

        // get number of different condition combinations by using the unique indices
        int numConditions = Arrays.stream(mappedConditions).max().getAsInt() + 1;
        
        // calculate CMI
        double cmi = 0;
        try{
            ConditionalMutualInformationCalculatorDiscrete cmiCalc = new ConditionalMutualInformationCalculatorDiscrete(base1, numPhenotypes, numConditions);
            cmi = cmiCalc.computeAverageLocal(mappedGeno, phenotypes, mappedConditions);
        }catch(Exception e){
            System.err.println("Error calculating CMI: " + e.getMessage());
        }
        return cmi;
    }
    
    /**
     * Maps each unique column in the transposed 2D array to a unique index using List<Integer> as keys.
     *
     * @param data The input transposed 2D array where each int[] represents an attribute across examples.
     * @return A 1D array where each element is the index of the unique column.
     */
    private static int[] mapColumnsToUniqueIndices(int[][] data) {
        if (data == null || data.length == 0) {
            return new int[0];
        }

        int numAttributes = data.length;
        int numExamples = data[0].length;

        // Validate that all rows have the same number of columns
        for (int i = 1; i < numAttributes; i++) {
            if (data[i].length != numExamples) {
                throw new IllegalArgumentException("All attribute rows must have the same number of examples.");
            }
        }

        // Map to store unique column combinations and their assigned indices
        Map<List<Integer>, Integer> uniqueColumnMap = new HashMap<>();
        int[] indices = new int[numExamples];
        int currentIndex = 0;

        for (int col = 0; col < numExamples; col++) {
            // Create a key for the current column by collecting all attribute values
            List<Integer> columnKey = new ArrayList<>();
            for (int attr = 0; attr < numAttributes; attr++) {
                columnKey.add(data[attr][col]);
            }

            // Assign an index to the unique column
            if (!uniqueColumnMap.containsKey(columnKey)) {
                uniqueColumnMap.put(columnKey, currentIndex);
                currentIndex++;
            }

            // Populate the indices array
            indices[col] = uniqueColumnMap.get(columnKey);
        }

        return indices;
    }
    private static int[][] transpose(int[][] matrix) {
        if (matrix.length == 0) return new int[0][0];
        int[][] transposed = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
}