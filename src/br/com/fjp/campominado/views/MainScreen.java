package br.com.fjp.campominado.views;

import javax.swing.JFrame;

import br.com.fjp.campominado.models.Board;

public class MainScreen extends JFrame {
    MainScreen() {
        Board board = new Board(6, 6, 6);

        add(new BoardPanel(board));
        setVisible(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Campo Minado");
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
