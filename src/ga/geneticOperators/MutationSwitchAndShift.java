package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationSwitchAndShift<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwitchAndShift(double probability) {
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

        //Save the gene in one of the positions
        int auxgene = ind.getGene(random1);
        //Set the gene saved as the other gene
        ind.setGene(random1, ind.getGene(random2));
        //Grab the saved gene and put it in the position of the second gene
        ind.setGene(random2, auxgene);

    }

    @Override
    public String toString() {
        return "Switch and Shift";
    }
}
