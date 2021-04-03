package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;

import java.util.List;

interface MyEventListener<T> {
    void handleChange(T value);
}

class MyEvent<T> {

    private List<MyEventListener<T>> listeners;

    public void addListener(MyEventListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(MyEventListener<T> listener) {
        listeners.remove(listener);
    }

    void fire(T value) {
        listeners.forEach(listener -> listener.handleChange(value));
    }
}

class Notifications2 {
    private final MyEvent<CursorMode> cursorModeChanged = new MyEvent<>();

    public MyEvent<CursorMode> getCursorModeChanged() {
        return cursorModeChanged;
    }
}

class FireMyEvent {
    public FireMyEvent(Notifications2 notifications2) {
        notifications2.getCursorModeChanged().fire(CursorMode.VIEW);
    }
}

class ConsumeMyEvent {
    public ConsumeMyEvent(Notifications2 notifications2) {
        notifications2.getCursorModeChanged().addListener(this::handleCursorModeChanged);
    }

    private void handleCursorModeChanged(CursorMode cursorMode) {

    }
}

public class Model {

}
