package br.com.fjp.campominado.views;

import java.awt.*;
import javax.swing.JPanel;

import br.com.fjp.campominado.models.Board;

public class BoardPanel extends JPanel {
    BoardPanel(Board board) {
        setLayout(new GridLayout(board.getLines(), board.getColumns()));

        board.forEachField(c -> add(new FieldButton(c)));
    }
}
