package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.exception.IDException;
import org.clinic.gui.GUI;
import org.clinic.gui.IGUIListener;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    private static String savedPath = null;

    public static void main(String[] args) {
        CRS crs = new CRS();

        GUI gui = new GUI(new IGUIListener() {
            @Override
            public HashMap<Integer, Hospital> getHospitals() {
                return crs.getHospitals();
            }

            @Override
            public void onHospitalCreated(String name, Integer id) {
                try {
                    crs.createHospital( id ,name );
                } catch ( DuplicateInfoException err ) {
                    GUI.ErrorMessage( err.getMessage() );
                }
            }

            @Override
            public HashMap<Long, Patient> getPatients() {
                return crs.getPatients();
            }

            @Override
            public void onPatientCreated(String name, Integer nationalId) {
                try {
                    crs.createPatient( name, nationalId );
                } catch ( DuplicateInfoException err ) {
                    GUI.ErrorMessage( err.getMessage() );
                }
            }

            @Override
            public void onSectionCreated(Hospital hospital, String name, Integer id) {
                Section section = new Section( id, name );

                try {
                    hospital.addSection(section);
                } catch (DuplicateInfoException e) {
                    GUI.ErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onDoctorCreated(Section section, String name, Integer nationalId, Integer diplomaId, Integer maxPatients) {
                Doctor doctor = new Doctor(name, nationalId, diplomaId, maxPatients);

                try {
                    section.addDoctor(doctor);
                } catch (DuplicateInfoException e) {
                    GUI.ErrorMessage(e.getMessage());
                }
            }

            @Override
            public LinkedList<Rendezvous> getRendezvouses() {
                return crs.getRendezvouses();
            }

            @Override
            public void onRendezvousCreated(Patient patient, Hospital hospital, Section section, Doctor doctor, Date date) {
                try {
                    if (!crs.makeRendezvous(patient.getNationalId(), hospital.getId(), section.getId(), doctor.getDiplomaId(), date)) {
                        GUI.ErrorMessage("Cannot create rendezvous, make sure not to exceed doctors daily limit. ");
                    }
                } catch (IDException e) {
                    GUI.ErrorMessage( e.getMessage() );
                }
            }

            @Override
            public void onFileLoad(String fullPath) {
                savedPath = fullPath;
                crs.loadTablesFromDisc(fullPath);
            }

            @Override
            public void onFileSave(String fullPath) {
                savedPath = fullPath;
                crs.saveTablesToDisk(fullPath);
            }

            @Override
            public String getSavedFilePath() {
                return savedPath;
            }

        });

        gui.render( );
    }
}