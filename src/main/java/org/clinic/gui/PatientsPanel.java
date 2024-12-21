package org.clinic.gui;

import javax.swing.*;

public class PatientsPanel extends TabPanel {
    public PatientsPanel() {
        this.add( new JLabel( "Patients Panel" ) );
    }

    @Override
    public String getPanelTitle() {
        return "Patients";
    }
}
