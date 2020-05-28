package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    private int lineAgent;
    private int columnAgent;

    private int theDoorLine;
    private int theDoorColumn;

    @Override
    public double compute(WarehouseState state){
        lineAgent = state.getLineAgent();
        columnAgent = state.getColumnAgent();

        theDoorLine = state.getTheDoorLine();
        theDoorColumn = state.getTheDoorColumn();

        //Chebyshev computes the distances diagonally as well
        //return computeDistanceChebyshev();
        return computeDistanceManhattan();
    }

    public int computeDistanceManhattan() {
      //Manhattan distance
      int dx = Math.abs(lineAgent - theDoorLine);
      int dy = Math.abs(columnAgent - theDoorColumn);

      //Cost is not used since it's always 1
      return (dx + dy);
    }

    public int computeDistanceChebyshev() {
      //Chebyshev distance in our case, since D and D2 have a cost = 1
      int dx = Math.abs(lineAgent - theDoorLine);
      int dy = Math.abs(columnAgent - theDoorColumn);

      //Cost is not used since it's always 1
      return (dx + dy) + (1 - 2 /*   *1   */) * Math.min(dx, dy);
    }

    @Override
    public String toString(){
        return "Compute Distance";
    }
}
