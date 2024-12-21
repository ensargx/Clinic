package org.clinic.gui;

import javax.swing.*;

public class RendezvousPanel extends TabPanel{
    public RendezvousPanel() {
        this.add( new JLabel( "Rendezvous Panel" ) );
    }

    @Override
    public String getPanelTitle() {
        return "Rendezvous";
    }
}
