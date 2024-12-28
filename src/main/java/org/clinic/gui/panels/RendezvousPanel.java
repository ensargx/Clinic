package org.clinic.gui.panels;

import org.clinic.gui.GTabPanel;
import org.clinic.lang.Language;

import javax.swing.*;

public class RendezvousPanel extends GTabPanel {
    public RendezvousPanel() {
        this.add( new JLabel( "Rendezvous Panel" ) );
    }

    @Override
    public String getPanelTitle() {
        return Language.Get("gui.rendezvous.title");
    }
}
