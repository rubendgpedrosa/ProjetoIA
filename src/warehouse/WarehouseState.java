package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {
    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int steps;

    //https://www.youtube.com/watch?v=nXv28fd46wM  THE DOOR!
    private int theDoorLine;
    private int theDoorColumn;

    public WarehouseState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 1; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == Properties.AGENT) {
                    this.lineAgent = this.theDoorLine = i;
                    this.columnAgent = this.theDoorColumn = j;
                }
            }
        }
        this.steps = 0;
    }

    public WarehouseState(int[][] matrix, int lineAgent, int columnAgent, int theDoorLine, int theDoorColumn, int steps) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 1; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        this.lineAgent = lineAgent;
        this.columnAgent = columnAgent;
        this.steps = steps;
        this.theDoorLine = theDoorLine;
        this.theDoorColumn = theDoorColumn;
    }

    public void executeAction(Action action) {
        action.execute(this);
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        fireUpdatedEnvironment();
    }

    public boolean canMoveUp() {
        return lineAgent > 0 && matrix[lineAgent - 1][columnAgent] != Properties.SHELF;
    }

    public boolean canMoveRight() {
        return columnAgent < getSize() - 1 && matrix[lineAgent][columnAgent + 1] != Properties.SHELF;
    }

    public boolean canMoveDown() {
        return lineAgent < getSize() - 1 && matrix[lineAgent + 1][columnAgent] != Properties.SHELF;
    }

    public boolean canMoveLeft() {
        return columnAgent > 0 && matrix[lineAgent][columnAgent - 1] != Properties.SHELF;
    }

    public void moveUp() {
        if(canMoveUp()){
            this.steps++;
            setCellAgent(lineAgent-1, columnAgent);
        }
    }

    public void moveRight() {
        if(canMoveRight()){
            this.steps++;
            setCellAgent(lineAgent, columnAgent+1);
        }
    }

    public void moveDown() {
        if(canMoveDown()){
            this.steps++;
            setCellAgent(lineAgent+1, columnAgent);
        }
    }

    public void moveLeft() {
        if(canMoveLeft()){
            this.steps++;
            setCellAgent(lineAgent, columnAgent-1);
        }
    }

    public void setCellAgent(int line, int column) {
        this.matrix[lineAgent][columnAgent] = Properties.EMPTY;
        this.matrix[line][column] = Properties.AGENT;
        this.lineAgent = line;
        this.columnAgent = column;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return matrix.length;
    }

    public Color getCellColor(int line, int column) {
        if (line == theDoorLine && column == theDoorColumn && (line != lineAgent || column != columnAgent))
            return Properties.COLOREXIT;

        switch (matrix[line][column]) {
            case Properties.AGENT:
                return Properties.COLORAGENT;
            case Properties.SHELF:
                return Properties.COLORSHELF;
            case Properties.EMPTY:
                return Properties.COLOREMPTY;
            default:
                return Properties.COLORSHELFPRODUCTCATCH;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
    }

    public int getTheDoorLine() {
      return theDoorLine;
    }

    public int getTheDoorColumn() {
      return theDoorColumn;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WarehouseState)) {
            return false;
        }

        WarehouseState o = (WarehouseState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() {
        return new WarehouseState(this.matrix, this.lineAgent, this.columnAgent, this.theDoorLine, this.theDoorColumn, this.steps);
    }

    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
