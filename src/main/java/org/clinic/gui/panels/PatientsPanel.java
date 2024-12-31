package org.clinic.gui.panels;

import org.clinic.gui.*;
import org.clinic.gui.lib.*;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;

public class PatientsPanel extends GTabPanel {
    private IGUIListener listener;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel patientsPanel = new JPanel();
    private static final String sPatients = "Patients";

    public PatientsPanel( IGUIListener listener ) {
        this.listener = listener;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(patientsPanel, sPatients);

        renderPatients();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(cardPanel, BorderLayout.CENTER);
    }

    public void renderPatients() {
        patientsPanel.removeAll();

        patientsPanel.setLayout(new BoxLayout(patientsPanel, BoxLayout.Y_AXIS));

        // New Patient Button
        GButton createButton = new GButton( "gui.patients.create" );
        createButton.setFlexibleSize(150, 30, 300, 40);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.addActionListener( e -> openAddPatientDialog() );

        // Info panel for each patient
        JPanel listPatientsPanel = new JPanel();
        listPatientsPanel.setLayout( new BoxLayout(listPatientsPanel, BoxLayout.Y_AXIS) );
        JPanel listPatientsPanelWrapper= new JPanel( new BorderLayout() );
        listPatientsPanelWrapper.add(listPatientsPanel, BorderLayout.PAGE_START);
        listener.getPatients().forEach( (nationalId, patient) -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            infoPanel.add( new GLabel( "gui.patients.label_patient_patient_name_format", patient.getName() ) );
            infoPanel.add( new GLabel( "gui.patients.label_patient_national_id_format", nationalId ) );

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            taskPanel.setPreferredSize(new Dimension(200, 100));
            listPatientsPanel.add(taskPanel);
        } );

        patientsPanel.add(createButton);
        patientsPanel.add(listPatientsPanelWrapper);

        patientsPanel.revalidate();
        patientsPanel.repaint();

        switchTab(PatientsPanel.sPatients);
    }

    private void openAddPatientDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), Language.Get( "gui.patients.create_patient_popup" ), true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Hospital Name Field
        GTextField nameField = new GTextField( 10 );
        nameField.setPlaceholder( "gui.patients.name_placeholder" );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        dialog.add(nameField);

        // Hospital ID Field
        GIntegerField idField = new GIntegerField(10);
        idField.setPlaceholder( "gui.patients.id_placeholder" );
        idField.setFlexibleSize( 150, 25, 300, 30 );
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(idField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton(Language.Get("gui.save"));
        JButton cancelButton = new JButton(Language.Get("gui.cancel"));

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty() || !idField.isInteger() || idField.isEmpty()) {
                JOptionPane.showOptionDialog(
                        this,
                        Language.Get("gui.patients.error_name_id_null"),
                        Language.Get("gui.error"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new String[] { Language.Get("gui.ok") },
                        Language.Get("gui.ok")
                );
                return;
            }
            Integer id = idField.getInteger();
            nameField.clear();
            idField.clear();
            listener.onPatientCreated( name, id );
            renderPatients();

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private void switchTab(String paneStr) {
        cardLayout.show(cardPanel, paneStr);
    }

    @Override
    public String getPanelTitle() {
        return Language.Get( "gui.patients.title" );
    }

    @Override
    public void reRender() {
        renderPatients();
    }
}
