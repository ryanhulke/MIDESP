package midesp.methods;

import infodynamics.measures.continuous.kraskov.ConditionalMutualInfoCalculatorMultiVariateKraskov2;

public class CMICalculator {
    
    private static double[][] arrToCol(int[] arr){
        double[][] col = new double[arr.length][1];
        for(int i = 0; i < arr.length; i++){
            col[i][0] = arr[i];
        }
        return col;
    }

    public static double calcCMI(int[] genotypes, int[] phenotypes, double[][] conditions, int k){
        double[][] geno = arrToCol(genotypes);
        double[][] pheno = arrToCol(phenotypes);
        
        double cmi = 0;
        try{
            ConditionalMutualInfoCalculatorMultiVariateKraskov2 cmiCalc = new ConditionalMutualInfoCalculatorMultiVariateKraskov2();
            cmiCalc.setProperty("k", Integer.toString(k));
            cmiCalc.initialise(1, 1, conditions[0].length);
            cmiCalc.setObservations(geno, pheno, conditions);
            cmi = cmiCalc.computeAverageLocalOfObservations() / Math.log(2); // convert from nats to bits
        }catch(Exception e){
            System.err.println("Error calculating CMI: " + e.getMessage());
        }
        return cmi;
    }
}
