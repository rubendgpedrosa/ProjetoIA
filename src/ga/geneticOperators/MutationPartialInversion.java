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
        int  random1,random2,j=0;

        I ind2 =(I) ind.clone();

        do{
            random1 = GeneticAlgorithm.random.nextInt(numGenes);
            random2 = GeneticAlgorithm.random.nextInt(numGenes);
        }while(random1 == random2);

        int minVal = j = Math.min(random1, random2);
        int maxVal = Math.max(random1, random2);


        for(int i = maxVal; i >= minVal; i--){
            ind.setGene(j,ind2.getGene(i));
            j++;
        }
    }

    @Override
    public String toString() {
        return "Partial Inversion";
    }
}
