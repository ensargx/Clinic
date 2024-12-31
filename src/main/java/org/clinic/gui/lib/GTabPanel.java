package org.clinic.gui.lib;

import javax.swing.JPanel;

public abstract class GTabPanel extends JPanel {
    abstract public String getPanelTitle();

    abstract public void reRender();
}
