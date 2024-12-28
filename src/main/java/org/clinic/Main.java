package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.gui.GUI;
import org.clinic.gui.IGUIListener;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.util.Date;
import java.util.HashMap;

public class Main {
    private static boolean isGui;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        CRS crs = new CRS();

        Hospital h1 = new Hospital(1, "Ensar");

        Section dis = new Section(1, "Dis");
        Section kbb = new Section(2, "KBB");

        Doctor disci1 = new Doctor( "Ensar", 1, 1, 2);
        Doctor disci2 = new Doctor( "Mert", 12, 2, 4);
        dis.addDoctor(disci1);
        dis.addDoctor(disci2);

        Doctor kbbci1 = new Doctor( "Habil", 13, 3, 6);
        Doctor kbbci2 = new Doctor( "Orcun", 4, 4, 7);
        kbb.addDoctor(kbbci1);
        kbb.addDoctor(kbbci2);

        h1.addSection(dis);
        h1.addSection(kbb);

        Schedule disci1sc = disci1.getSchedule();

        Patient p1 = new Patient("Hasta1", 32);
        Date desired = new Date();
        desired.setTime(3232);
        disci1sc.addRendezvous(p1, desired);

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
        });

        gui.render( );

        System.out.println("Hello world!");
    }
}