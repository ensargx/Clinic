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
import java.util.Objects;

// GUI object will be a singleton which will initialized after CRS
public class GUI {
    JMenuBar menuBar;
    JFrame frame;
    JTabbedPane tabbedPane;

    GTabPanel hospitalsPanel;
    GTabPanel patientsPanel;
    GTabPanel rendezvousPanel;

    IGUIListener listener;

    private static GUI mainGui = null;

    public static JFrame getMainFrame() {
        return mainGui == null ? null : mainGui.frame;
    }

    public GUI( IGUIListener listener ) {
        GUI.mainGui = this;
        this.listener = listener;
        frame = new JFrame();
        menuBar = createMenuBar();
    }

    public void render( ) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        frame.setIconImage(icon.getImage());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        frame.setTitle( "G Clinic" );

        frame.setSize(800, 600);
        frame.setJMenuBar(menuBar);

        tabbedPane = new GTabbedPane();

        hospitalsPanel = new HospitalsPanel( listener );
        tabbedPane.addTab( hospitalsPanel.getPanelTitle(), hospitalsPanel );

        patientsPanel = new PatientsPanel( listener );
        tabbedPane.addTab( patientsPanel.getPanelTitle(), patientsPanel );

        rendezvousPanel = new RendezvousPanel( listener );
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

        GMenuItem load = new GMenuItem("gui.menubar.open");
        load.setIcon( UIManager.getIcon("FileView.fileIcon") );
        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            String savedPath = listener.getSavedFilePath();
            if ( savedPath != null ) {
                fileChooser.setSelectedFile( new File(savedPath) );
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Clinic", "clinic");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                listener.onFileLoad(file.getPath());
                reRender();
            }
        });

        GMenuItem save = new GMenuItem( "gui.menubar.save" );
        save.setIcon( UIManager.getIcon("FileView.floppyDriveIcon") );
        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            String savedPath = listener.getSavedFilePath();
            if ( savedPath != null ) {
                fileChooser.setSelectedFile( new File(savedPath) );
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Clinic", "clinic");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getPath();
                if (!filePath.toLowerCase().endsWith(".clinic")) {
                    file = new File(filePath + ".clinic");
                }
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
                getMainFrame(),
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

    public static final int PANEL_HOSPITAL = 0;
    public static final int PANEL_PATIENTS = 1;
    public static final int PANEL_RENDEZVOUS = 2;

    public static void switchToPanel(int panelIdx) {
        if ( panelIdx < PANEL_HOSPITAL || panelIdx > PANEL_RENDEZVOUS ) {
            return;
        }

        if ( mainGui == null ) {
            return;
        }

        mainGui.tabbedPane.setSelectedIndex(panelIdx);
    }

}
