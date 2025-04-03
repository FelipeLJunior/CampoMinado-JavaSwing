package br.com.fjp.campominado.models.observers;

import br.com.fjp.campominado.models.Field;
import br.com.fjp.campominado.models.events.EventField;

public interface FieldObserver {

    public void eventOccur(Field field, EventField e);
}
