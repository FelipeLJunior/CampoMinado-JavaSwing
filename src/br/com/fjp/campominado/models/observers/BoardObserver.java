package br.com.fjp.campominado.models.observers;

import br.com.fjp.campominado.models.Board;

public interface BoardObserver {

    public void eventOccur(Board board);
}
