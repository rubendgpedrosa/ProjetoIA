package warehouse;

import ga.IntVectorIndividual;

import java.util.LinkedList;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    //TODO this class might require the definition of additional methods and/or attributes
    private LinkedList<Cell> productsCells = problem.getCellsWarehouseProducts();
    private Cell warehouseAgent = problem.getCellAgent();
    private Cell theDoor = problem.getTheDoor();
    private LinkedList<Pair> pairs = WarehouseAgentSearch.getPairs();

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);
        //TODO criar genoma para cada request?
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;

        for (int i = 0; i < genome.length; i++) {
            Cell cell = productsCells.get(i);
            for (Pair pair : pairs) {
                if(pair.getCell1() == warehouseAgent && pair.getCell2() == cell){
                    fitness += pair.getValue();
                }
                //caso o par esteja ao contrário
                if(pair.getCell1() == cell && pair.getCell2() == warehouseAgent){
                    fitness += pair.getValue();
                }
            }
        }
        //par da porta
        for (Pair pair : pairs) {
            if(pair.getCell1() == warehouseAgent && pair.getCell2() == theDoor){
                fitness += pair.getValue();
            }
        }
        //Returns the fitness of the total distances summed up previously
        return fitness;
    }

    /*
    The getShelfPos() method receives the genome and a product number and returns
    the position of the shelf in the list of shelves in the WarehouseAgentSearch class
    The getProductInShelf() method returns the product number if the shelf in cell
    [line, column] has some product and 0 otherwise
    */

    public static int getShelfPos(int[] genome, int value) {
        genome[value-1] = 1;
        return value-1;
    }

    public int getProductInShelf(int line, int column){
        //throw new UnsupportedOperationException("Not implemented yet.");
        for (int i = 0; i < productsCells.size(); i++) {
            if(productsCells.get(i).getLine() == line && productsCells.get(i).getColumn() == column){
                return i+1;
            }
        }
        return 0;
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
