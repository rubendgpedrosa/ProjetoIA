package warehouse;

import ga.IntVectorIndividual;

import java.util.LinkedList;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    //TODO this class might require the definition of additional methods and/or attributes

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        LinkedList<Cell> productsCells = problem.getCellsWarehouseProducts();
        Cell warehouseAgent = problem.getCellAgent();
        Cell theDoor = problem.getTheDoor();

        //TODO genome está sempre a 0s. Não anda a ser set nas classes de combinações/mutações?

        //Calculates the distance between the agent and the first product
        fitness = problem.getDistanceBetweenCells(warehouseAgent, productsCells.get(0));

        //Calculates the distance that each product has between each other
        for (int i = 1; i < productsCells.size()-1 ; i++) {
            fitness += problem.getDistanceBetweenCells(productsCells.get(i), productsCells.get(i+1));
        }

        //Calculates de distance between the last product and the door
        fitness += problem.getDistanceBetweenCells(productsCells.get(productsCells.size()-1), theDoor);

        //Returns the fitness of the total distances summed up previously
        return fitness;
    }

    public static int getShelfPos(int[] genome, int value) {
        //TODO

        throw new UnsupportedOperationException("Not implemented yet.");
    }

    //Return the product Id if the shelf in cell [line, column] has some product and 0 otherwise
    public int getProductInShelf(int line, int column){

        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i < genome.length; i++) {
            sb.append(genome[i]).append(" ");
            //this method might require changes
        }

        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(WarehouseIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);
    }
}
