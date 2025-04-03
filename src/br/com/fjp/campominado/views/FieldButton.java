package br.com.fjp.campominado.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.com.fjp.campominado.models.Field;
import br.com.fjp.campominado.models.events.EventField;
import br.com.fjp.campominado.models.observers.FieldObserver;

public class FieldButton extends JButton implements FieldObserver, MouseListener {
    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_EXPLODE = new Color(230, 0, 0);
    private final ImageIcon IMG_EXPLODE = new ImageIcon("images/explode60.png");
    private final ImageIcon IMG_FLAG = new ImageIcon("images/flag60.png");

    private Field field;

    FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        field.addObserver(this);
    }

    @Override
    public void eventOccur(Field field, EventField e) {
        switch(e) {
            case OPEN:
                setOpenStyle();
                break;
            case CHECK:
                setCheckedStyle();
                break;
            case EXPLODE:
                setExplodeStyle();
                break;
            default:
                setDefaultStyle();
        }
    }

    private void setDefaultStyle() {
        setIcon(null);
        setText("");
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_DEFAULT);
    }

    private void setOpenStyle() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if(field.hasMine()) {
            setBackground(BG_EXPLODE);
            return ;
        }

        switch(field.neighborsMines()) {
            case 1:
                setForeground(Color.GREEN);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }

        String numberOfLandmines = field.isNeighborhoodSafe() ? "" : Integer.toString(field.neighborsMines());
        setFont(new Font(Font.SANS_SERIF, 1, 20));
        setText(numberOfLandmines);
    }

    private void setCheckedStyle() {
        setIconTextGap(2);
        setIcon(IMG_FLAG);
    }

    private void setExplodeStyle() {
        setIcon(IMG_EXPLODE);
    }

    
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {
            field.open();
        } else {
            field.switchMarking();
        }
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
