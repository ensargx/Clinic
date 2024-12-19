package org.clinic;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class CRS {
    private HashMap<Long, Patient> patients = new HashMap<>();
    private LinkedList<Rendezvous> rendezvous = new LinkedList<>();
    private HashMap<Integer, Hospital> hospitals = new HashMap<>();

    public boolean makeRendezvous(long patientId, int hospitalId, int sectionId, int diplomaId, Date desiredDate) throws IDException {
        Patient patient = patients.get( patientId );
        if ( patient == null ) {
            throw new IDException("Cannot find patient with given id: " + patientId);
        }

        Hospital hospital = hospitals.get( hospitalId );
        if ( hospital == null ) {
            throw new IDException("Cannot find hospital with given id: " + hospitalId);
        }

        Section section = hospital.getSection( sectionId );
        if ( section == null ) {
            throw new IDException("Cannot find section with given id: " + sectionId);
        }

        Doctor doctor = section.getDoctor( diplomaId );
        if ( doctor == null ) {
            throw new IDException("Cannot find doctor with given id: " + diplomaId);
        }

        Schedule schedule = doctor.getSchedule();

        boolean ret = schedule.addRendezvous( patient, desiredDate );

        // the Rendezvous here are copies made with parameters.
        if ( ret ) {
            rendezvous.add( new Rendezvous( doctor, patient, desiredDate ) );
        }

        return ret;
    }

    public void saveTablesToDisk(String fullPath) {

    }

    public void loadTablesFromDisc(String fullPath) {

    }
}
