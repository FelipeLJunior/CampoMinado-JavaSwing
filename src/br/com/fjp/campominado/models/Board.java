package br.com.fjp.campominado.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import br.com.fjp.campominado.models.events.EventBoard;
import br.com.fjp.campominado.models.events.EventField;
import br.com.fjp.campominado.models.observers.BoardObserver;
import br.com.fjp.campominado.models.observers.FieldObserver;
import br.com.fjp.campominado.models.observers.GameOver;

public class Board implements FieldObserver {
    private int linesQuantity;
    private int columnsQuantity;
    private int minesQuantity;

    private final List<Field> fields = new ArrayList<>();
    private final Set<Consumer<BoardObserver>> observers = new HashSet<>();

    public Board(int linesQuantity, int columnsQuantity, int minesQuantity) {
        this.linesQuantity = linesQuantity;
        this.columnsQuantity = columnsQuantity;
        this.minesQuantity = minesQuantity;

        fieldsGenerate();
        associateNeighbors();
        shuffleMines();
    }

    public int getLines() {
        return linesQuantity;
    }

    public int getColumns() {
        return columnsQuantity;
    }


    public void addObserver(Consumer<BoardObserver> observer) {
        observers.add(observer);
    }

    private void notifyObservers(EventBoard event) {
        observers.stream().forEach(o -> o.accept(new GameOver(event, this)));
    }

    public void openField(int line, int column) {
            fields.stream()
                .filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.open());
    }

    public void checkField(int line, int column) {
        fields.stream()
            .filter(f -> f.getLine() == line && f.getColumn() == column)
            .findFirst()
            .ifPresent(f -> f.switchMarking());
    }

    public boolean isGoalAchieved() {
        return fields.stream().allMatch(f -> f.isGoalAchieved());
    }

    public void reset() {
        fields.forEach(f -> f.reset());
        shuffleMines();
    }

    @Override
    public void eventOccur(Field field, EventField e) {
        if(EventField.EXPLODE == e) {
            showLandmines();
            notifyObservers(EventBoard.LOSE);
        } else if(isGoalAchieved()) {
            notifyObservers(EventBoard.WIN);
        }
    }

    private void fieldsGenerate() {
        for(int line = 0; line < linesQuantity; line++) {
            for(int column = 0; column < columnsQuantity; column++) {
                Field field = new Field(line, column);
                field.addObserver(this);
                fields.add(field);
            }
        }
    }

    private void associateNeighbors() {
        for(Field f1: fields) {
            for(Field f2: fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void shuffleMines() {
        long armedMines = 0;
        Predicate<Field> hasMine = f -> f.hasMine();
        int totalFields = fields.size();

        while(armedMines < minesQuantity) {
            int randomIndex = (int) (Math.random() * totalFields);
            fields.get(randomIndex).setMine();
            
            armedMines = fields.stream().filter(hasMine).count();
        }
    }

    private void showLandmines() {
        fields.stream().filter(f -> f.hasMine()).forEach(f -> f.setOpen(true));
    }

    public void forEachField(Consumer<Field> consumer) {
        fields.forEach(consumer);
    }
}
