package org.clinic.gui.panels;

import org.clinic.Hospital;
import org.clinic.Section;
import org.clinic.gui.*;
import org.clinic.gui.lib.*;
import org.clinic.lang.Language;
import org.clinic.person.Doctor;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.function.BiConsumer;

public class HospitalsPanel extends GTabPanel {
    private IGUIListener listener;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel hospitalsPanel = new JPanel();
    private JPanel sectionsPanel = new JPanel();
    private JPanel doctorsPanel = new JPanel();
    private JPanel schedulePanel = new JPanel();

    private static final String sHospitals = "Hospitals";
    private static final String sSections = "Sections";
    private static final String sDoctors = "Doctors";
    private static final String sSchedule = "Schedule";

    public HospitalsPanel( IGUIListener listener ) {
        this.listener = listener;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(hospitalsPanel, sHospitals);
        cardPanel.add(sectionsPanel, sSections);
        cardPanel.add(doctorsPanel, sDoctors);
        cardPanel.add(schedulePanel, sSchedule);

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
        JLabel hospitalListLabel = new GLabel( "gui.hospital.all_hospitals" );
        hospitalListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info panel for each hospital
        JPanel listHospitalsPanel = new JPanel();
        listHospitalsPanel.setLayout( new BoxLayout(listHospitalsPanel, BoxLayout.Y_AXIS) );
        JPanel listHospitalsPanelWrapper= new JPanel( new BorderLayout() );
        listHospitalsPanelWrapper.add(listHospitalsPanel, BorderLayout.PAGE_START);
        listener.getHospitals().forEach((id, hospital) -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            infoPanel.add( new GLabel( "gui.hospital.label_name", hospital.getName() ) );
            infoPanel.add( new GLabel( "gui.hospital.label_id", hospital.getId() ) );

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            GButton completeButton = new GButton("gui.hospital.hospital_details");
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionPanel.setOpaque(false);

            completeButton.addActionListener(e -> renderSections(hospital));
            actionPanel.add(completeButton);
            taskPanel.add(actionPanel, BorderLayout.EAST);

            taskPanel.setPreferredSize(new Dimension(400, 100));
            listHospitalsPanel.add(taskPanel);
        });

        hospitalsPanel.add(createButton);
        hospitalsPanel.add(hospitalListLabel);
        hospitalsPanel.add(listHospitalsPanelWrapper);

        hospitalsPanel.revalidate();
        hospitalsPanel.repaint();

        switchTab(HospitalsPanel.sHospitals);
    }

    private void renderSections(Hospital hospital) {
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

            infoPanel.add( new GLabel( "gui.hospital.label_section_name", section.getName() ) );
            infoPanel.add( new GLabel( "gui.hospital.label_section_id", section.getId() ) );

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            GButton completeButton = new GButton( "gui.hospital.section_details" );
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
        newDoctorButton.addActionListener( e -> openNewDoctorDialog(hospital, section) );

        // All doctors list
        JLabel doctorsListPanel = new GLabel( "gui.hospital.all_doctors" );
        doctorsListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        doctorsPanel.add(backButton);
        doctorsPanel.add(newDoctorButton);
        doctorsPanel.add(doctorsListPanel);

        section.getDoctors().forEach(doctor -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            infoPanel.add(new GLabel("gui.hospital.doctor_name_label", doctor.getName() ));
            infoPanel.add(new GLabel("gui.hospital.doctor_diploma_id_label", doctor.getDiplomaId() ));
            infoPanel.add(new GLabel("gui.hospital.doctor_national_id_label", doctor.getNationalId() ));

            taskPanel.add(infoPanel, BorderLayout.CENTER);

            GButton schedulesButton = new GButton( "gui.hospital.doctor_schedule" );
            JPanel actionPanel = new JPanel(new FlowLayout());

            schedulesButton.addActionListener( e -> renderSchedule(hospital, section, doctor) );

            actionPanel.add(schedulesButton);
            taskPanel.add(actionPanel, BorderLayout.EAST);

            doctorsPanel.add(taskPanel);
        });

        doctorsPanel.revalidate();
        doctorsPanel.repaint();

        switchTab(HospitalsPanel.sDoctors);
    }

    private void renderSchedule(Hospital hospital, Section section, Doctor doctor) {
        schedulePanel.removeAll();

        schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.Y_AXIS));

        // Back button
        GButton backButton = new GButton( "gui.back" );
        backButton.addActionListener(e -> {
            renderDoctors(hospital, section);
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create Schedule Button
        GButton newDoctorButton = new GButton( "gui.hospital.new_schedule" );
        newDoctorButton.setFlexibleSize(150, 30, 300, 40);
        newDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newDoctorButton.addActionListener( e -> GUI.switchToPanel(GUI.PANEL_RENDEZVOUS) );

        // All schedule list
        GLabel doctorsListPanelLabel = new GLabel( "gui.hospital.all_schedules" );
        doctorsListPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info panel for each rendezvous
        JPanel listRendezvousPanel = new JPanel();
        listRendezvousPanel.setLayout( new BoxLayout(listRendezvousPanel, BoxLayout.Y_AXIS) );
        JPanel listRendezvousPanelWrapper= new JPanel( new BorderLayout() );
        listRendezvousPanelWrapper.add(listRendezvousPanel, BorderLayout.PAGE_START);
        doctor.getSchedule().getSessions().forEach( rendezvous -> {
            JPanel taskPanel = new JPanel();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
            String date = dateFormat.format(rendezvous.getDate());

            infoPanel.add( new GLabel( "gui.hospital.label_rendezvous_patient_name_format", rendezvous.getPatient().getName() ) );
            infoPanel.add( new GLabel( "gui.hospital.label_rendezvous_date_format", date ) );

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

        schedulePanel.add(backButton);
        schedulePanel.add(newDoctorButton);
        schedulePanel.add(doctorsListPanelLabel);
        schedulePanel.add(listRendezvousPanelWrapper);

        schedulePanel.revalidate();
        schedulePanel.repaint();

        switchTab(sSchedule);
    }

    private void openDialogStrInt(String labelTitle, String label1, String label2, String errorLabel, BiConsumer<String, Integer> callback ) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), Language.Get( labelTitle ), true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Name Field
        GTextField nameField = new GTextField( 10 );
        nameField.setPlaceholder( label1 );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        dialog.add(nameField);

        // ID Field
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
        openDialogStrInt( "gui.hospital.create_hospital_popup","gui.hospital.name_placeholder", "gui.hospital.id_placeholder", "gui.hospital.error_name_id_null",
            (name, id) -> {
                listener.onHospitalCreated( name, id );
                renderHospitals();
        });
    }

    private void openCreateSectionDialog(Hospital hospital) {
        openDialogStrInt("gui.hospital.create_section_popup", "gui.hospital.section_name_placeholder", "gui.hospital.section_id_placeholder", "gui.hospital.error_section_name_id_null",
            (name, id) -> {
                listener.onSectionCreated(hospital, name, id);
                renderSections(hospital);
        });
    }

    private void openNewDoctorDialog(Hospital hospital, Section section) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), Language.Get( "gui.hospital.new_doctor" ), true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Name Field
        GTextField nameField = new GTextField( 10 );
        nameField.setPlaceholder( "gui.hospital.new_doctor_name" );
        nameField.setFlexibleSize(150, 25, 300, 30);
        nameField.setAlignmentX( Component.CENTER_ALIGNMENT );
        dialog.add(nameField);

        // national ID Field
        GIntegerField nationalIdField = new GIntegerField(10);
        nationalIdField.setPlaceholder( "gui.hospital.new_doctor_national_id" );
        nationalIdField.setFlexibleSize( 150, 25, 300, 30 );
        nationalIdField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(nationalIdField);

        // diploma ID Field
        GIntegerField diplomaIdField = new GIntegerField(10);
        diplomaIdField.setPlaceholder( "gui.hospital.new_doctor_diploma_id" );
        diplomaIdField.setFlexibleSize( 150, 25, 300, 30 );
        diplomaIdField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(diplomaIdField);

        // Max Patients Field
        GIntegerField maxPatientsField = new GIntegerField(10);
        maxPatientsField.setPlaceholder( "gui.hospital.new_doctor_max_patients" );
        maxPatientsField.setFlexibleSize( 150, 25, 300, 30 );
        maxPatientsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(maxPatientsField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton(Language.Get("gui.save"));
        JButton cancelButton = new JButton(Language.Get("gui.cancel"));

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty() || !nationalIdField.isInteger() || nationalIdField.isEmpty() || !diplomaIdField.isInteger() || diplomaIdField.isEmpty() || !maxPatientsField.isInteger() || maxPatientsField.isEmpty() ) {
                JOptionPane.showOptionDialog(
                        this,
                        Language.Get( "gui.hospital.new_doctor_error_fields" ),
                        Language.Get("gui.error"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new String[] { Language.Get("gui.ok") },
                        Language.Get("gui.ok")
                );
                return;
            }
            Integer nationalId = nationalIdField.getInteger();
            Integer diplomaId = diplomaIdField.getInteger();
            Integer maxPatients = maxPatientsField.getInteger();
            nameField.clear();
            diplomaIdField.clear();
            nationalIdField.clear();
            maxPatientsField.clear();

            // callback.accept(name, nationalId, diplomaId);
            listener.onDoctorAdded(section, name, nationalId, diplomaId, maxPatients);

            renderDoctors(hospital, section);

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

    @Override
    public void reRender() {
        renderHospitals();
    }
}
