package org.clinic.gui.panels;

import org.clinic.gui.Button;
import org.clinic.gui.IntegerField;
import org.clinic.gui.TabPanel;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;

public class HospitalsPanel extends TabPanel {
    public HospitalsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Hospital Name Field
        org.clinic.gui.TextField nameField = new org.clinic.gui.TextField( 10 );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.add(nameField);

        // Hospital ID Field
        IntegerField idField = new IntegerField(10);
        idField.setFlexibleSize( 150, 25, 300, 30 );
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(idField);

        // Create Hospital Button
        org.clinic.gui.Button createButton = new Button( Language.Get("gui.hospital.create") );
        Language.AddLanguageCallback( () -> {
            createButton.setText( Language.Get("gui.hospital.create") );
        } );

        createButton.setFlexibleSize(150, 30, 300, 40);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(createButton);

        // Event: create hospital
        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            Integer id = idField.getInteger();
            if (name.isEmpty() || idField.isEmpty()) {
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
            // @TODO:
            // // Hastaneyi listeye ekle
            // hospitals.add("Name: " + name + ", ID: " + id);
            // updateHospitalList();
            nameField.clear();
            idField.clear();
        });

        JLabel hospitalListLabel = new JLabel( Language.Get("gui.hospital.all_hospitals") );
        Language.AddLanguageCallback(()->{
           hospitalListLabel.setText( Language.Get("gui.hospital.all_hospitals") );
        });
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(hospitalListLabel);
    }

    @Override
    public String getPanelTitle() {
        return Language.Get( "gui.hospital.title" );
    }
}
