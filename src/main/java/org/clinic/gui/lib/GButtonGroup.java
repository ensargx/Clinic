package org.clinic.gui.lib;

import javax.swing.*;

public class GButtonGroup<T> extends JPanel {
    private T selected = null;
    private final GCallback<T> callback;

    public GButtonGroup(GCallback<T> callback) {
        this.callback = callback;
    }

    public void add(JButton button, T select, boolean selected) {
        button.addActionListener( e -> onSelect(select) );
        button.setEnabled(!selected);
        super.add(button);
        this.rePaint();
    }

    public void rePaint() {
        this.revalidate();
        this.repaint();
    }

    private void onSelect(T select) {
        selected = select;
        callback.onSelect(select);
    }

    public T getSelected() {
        return selected;
    }
}