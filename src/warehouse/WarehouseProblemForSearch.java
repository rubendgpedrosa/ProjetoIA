package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.ArrayList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {
    private int[][] matrix;
    private Cell goalState;
    private final ArrayList<Action> availableActions;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);

        //Set goal state
        this.goalState = goalPosition;

        //Add every 4 available actions
        this.availableActions = new ArrayList<>(4);
        availableActions.add(new ActionUp());
        availableActions.add(new ActionRight());
        availableActions.add(new ActionDown());
        availableActions.add(new ActionLeft());

        matrix = initialWarehouseState.getMatrix();
    }

    @Override
    public List<S> executeActions(S state) {
        //create empty list of successors
        ArrayList<S> successors = new ArrayList<>(4);
        //Iterate through every action and validate them
        for (Action availableAction : this.availableActions) {
            if (availableAction.isValid(state)) {
                S successor = (S) state.clone();
                //Get sucesssor state and add it to the sucessor list
                availableAction.execute(successor);
                successors.add(successor);
            }
        }
        return successors;
    }

    public boolean isGoal(S state) {
        if (matrix[goalState.getLine()][goalState.getColumn()] == Properties.SHELF) {
            return state.getLineAgent() == goalState.getLine() && state.getColumnAgent() == goalState.getColumn() + 1;
        }
        else{
            return state.getLineAgent() == goalState.getLine() && state.getColumnAgent() == goalState.getColumn();
        }
    }
}
