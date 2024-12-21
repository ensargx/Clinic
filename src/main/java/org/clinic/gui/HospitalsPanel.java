package org.clinic.gui;

import javax.swing.*;
import java.awt.*;

public class HospitalsPanel extends TabPanel {
    public HospitalsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Hospital Name Field
        TextField nameField = new TextField( 10 );
        nameField.setFlexibleSize(150, 25, 300, 30); // Esnek boyutlar
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT ); // Ortala
        this.add(nameField);

        // Hospital ID Field
        IntegerField idField = new IntegerField(10);
        idField.setFlexibleSize( 150, 25, 300, 30 ); // Esnek boyutlar
        idField.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortala
        this.add(idField);

        // Create Hospital Button
        Button createButton = new Button("Create Hospital");
        createButton.setFlexibleSize(150, 30, 300, 40); // Esnek boyutlar
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortala
        this.add(createButton);

        // Event: Hastane oluşturma
        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            Integer id = idField.getInteger();
            if (name.isEmpty() || idField.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // @TODO:
            // // Hastaneyi listeye ekle
            // hospitals.add("Name: " + name + ", ID: " + id);
            // updateHospitalList();
            nameField.clear();
            idField.clear();
        });

        JLabel hospitalListLabel = new JLabel("All Hospitals:");
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortala
        this.add(hospitalListLabel);
    }

    @Override
    public String getPanelTitle() {
        return "Hospitals";
    }
}
