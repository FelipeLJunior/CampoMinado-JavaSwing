package br.com.fjp.campominado.views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.fjp.campominado.models.Field;
import br.com.fjp.campominado.models.events.EventField;
import br.com.fjp.campominado.models.observers.FieldObserver;

public class FieldButton extends JButton implements FieldObserver {
    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_CHECK = new Color(8, 179, 247);
    private final Color BG_UNCHECK = new Color(189, 66, 68);
    private final Color BG_EXPLODE = new Color(0, 100, 0);
    
    private Field field;

    FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0
        ));

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
        
    }

    private void setOpenStyle() {
    
    }

    private void setCheckedStyle() {
        
    }

    private void setExplodeStyle() {
        
    }
}
