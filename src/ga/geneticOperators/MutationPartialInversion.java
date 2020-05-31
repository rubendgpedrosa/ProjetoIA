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
        int numGenes = ind.getNumGenes();
        int random1, random2;

        do{
            //create two random numbers between 0 and the number of genes
            random1 = GeneticAlgorithm.random.nextInt(numGenes);
            random2 = GeneticAlgorithm.random.nextInt(numGenes);
            //make sure that the random numbers aren't equal to prevent the same resulting individual
        }while(random1 == random2);

        //Set the minimum and maximum value so that we know where we start and end shifting
        int minVal = Math.min(random1, random2);
        int maxVal = Math.max(random1, random2);

        //Save last gene so we don't lose it.
        int geneAux = ind.getGene(maxVal);

        for (int i = maxVal; i > minVal + 1; i--) {
            ind.setGene(i, ind.getGene(i-1));
        }

        //Set gene saved on the new position
        ind.setGene(minVal+1, geneAux);
    }

    @Override
    public String toString() {
        return "Partial Inversion";
    }
}
