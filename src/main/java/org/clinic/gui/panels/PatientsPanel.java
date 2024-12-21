package org.clinic.gui.panels;

import org.clinic.gui.TabPanel;
import org.clinic.lang.Language;

import javax.swing.*;

public class PatientsPanel extends TabPanel {
    public PatientsPanel() {
        this.add( new JLabel( "Patients Panel" ) );
    }

    @Override
    public String getPanelTitle() {
        return Language.Get("gui.patients.title");
    }
}
