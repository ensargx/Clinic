package org.clinic.gui;

import org.clinic.gui.lib.GMenuItem;
import org.clinic.gui.lib.GTabPanel;
import org.clinic.gui.lib.GTabbedPane;
import org.clinic.gui.lib.GMenu;
import org.clinic.gui.panels.HospitalsPanel;
import org.clinic.gui.panels.PatientsPanel;
import org.clinic.gui.panels.RendezvousPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class GUI {
    JMenuBar menuBar;
    JFrame frame;
    JTabbedPane tabbedPane;

    GTabPanel hospitalsPanel;
    GTabPanel patientsPanel;
    GTabPanel rendezvousPanel;

    IGUIListener listener;

    public GUI( IGUIListener listener ) {
        this.listener = listener;
        frame = new JFrame();
        menuBar = createMenuBar();
    }

    public void render( ) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(400, 300);
        frame.setJMenuBar(menuBar);

        tabbedPane = new GTabbedPane();

        hospitalsPanel = new HospitalsPanel( listener );
        tabbedPane.addTab( hospitalsPanel.getPanelTitle(), hospitalsPanel );

        patientsPanel = new PatientsPanel( listener );
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
            item.addActionListener(e -> Language.SetLanguage( lang ));
            languageMenu.add( item );
        }

        // Create 'File' section for saving/loading
        GMenu fileMenu = new GMenu( "gui.menubar.file" );
        GMenuItem load = new GMenuItem("gui.menubar.load");
        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Clinic", "clinic");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                listener.onFileLoad(file.getPath());
                reRender();
            }
        });
        GMenuItem save = new GMenuItem( "gui.menubar.save" );
        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Clinic", "clinic");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                listener.onFileSave(file.getPath());
            }
        });

        fileMenu.add(load);
        fileMenu.add(save);

        menuBar.add(fileMenu);
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

    public void reRender() {
        hospitalsPanel.reRender();
        patientsPanel.reRender();
        rendezvousPanel.reRender();
    }

}
