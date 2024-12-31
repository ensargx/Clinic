package org.clinic.gui.panels;

import org.clinic.gui.IGUIListener;
import org.clinic.gui.lib.GButton;
import org.clinic.gui.lib.GLabel;
import org.clinic.gui.lib.GTabPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;

public class RendezvousPanel extends GTabPanel {
    private IGUIListener listener;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel rendezvousPanel = new JPanel();
    private JPanel newRendezvousPanel = new JPanel();

    private static String sRendezvous = "Rendezvous";
    private static String sNewRendezvous = "NewRendezvous";

    public RendezvousPanel( IGUIListener listener ) {
        this.listener = listener;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(rendezvousPanel, sRendezvous);
        cardPanel.add(newRendezvousPanel, sNewRendezvous);

        renderRendezvous();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(cardPanel, BorderLayout.CENTER);
    }

    private void renderRendezvous() {
        rendezvousPanel.removeAll();
        rendezvousPanel.setLayout(new BoxLayout(rendezvousPanel, BoxLayout.Y_AXIS));

        // Button for adding new rendezvous
        GButton newRendezvousButton = new GButton( "gui.rendezvous.new_rendezvous" );
        newRendezvousButton.setFlexibleSize(150, 30, 300, 40);
        newRendezvousButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newRendezvousButton.addActionListener( e -> renderNewRendezvous() );

        // All rendezvous label
        GLabel allRendezvousLabel = new GLabel( "gui.rendezvous.all_rendezvous_label" );
        allRendezvousLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // List each rendezvous
        JPanel listRendezvousPanel = new JPanel();
        listRendezvousPanel.setLayout( new BoxLayout(listRendezvousPanel, BoxLayout.Y_AXIS) );
        JPanel listRendezvousPanelWrapper= new JPanel( new BorderLayout() );
        listRendezvousPanelWrapper.add(listRendezvousPanel, BorderLayout.PAGE_START);
        listener.getRendezvouses().forEach( rendezvous -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
            String date = dateFormat.format( rendezvous.getDate() );

            infoPanel.add( new GLabel( "gui.rendezvous.patient_name_format", rendezvous.getPatient().getName() ) );
            infoPanel.add( new GLabel( "gui.rendezvous.label_date_format", date ) );

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            // GButton completeButton = new GButton("gui.hospital.hospital_details");
            // JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            // actionPanel.setOpaque(false);

            // completeButton.addActionListener(e -> renderSections(hospital));
            // actionPanel.add(completeButton);
            // taskPanel.add(actionPanel, BorderLayout.EAST);

            taskPanel.setPreferredSize(new Dimension(400, 100));
            listRendezvousPanel.add(taskPanel);
        } );

        rendezvousPanel.add(newRendezvousButton);
        rendezvousPanel.add(allRendezvousLabel);
        rendezvousPanel.add(listRendezvousPanelWrapper);

        rendezvousPanel.revalidate();
        rendezvousPanel.repaint();

        switchTab(RendezvousPanel.sRendezvous);
    }

    private void renderNewRendezvous() {
        newRendezvousPanel.removeAll();
        newRendezvousPanel.setLayout(new BoxLayout(newRendezvousPanel, BoxLayout.Y_AXIS));



        newRendezvousPanel.revalidate();
        newRendezvousPanel.repaint();

        switchTab(RendezvousPanel.sNewRendezvous);
    }

    private void switchTab(String paneStr) {
        cardLayout.show(cardPanel, paneStr);
    }

    @Override
    public String getPanelTitle() {
        return Language.Get("gui.rendezvous.title");
    }

    @Override
    public void reRender() {

    }
}
