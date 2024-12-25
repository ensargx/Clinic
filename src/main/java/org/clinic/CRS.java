package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.exception.IDException;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class CRS {
    private HashMap<Long, Patient> patients = new HashMap<>();
    private LinkedList<Rendezvous> rendezvouses = new LinkedList<>();
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

        Rendezvous rendezvous = schedule.addRendezvous( patient, desiredDate );

        if ( rendezvous != null ) {
            rendezvouses.add( rendezvous );
            return true;
        } else {
            return false;
        }
    }

    public Hospital createHospital( Integer id, String name ) throws DuplicateInfoException {
        if ( hospitals.containsKey( id ) ) {
            throw new DuplicateInfoException("Hospital with given id exists: " + id);
        }

        Hospital hospital = new Hospital( id, name );
        hospitals.put( id, hospital );
        return hospital;
    }

    public Patient createPatient( String name, long nationalId ) throws DuplicateInfoException {
        if (patients.containsKey( nationalId ) ) {
            throw new DuplicateInfoException("Patient with given nationalId exist: " + nationalId);
        }

        Patient patient = new Patient( name, nationalId );
        patients.put( nationalId, patient );
        return patient;
    }

    public HashMap<Integer, Hospital> getHospitals() {
        return hospitals;
    }

    public void saveTablesToDisk(String fullPath) {

    }

    public void loadTablesFromDisc(String fullPath) {

    }
}
