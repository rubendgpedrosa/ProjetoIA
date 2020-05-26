package warehouse;

import ga.IntVectorIndividual;

import java.util.*;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    //TODO this class might require the definition of additional methods and/or attributes
    private LinkedList<Cell> productsCells = problem.getCellsWarehouseProducts();
    private Cell warehouseAgent = problem.getCellAgent();
    private Cell theDoor = problem.getTheDoor();
    private LinkedList<Pair> pairs = problem.getPairs();
    private List<Integer> list = new ArrayList<>();

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);
        Random random = new Random();
        int number;

        for (int i = 0; i < genome.length; i++)
        {
            number = random.nextInt(genome.length)+1;
            while(list.contains(number)){
                number = random.nextInt(genome.length)+1;
            }
            this.setGene(i, number);
            list.add(number);
        }
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;

        //Iterates through each request line
        for (Request request : WarehouseAgentSearch.getRequests()) {
            //Gets the cell for the product requested through getRequest, using the number in the request position
            for (Pair pair : pairs) {
                for (int i = 0; i < request.getRequest().length; i++) {
                    int shelfPos = getShelfPos(genome, request.getRequest()[i]);
                    Cell cell = productsCells.get(shelfPos);
                    if (pair.getCell1() == warehouseAgent && pair.getCell2() == cell) {
                        fitness += pair.getValue();
                        break;
                    }
                    //In case they are swapped
                    if (pair.getCell1() == cell && pair.getCell2() == warehouseAgent) {
                        fitness += pair.getValue();
                        break;
                    }
                }
            }
            for (Pair pair : pairs) {
                if (pair.getCell1() == warehouseAgent && pair.getCell2() == theDoor) {
                    fitness += pair.getValue();
                }
                //In case they are swapped again
                if (pair.getCell2() == warehouseAgent && pair.getCell1() == theDoor) {
                    fitness += pair.getValue();
                }
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
        /*genome[value-1] = 1;
        return value-1;*/
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value){
                return i;
            }
        }
        return 0;
    }

    public int getProductInShelf(int line, int column){
        /*for (int i = 0; i < productsCells.size(); i++) {
            if(productsCells.get(i).getLine() == line && productsCells.get(i).getColumn() == column){
                return i+1;
            }
        }
        return 0;*/
        for (int i = 0; i < genome.length; i++) {
            if(productsCells.get(i).getLine() == line && productsCells.get(i).getColumn() == column){
                //return i+1;
                return genome[i];
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: \n");
        for (Request request : WarehouseAgentSearch.getRequests()) {
            sb.append(warehouseAgent+" ");
            for (int i = 0; i < request.getRequest().length; i++) {
                int shelfPos = getShelfPos(genome, request.getRequest()[i]);
                Cell cell = productsCells.get(shelfPos);
                sb.append(cell).append(" ");
            }
            sb.append(warehouseAgent).append(" | ");
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
