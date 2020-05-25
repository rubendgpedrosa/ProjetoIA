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
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        double cost = 0;

        for (int i = 0; i < genome.length; i++) {
            Cell cell = productsCells.get(i);
            for (Pair pair : pairs) {
                if(pair.getCell1() == warehouseAgent && pair.getCell2() == cell){
                    cost += pair.getValue();
                    warehouseAgent = cell;
                }
                //caso o par esteja ao contrário
                if(pair.getCell1() == cell && pair.getCell2() == warehouseAgent){
                    cost += pair.getValue();
                    warehouseAgent = cell;
                }
            }
        }
        //par da porta
        for (Pair pair : pairs) {
            if(pair.getCell1() == warehouseAgent && pair.getCell2() == theDoor){
                cost += pair.getValue();
                warehouseAgent = theDoor;
            }
        }

        fitness = cost;

        //Returns the fitness of the total distances summed up previously
        return fitness;
    }

    public static int getShelfPos(int[] genome, int value) {
        return genome[value];

        //TODO
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    //Return the product Id if the shelf in cell [line, column] has some product and 0 otherwise
    public int getProductInShelf(int line, int column){
        for (Cell productsCell : productsCells) {
            if(productsCell.getLine() == line && productsCell.getColumn() == column){
                return 2;
            }
        }
        return 0;
        //TODO
        //throw new UnsupportedOperationException("Not implemented yet.");
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
