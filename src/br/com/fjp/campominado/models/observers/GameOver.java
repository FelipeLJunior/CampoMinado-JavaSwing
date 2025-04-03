package br.com.fjp.campominado.models.observers;

import javax.swing.JOptionPane;

import br.com.fjp.campominado.models.Board;
import br.com.fjp.campominado.models.events.EventBoard;

public class GameOver implements BoardObserver {
    private EventBoard event;
    
    public GameOver(EventBoard event) {
        this.event = event;
    }

    public EventBoard getEvent() {
        return event;
    }

    @Override
    public void eventOccur(Board board) {
        if(getEvent() == EventBoard.WIN) {
            JOptionPane.showMessageDialog(null, "Você ganhou!");
        } else {
            JOptionPane.showMessageDialog(null, "Você perdeu :(");
        }
        
        board.reset();
    }
}
