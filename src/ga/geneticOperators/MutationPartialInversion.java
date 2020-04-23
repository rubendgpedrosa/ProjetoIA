package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationPartialInversion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationPartialInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //Get number of genes and create new instance of vars to hold random numbers which indicate where we cut
        int numGenes = ind.getNumGenes();
        int random1, random2, j;

        //Clone the individual for later use
        I ind2 = (I) ind.clone();

        do{
            //create two random numbers between 0 and the number of genes
            random1 = GeneticAlgorithm.random.nextInt(numGenes);
            random2 = GeneticAlgorithm.random.nextInt(numGenes);
            //make sure that the random numbers aren't equal to prevent not changing the individual due to him having 0 genes inverted
        }while(random1 == random2);

        //Set the minimum and maximum value so that we know where we start and end inverting genes
        int minVal = j = Math.min(random1, random2);
        int maxVal = Math.max(random1, random2);

        //Set individual 1 genes by getting the individual 2 genes (inverted) and replacing them on individual 1
        for(int i = maxVal; i >= minVal; i--){
            ind.setGene(j, ind2.getGene(i));
            j++;
        }
    }

    @Override
    public String toString() {
        return "Partial Inversion";
    }
}
