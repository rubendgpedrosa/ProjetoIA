package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        //return state.computeDistanceChebyshev();
        return state.computeDistanceManhattan();
        
    }

    @Override
    public String toString(){
        return "Compute Distance";
    }
}
