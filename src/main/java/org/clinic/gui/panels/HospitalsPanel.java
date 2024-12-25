package org.clinic.gui.panels;

import org.clinic.gui.*;
import org.clinic.gui.TextField;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;

public class HospitalsPanel extends TabPanel {
    private IGUIListener listener;
    private JPanel listPanel;

    public HospitalsPanel( IGUIListener listener ) {
        this.listener = listener;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create Hospital Button
        GButton createButton = new GButton( "gui.hospital.create" );
        createButton.setFlexibleSize(150, 30, 300, 40);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(createButton);

        // Event: create hospital
        createButton.addActionListener( e -> openAddHospitalDialog() );

        JLabel hospitalListLabel = new GLabel( "gui.hospital.all_hospitals" );
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(hospitalListLabel);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        this.add(scrollPane, BorderLayout.CENTER);

    }

    public void renderHospitals() {
        listPanel.removeAll();

        // Info panel for each hospital
        listener.getHospitals().forEach(( id, hospital ) -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.add(new JLabel("Title: " + hospital.getName() ));
            infoPanel.add(new JLabel("Description: " + id ));

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            JButton completeButton = new JButton("Complete");
            JPanel actionPanel = new JPanel(new FlowLayout());

            actionPanel.add(completeButton);
            taskPanel.add(actionPanel, BorderLayout.EAST);

            listPanel.add(taskPanel);
        });

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void openAddHospitalDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), Language.Get( "gui.hospital.create_hospital_popup" ), true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Hospital Name Field
        TextField nameField = new TextField( 10 );
        nameField.setPlaceholder( "gui.hospital.name_placeholder" );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        dialog.add(nameField);

        // Hospital ID Field
        IntegerField idField = new IntegerField(10);
        idField.setPlaceholder( "gui.hospital.id_placeholder" );
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
                        Language.Get("gui.hospital.error_name_id_null"),
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
            listener.onHospitalCreated( name, id );
            renderHospitals();

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    @Override
    public String getPanelTitle() {
        return Language.Get( "gui.hospital.title" );
    }
}
