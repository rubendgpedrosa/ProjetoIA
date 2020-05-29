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

        //Adds index of genes to indexes untill all indexes are in the list
        do{
            indexes.add(idx);
            item = ind2.getGene(idx);
            idx = ind1.getIndexof(item);
        }while(!indexes.contains(idx));

        //Swaps out the genes on both individuals using the index list as an auxiliary
        for(int i = 0; i < indexes.size(); i++){
            aux = ind1.getGene(indexes.get(i));
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
