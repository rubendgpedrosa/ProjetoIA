package warehouse;

import ga.Problem;

import java.util.LinkedList;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {

    private LinkedList<Cell> cellsWarehouseProducts;
    private LinkedList<Pair> pairs;
    private Cell cellAgent;
    private Cell theDoor;

    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.cellAgent = this.theDoor = WarehouseAgentSearch.getCellAgent();
        this.cellsWarehouseProducts = WarehouseAgentSearch.getShelves();
        this.pairs = agentSearch.getPairs();
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, this.cellsWarehouseProducts.size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pair pair : pairs) {
            sb.append(pair.toString());
        }
        return sb.toString();
    }

    public LinkedList<Cell> getCellsWarehouseProducts() {
        return cellsWarehouseProducts;
    }

    public LinkedList<Pair> getPairs() {
        return pairs;
    }

    public Cell getCellAgent() {
        return cellAgent;
    }

    public Cell getTheDoor() {
        return theDoor;
    }

    public int getDistanceBetweenCells(Cell init, Cell end) {
        return Math.max(Math.abs(end.getColumn() - init.getColumn()), Math.abs(end.getLine() - init.getLine()));
    }
}
