package org.clinic.gui;

import org.clinic.gui.panels.HospitalsPanel;
import org.clinic.gui.panels.PatientsPanel;
import org.clinic.gui.panels.RendezvousPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    public void render() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();

        TabPanel hospitalsPanel = new HospitalsPanel();
        tabbedPane.addTab( hospitalsPanel.getPanelTitle(), hospitalsPanel );

        TabPanel patientsPanel = new PatientsPanel();
        tabbedPane.addTab( patientsPanel.getPanelTitle(), patientsPanel );

        TabPanel rendezvousPanel = new RendezvousPanel();
        tabbedPane.addTab( rendezvousPanel.getPanelTitle(), rendezvousPanel );

        Language.AddLanguageCallback(()->{
            for ( int i = 0; i < tabbedPane.getTabCount(); ++i ) {
                TabPanel panel = (TabPanel) tabbedPane.getComponentAt(i);
                if ( panel != null ) {
                    tabbedPane.setTitleAt(i, panel.getPanelTitle());
                }
            }
        });

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible( true );
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Create a "Language" menu
        JMenu languageMenu = new JMenu("Language");

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
