package br.com.fjp.campominado.models.observers;

import br.com.fjp.campominado.models.Board;
import br.com.fjp.campominado.models.events.EventBoard;

public class GameOver implements BoardObserver {
    private EventBoard event;
    private Board board;
    
    public GameOver(EventBoard event, Board board) {
        this.event = event;
        this.board = board;
    }

    public EventBoard getEvent() {
        return event;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void eventOccur() {
        if(getEvent() == EventBoard.LOSE) {
            
        } else {
            
        }
    }
}
