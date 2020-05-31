package ga.geneticOperators;

import ga.IntVectorIndividual;
import ga.Problem;

import java.util.LinkedList;

public class RecombinationCycle<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
private LinkedList<Integer> indexes;

public RecombinationCycle(double probability) {
    super(probability);
        indexes = new LinkedList<>();
    }

    @Override
    public void recombine(I ind1, I ind2) {
        int idx = 0, item, aux;

        //Cycle through all genes
        do{
            //Save index 0
            indexes.add(idx);
            //Save individual's gene
            item = ind2.getGene(idx);
            //Find the same gene value stored in the second individual and save it to idx
            idx = ind1.getIndexof(item);
        }while(!indexes.contains(idx));

        //Run through the indexes
        for(int i = 0; i < indexes.size(); i++){
            //Get the first gene stored in the index value stored
            aux = ind1.getGene(indexes.get(i));
            //Set genes positions from the stored values and the second individual genes
            ind1.setGene(indexes.get(i),ind2.getGene(indexes.get(i)));
            ind2.setGene(indexes.get(i),aux);
        }

        indexes.clear();
    }

    @Override
    public String toString(){
        return "cx";
    }
}
