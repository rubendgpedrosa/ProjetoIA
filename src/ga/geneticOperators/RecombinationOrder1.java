package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class RecombinationOrder1<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    private ArrayList<Integer> outerSegmentBuildArray;
    private int cut1;
    private int cut2;

    public RecombinationOrder1(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        outerSegmentBuildArray = new ArrayList<>();
        int[] parent1 = new int[ind1.getNumGenes()];
        int[] parent2 = new int[ind2.getNumGenes()];

        for(int index = 0; index < parent1.length; index ++){
            parent1[index] = ind1.getGene(index);
            parent2[index] = ind2.getGene(index);
        }

        int[] offspring1 = new int[ind1.getNumGenes()];
        int[] offspring2 = new int[ind2.getNumGenes()];
        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        cut2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        //Swap the cuts around in case cut 1 is bigger than cut 2
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        //Let's make babies
        crossOver(offspring1, parent1, parent2);
        crossOver(offspring2, parent2, parent1);
    }

    private void crossOver(int[] offspring, int[] parentX, int[] parentY) {
        int tempIndex = 0;
        int index = cut2 + 1;
        // if index - cutPoint2 + 1  == parentX.length
        // add all parentX elements directly to  outerSegmentBuildArray ArrayList.
        if(index == parentX.length) { // e.g. (1 2 3 | 4 5 6 7 8| )
            for(int x = 0; x < parentX.length; x++){
                outerSegmentBuildArray.add(parentX[x]);
            }
        }

        // Else block here concatenates segments in the following order 3rd then (1 and 2)
        // outerSegmentBuildArray
        else {
            for(index = cut2 + 1; index < parentX.length; index++){
                outerSegmentBuildArray.add(tempIndex, parentX[index]);
                tempIndex++;
            }
            for(index = 0; index <= cut2; index++){
                outerSegmentBuildArray.add(tempIndex, parentX[index]);
                tempIndex++;
            }

        }


        for(int indexInSegment = cut1; indexInSegment <=cut2; indexInSegment++){
            // for ArrayList temp remove elements that appear in parentY mid segments
            removeSpecifiedElement(parentY[indexInSegment]);
        }

        // copy mid segment from parent designated as Y,
        // into offspring to be created.
        if (cut2 + 1 - cut1 >= 0) System.arraycopy(parentY, cut1, offspring, cut1, cut2 + 1 - cut1);


        // Belows section copies remaining elements in temp into offspring
        // starting from 3rd segment of offspring.
        tempIndex = 0;
        for(int y = cut2 + 1; y < offspring.length; y++){
            offspring[y] = outerSegmentBuildArray.get(tempIndex);
            tempIndex++;
        }

        // after end of offspring reach, copy elements from temp haven't been copied
        // into offspring from 1st segment.
        for(int z = 0; z < cut1; z++){
            if(offspring.length == z){ break; }
            offspring[z] = outerSegmentBuildArray.get(tempIndex);
            tempIndex++;
        }
    }

    private void removeSpecifiedElement(int elementToRemove){
        for(int index = 0; index< outerSegmentBuildArray.size(); index++){
            if(outerSegmentBuildArray.get(index) == elementToRemove){
                outerSegmentBuildArray.remove(index);
                break;
            }
        }
    }

    @Override
    public String toString(){
        return "ORDER1";
    }
}