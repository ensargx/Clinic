package org.clinic.gui;

import org.clinic.Hospital;
import org.clinic.gui.menu.GMenu;
import org.clinic.gui.panels.HospitalsPanel;
import org.clinic.gui.panels.PatientsPanel;
import org.clinic.gui.panels.RendezvousPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUI {
    JMenuBar menuBar;
    JFrame frame;
    JTabbedPane tabbedPane;

    TabPanel hospitalsPanel;
    TabPanel patientsPanel;
    TabPanel rendezvousPanel;

    IGUIListener listener;

    public GUI( IGUIListener listener ) {
        this.listener = listener;
        frame = new JFrame();
        menuBar = createMenuBar();
    }

    public void render( HashMap<Integer, Hospital> hospitals ) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(400, 300);
        frame.setJMenuBar(menuBar);

        tabbedPane = new TabbedPane();

        hospitalsPanel = new HospitalsPanel( listener, hospitals );
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
        GMenu languageMenu = new GMenu("gui.menubar.language");
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

    public static void ErrorMessage( String error ) {
        JOptionPane.showOptionDialog(
                null,
                error,
                Language.Get("gui.error"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[] { Language.Get("gui.ok") },
                Language.Get("gui.ok")
        );
    }

}
