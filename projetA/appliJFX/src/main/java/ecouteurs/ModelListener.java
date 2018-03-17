package ecouteurs;

import java.util.EventListener;

public interface ModelListener extends EventListener {
    void modelChanged(ModelChangedEvent event);
}
