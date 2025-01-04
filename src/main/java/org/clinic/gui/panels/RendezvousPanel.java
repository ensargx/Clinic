package org.clinic.gui.panels;

import org.clinic.Hospital;
import org.clinic.Section;
import org.clinic.gui.GUI;
import org.clinic.gui.IGUIListener;
import org.clinic.gui.lib.GButton;
import org.clinic.gui.lib.GLabel;
import org.clinic.gui.lib.GTabPanel;
import org.clinic.lang.Language;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

public class RendezvousPanel extends GTabPanel {
    private IGUIListener listener;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel rendezvousPanel = new JPanel();
    private JPanel newRendezvousPanel = new JPanel();

    private Patient selectedPatient = null;
    private Hospital selectedHospital = null;
    private Section selectedSection = null;
    private Doctor selectedDoctor = null;

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

    @FunctionalInterface
    public interface Callback<T> {
        void onSelect(T value);
    }

    static class SelectorGButtonList<T> extends JPanel {
        private ArrayList<GButton> buttons = new ArrayList<>();
        private T selected = null;
        private Callback<T> callback;

        public SelectorGButtonList(Callback<T> callback) {
            this.callback = callback;
        }

        public void add(GButton button, T select) {
            button.addActionListener( e -> onSelect(select) );
            buttons.add(button);
            super.add(button);
            this.rePaint();
        }

        public void rePaint() {
            this.revalidate();
            this.repaint();
        }

        private void onSelect(T select) {
            selected = select;
            callback.onSelect(select);
        }

        public T getSelected() {
            return selected;
        }
    }

    private void renderNewRendezvous() {
        newRendezvousPanel.removeAll();
        newRendezvousPanel.setLayout(new BoxLayout(newRendezvousPanel, BoxLayout.Y_AXIS));

        // Back button
        GButton backButton = new GButton( "gui.back" );
        backButton.addActionListener(e -> {
            renderRendezvous();
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newRendezvousPanel.add(backButton);

        renderPatientsList();
        renderHospitalsList();
        renderSectionsList();
        renderDoctorsList();

        newRendezvousPanel.revalidate();
        newRendezvousPanel.repaint();

        switchTab(RendezvousPanel.sNewRendezvous);
    }

    private void renderPatientsList() {
        // Select a patient (vertical buttons only one is active)
        SelectorGButtonList<Patient> patientSelectorList = new SelectorGButtonList<>(patient -> {
            System.out.println("[DEBUG]: Selected patient: " + patient.getName());
            // if selected patient is changed, rest should also be changed
            if (patient != selectedPatient) {
                selectedDoctor = null;
                selectedHospital = null;
                selectedSection = null;
            }
            selectedPatient = patient;
            renderNewRendezvous();
        });

        // Add each patient to the list
        listener.getPatients().forEach((national_id, patient) -> patientSelectorList.add(new GButton(patient.getName()), patient));

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(patientSelectorList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GLabel label = new GLabel("gui.rendezvous.select_patient");

        newRendezvousPanel.add(label);
        newRendezvousPanel.add(scrollPane);
    }

    private void renderHospitalsList() {
        // only render if patient is selected
        if ( selectedPatient == null )
            return;

        SelectorGButtonList<Hospital> hospitalSelectorList = new SelectorGButtonList<>( hospital -> {
            System.out.println("[DEBUG]: Selected hospital: " + hospital.getName());
            if ( hospital != selectedHospital ) {
                selectedSection = null;
                selectedDoctor = null;
            }
            selectedHospital = hospital;
            renderNewRendezvous();
        } );

        // add each hospital to the list
        listener.getHospitals().forEach( (id, hospital) -> {
            GButton hospitalButton = new GButton( hospital.getName() );
            hospitalSelectorList.add(hospitalButton, hospital);
        } );

        JScrollPane scrollPane = new JScrollPane(hospitalSelectorList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GLabel label = new GLabel("gui.rendezvous.select_hospital");

        newRendezvousPanel.add(label);
        newRendezvousPanel.add(scrollPane);
    }

    private void renderSectionsList() {
        // only render if patient is selected
        if ( selectedHospital == null )
            return;

        SelectorGButtonList<Section> sectionSelectorList = new SelectorGButtonList<>( section -> {
            System.out.println("[DEBUG]: Selected section: " + section.getName());
            if ( section != selectedSection ) {
                selectedDoctor = null;
            }
            selectedSection = section;
            renderNewRendezvous();
        } );

        // add each hospital to the list
        selectedHospital.getSections().forEach( section -> {
            GButton sectionButton = new GButton( section.getName() );
            sectionSelectorList.add(sectionButton, section);
        } );

        JScrollPane scrollPane = new JScrollPane(sectionSelectorList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GLabel label = new GLabel("gui.rendezvous.select_section");

        newRendezvousPanel.add(label);
        newRendezvousPanel.add(scrollPane);
    }

    private void renderDoctorsList() {
        // only render if patient is selected
        if ( selectedSection == null )
            return;

        SelectorGButtonList<Doctor> doctorSelectorList = new SelectorGButtonList<>( doctor -> {
            System.out.println("[DEBUG]: Selected doctor: " + doctor.getName());
            selectedDoctor = doctor;
            renderNewRendezvous();
            createNewRendezvousPopup(selectedPatient, selectedHospital, selectedSection, selectedDoctor);
        } );

        // add each hospital to the list
        selectedSection.getDoctors().forEach( doctor -> {
            GButton sectionButton = new GButton( doctor.getName() );
            doctorSelectorList.add(sectionButton, doctor);
        } );

        JScrollPane scrollPane = new JScrollPane(doctorSelectorList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GLabel label = new GLabel("gui.rendezvous.select_doctor");

        newRendezvousPanel.add(label);
        newRendezvousPanel.add(scrollPane);
    }

    // will get the date and call the listener
    private void createNewRendezvousPopup(Patient patient, Hospital hospital, Section section, Doctor doctor) {
        // those cannot be null
        if ( patient == null || hospital == null || section == null || doctor == null ) {
            // big error, must not happen
            // just return
            return;
        }

        // a lil bit gpt code...
        JDialog dialog = new JDialog(GUI.getMainFrame(), Language.Get("gui.rendezvous.new_rendezvous"), true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        // JSpinner select date
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date()); // defaults to today

        GButton okButton = new GButton( "gui.ok" );
        GButton cancelButton = new GButton("gui.cancel");

        // sanırım bir java saçmalığı, sorgulamayacağım..
        // Seçilen tarihi saklamak için bir array (lambda uyumluluğu için)
        final Date[] selectedDate = {null};

        okButton.addActionListener(e -> {
            Date date = (Date) dateSpinner.getValue();
            listener.onRendezvousCreated(patient, hospital, section, doctor, date);
            reRender();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.add(new GLabel("gui.rendezvous.select_date"));
        dialog.add(dateSpinner);
        dialog.add(okButton);
        dialog.add(cancelButton);

        dialog.setLocationRelativeTo(GUI.getMainFrame());
        dialog.setVisible(true);
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
        selectedPatient = null;
        selectedHospital = null;
        selectedSection = null;
        selectedDoctor = null;

        renderRendezvous();
    }
}
