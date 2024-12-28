package org.clinic.gui.panels;

import org.clinic.Hospital;
import org.clinic.Section;
import org.clinic.gui.*;
import org.clinic.gui.GTextField;
import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class HospitalsPanel extends GTabPanel {
    private IGUIListener listener;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel hospitalsPanel = new JPanel();
    private JPanel sectionsPanel = new JPanel();
    private JPanel doctorsPanel = new JPanel();

    private static final String sHospitals = "Hospitals";
    private static final String sSections = "Sections";
    private static final String sDoctors = "Doctors";

    public HospitalsPanel( IGUIListener listener ) {
        this.listener = listener;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(hospitalsPanel, sHospitals);
        cardPanel.add(sectionsPanel, sSections);
        cardPanel.add(doctorsPanel, sDoctors);

        renderHospitals();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(cardPanel, BorderLayout.CENTER);
    }

    private void switchTab(String paneStr) {
        cardLayout.show(cardPanel, paneStr);
    }

    private void renderHospitals() {
        hospitalsPanel.removeAll();

        hospitalsPanel.setLayout(new BoxLayout(hospitalsPanel, BoxLayout.Y_AXIS));

        GButton createButton = new GButton( "gui.hospital.create" );
        createButton.setFlexibleSize(150, 30, 300, 40);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Event: create hospital
        createButton.addActionListener( e -> openAddHospitalDialog() );

        // Create Hospital Button
        hospitalsPanel.add(createButton);

        JLabel hospitalListLabel = new GLabel( "gui.hospital.all_hospitals" );
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hospitalsPanel.add(hospitalListLabel);

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

            completeButton.addActionListener(e -> {
                renderSections(hospital);
            });

            actionPanel.add(completeButton);
            taskPanel.add(actionPanel, BorderLayout.EAST);

            hospitalsPanel.add(taskPanel);
        });

        hospitalsPanel.revalidate();
        hospitalsPanel.repaint();

        switchTab(HospitalsPanel.sHospitals);
    }


    private void renderSections(Hospital hospital ) {
        sectionsPanel.removeAll();

        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));

        GButton backButton = new GButton( "gui.back" );
        backButton.addActionListener(e -> {
            renderHospitals();
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Event: create section
        GButton createButton = new GButton( "gui.hospital.create_section" );
        createButton.setFlexibleSize(150, 30, 300, 40);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.addActionListener( e -> openCreateSectionDialog(hospital) );

        JLabel hospitalListLabel = new GLabel( "gui.hospital.all_sections" );
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sectionsPanel.add(backButton);
        sectionsPanel.add(createButton);
        sectionsPanel.add(hospitalListLabel);

        // Info panel for each section
        hospital.getSections().forEach(section -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.add(new JLabel("NAME: " + section.getName() ));
            infoPanel.add(new JLabel("ID: " + section.getId() ));

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            JButton completeButton = new JButton("Complete");
            JPanel actionPanel = new JPanel(new FlowLayout());

            completeButton.addActionListener(e -> {
                renderDoctors(hospital, section);
            });

            actionPanel.add(completeButton);
            taskPanel.add(actionPanel, BorderLayout.EAST);

            sectionsPanel.add(taskPanel);
        });

        sectionsPanel.revalidate();
        sectionsPanel.repaint();

        switchTab(HospitalsPanel.sSections);
    }

    private void renderDoctors(Hospital hospital, Section section) {
        doctorsPanel.removeAll();

        doctorsPanel.setLayout(new BoxLayout(doctorsPanel, BoxLayout.Y_AXIS));

        GButton backButton = new GButton( "gui.back" );
        backButton.addActionListener(e -> {
            renderSections(hospital);
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Event: create doctor
        GButton newDoctorButton = new GButton( "gui.hospital.new_doctor" );
        newDoctorButton.setFlexibleSize(150, 30, 300, 40);
        newDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newDoctorButton.addActionListener( e -> openNewDoctorDialog(section) );

        // All doctors list
        JLabel doctorsListPanel = new GLabel( "gui.hospital.all_doctors" );
        doctorsListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        doctorsPanel.add(backButton);
        doctorsPanel.add(newDoctorButton);
        doctorsPanel.add(doctorsListPanel);

        section.getDoctors().forEach(doctor -> {
           JLabel label = new JLabel( doctor.toString() );
           doctorsPanel.add(label);
        });

        doctorsPanel.revalidate();
        doctorsPanel.repaint();

        switchTab(HospitalsPanel.sDoctors);
    }

    private void openDialogStrInt(String label1, String label2, String errorLabel, BiConsumer<String, Integer> callback ) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), Language.Get( "gui.hospital.create_hospital_popup" ), true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Hospital Name Field
        GTextField nameField = new GTextField( 10 );
        nameField.setPlaceholder( label1 );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        dialog.add(nameField);

        // Hospital ID Field
        GIntegerField idField = new GIntegerField(10);
        idField.setPlaceholder( label2 );
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
                        Language.Get(errorLabel),
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

            callback.accept(name, id);

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private void openAddHospitalDialog() {
        openDialogStrInt("gui.hospital.name_placeholder", "gui.hospital.id_placeholder", "gui.hospital.error_name_id_null",
            (name, id) -> {
                listener.onHospitalCreated( name, id );
                renderHospitals();
        });
    }

    private void openCreateSectionDialog(Hospital hospital) {
        openDialogStrInt("gui.hospital.section_name_placeholder", "gui.hospital.section_id_placeholder", "gui.hospital.error_section_name_id_null",
            (name, id) -> {
                listener.onSectionCreated(hospital, name, id);
                renderSections(hospital);
        });
    }

    private void openNewDoctorDialog(Section section) {

    }

    @Override
    public String getPanelTitle() {
        return Language.Get( "gui.hospital.title" );
    }
}
