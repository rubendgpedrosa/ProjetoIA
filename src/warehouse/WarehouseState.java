package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {

    //TODO this class might require the definition of additional methods and/or attributes

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
        this.matrix = matrix;
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
        return lineAgent > 0;
    }

    public boolean canMoveRight() {
        return columnAgent < getSize()-1;
    }

    public boolean canMoveDown() {
        return lineAgent < getSize()-1;
    }

    public boolean canMoveLeft() {
        return columnAgent > 0;
    }

    public void moveUp() {
        if(canMoveUp()){
            this.steps++;
            System.out.println("Up "+ lineAgent+","+columnAgent);
            setCellAgent(lineAgent-1, columnAgent);
        }
    }

    public void moveRight() {
        if(canMoveRight()){
            this.steps++;
            System.out.println("Right "+ lineAgent+","+columnAgent);
            setCellAgent(lineAgent, columnAgent+1);
        }
    }

    public void moveDown() {
        if(canMoveDown()){
            this.steps++;
            System.out.println("Down "+ lineAgent+","+columnAgent);
            setCellAgent(lineAgent+1, columnAgent);
        }
    }

    public void moveLeft() {
        if(canMoveLeft()){
            this.steps++;
            System.out.println("Left "+ lineAgent+","+columnAgent);
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
            default:
                return Properties.COLOREMPTY;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
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

    public int computeDistanceManhattan() {
        //Manhattan distance
        int dx = Math.abs(this.lineAgent - this.theDoorLine);
        int dy = Math.abs(this.columnAgent - this.theDoorColumn);

        //Cost is not used since it's always 1
        return (dx + dy);
    }

    public int computeDistanceChebyshev() {
        //Chebyshev distance in our case, since D and D2 have a cost = 1
        int dx = Math.abs(this.lineAgent - this.theDoorLine);
        int dy = Math.abs(this.columnAgent - this.theDoorColumn);

        //Cost is not used since it's always 1
        return (dx + dy) + (1 - 2 * 1) * Math.min(dx, dy);
    }

}
