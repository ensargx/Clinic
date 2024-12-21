package org.clinic.gui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public void render() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();

        TabPanel hospitalsPanel = new HospitalsPanel();
        tabbedPane.addTab( hospitalsPanel.getPanelTitle(), hospitalsPanel );

        TabPanel patientsPanel = new PatientsPanel();
        tabbedPane.addTab( patientsPanel.getPanelTitle(), patientsPanel );

        TabPanel rendezvousPanel = new RendezvousPanel();
        tabbedPane.addTab( rendezvousPanel.getPanelTitle(), rendezvousPanel );

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible( true );
    }
}
