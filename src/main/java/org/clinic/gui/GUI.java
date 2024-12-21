package org.clinic.gui;

import org.clinic.gui.menu.Menu;
import org.clinic.gui.panels.HospitalsPanel;
import org.clinic.gui.panels.PatientsPanel;
import org.clinic.gui.panels.RendezvousPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    JMenuBar menuBar;
    JFrame frame;
    JTabbedPane tabbedPane;

    TabPanel hospitalsPanel;
    TabPanel patientsPanel;
    TabPanel rendezvousPanel;

    public GUI() {
        frame = new JFrame();
        menuBar = createMenuBar();
    }

    public void render() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(400, 300);
        frame.setJMenuBar(menuBar);

        tabbedPane = new TabbedPane();

        hospitalsPanel = new HospitalsPanel();
        tabbedPane.addTab( hospitalsPanel.getPanelTitle(), hospitalsPanel );

        patientsPanel = new PatientsPanel();
        tabbedPane.addTab( patientsPanel.getPanelTitle(), patientsPanel );

        rendezvousPanel = new RendezvousPanel();
        tabbedPane.addTab( rendezvousPanel.getPanelTitle(), rendezvousPanel );

        tabbedPane.setTabPlacement(JTabbedPane.TOP);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible( true );
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // using MacOS's global menu bar.
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        // Create a "Language" menu
        Menu languageMenu = new Menu("gui.menubar.language");
        for ( String lang : Language.getLanguages() ) {
            JMenuItem item = new JMenuItem( lang );
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Language.SetLanguage( lang );
                }
            });
            languageMenu.add( item );
        }

        // Add the Language menu to the menu bar
        menuBar.add(languageMenu);

        return menuBar;
    }

}
