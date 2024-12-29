package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.gui.GUI;
import org.clinic.gui.IGUIListener;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.util.HashMap;

public class Main {
    private static boolean isGui;
    private static String fullPath = null;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        CRS crs = new CRS();

        GUI gui = new GUI(new IGUIListener() {
            @Override
            public HashMap<Integer, Hospital> getHospitals() {
                return crs.getHospitals();
            }

            @Override
            public void onHospitalCreated(String name, Integer id) {
                System.out.println("test name: "+name+" id: "+id);
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
                System.out.println("[dbg] patient create: name: "+name+" id: "+nationalId);
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
            public void onDoctorAdded(Section section, String name, Integer nationalId, Integer diplomaId, Integer maxPatients) {
                Doctor doctor = new Doctor(name, nationalId, diplomaId, maxPatients);

                try {
                    section.addDoctor(doctor);
                } catch (DuplicateInfoException e) {
                    GUI.ErrorMessage(e.getMessage());
                }
            }
        });

        gui.render( );

        System.out.println("Hello world!");
    }
}