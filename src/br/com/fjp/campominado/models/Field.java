package br.com.fjp.campominado.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.fjp.campominado.models.events.EventField;
import br.com.fjp.campominado.models.observers.FieldObserver;

public class Field {
    private final int line;
    private final int column;

    private boolean mine;
    private boolean open;
    private boolean marking;

    private List<Field> neighbors = new ArrayList<>();
    private Set<FieldObserver> observers = new HashSet<>();

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    private void notifyObservers(EventField event) {
        observers.stream().forEach(o -> o.eventOccur(this, event));
    }

    public void addObserver(FieldObserver fieldObserver) {
        observers.add(fieldObserver);
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean isChecked() {
        return this.marking;
    }

    public void switchMarking() {
        if(!this.open) {
            this.marking = !this.marking;
            
            if(isChecked()) {
                notifyObservers(EventField.CHECK);
            } else {
                notifyObservers(EventField.UNCHECK);
            }
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    void setOpen(boolean open) {
        this.open = open;

        if(this.open) {
            notifyObservers(EventField.OPEN);
        }
    }

    public boolean hasMine() {
        return this.mine;
    }

    void setMine() {
        this.mine = true;
    }

    public boolean open() {
        if(isOpen() || isChecked()) {
            return false;
        }

        if(hasMine()) {
            notifyObservers(EventField.EXPLODE);
            return true;
        }

        setOpen(true);
        notifyObservers(EventField.OPEN);
        
        if(isNeighborhoodSafe()) {
            neighbors.forEach(neighbor -> neighbor.open());
        }

        return true;
    }

    public boolean isNeighborhoodSafe() {
        return neighbors.stream().noneMatch(neighbor -> neighbor.hasMine());
    }

    boolean isGoalAchieved() {
        boolean discovered = !hasMine() && isOpen();
        boolean protectedFromMine = hasMine() && isChecked();

        return discovered || protectedFromMine;
    }

    public int neighborsMines() {
        return (int) neighbors.stream().filter(n -> n.hasMine()).count();
    }

    void reset() {
        mine = false;
        marking = false;
        open = false;

        notifyObservers(EventField.RESET);
    }

    public boolean addNeighbor(Field field) {
        int neighborLine = field.getLine();
        int neighborColumn = field.getColumn();

        int lineDistance = Math.abs(neighborLine - this.getLine());
        int columnDistance = Math.abs(neighborColumn - this.getColumn());

        if(lineDistance == 0 && columnDistance == 0) {
            return false;
        }

        if(columnDistance > 1 || lineDistance > 1) {
            return false;
        }

        this.neighbors.add(field);
        
        return true;
    }
}
