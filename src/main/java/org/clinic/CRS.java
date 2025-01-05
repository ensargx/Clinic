package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.exception.IDException;
import org.clinic.hospital.Hospital;
import org.clinic.hospital.Rendezvous;
import org.clinic.hospital.Schedule;
import org.clinic.hospital.Section;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class CRS {
    private HashMap<Long, Patient> patients = new HashMap<>();
    private LinkedList<Rendezvous> rendezvouses = new LinkedList<>();
    private HashMap<Integer, Hospital> hospitals = new HashMap<>();

    public synchronized boolean makeRendezvous(long patientId, int hospitalId, int sectionId, int diplomaId, Date desiredDate) throws IDException {
        Patient patient = patients.get( patientId );
        if ( patient == null ) {
            throw new IDException("error.crs.cannot_find_patient_id_format", patientId);
        }

        Hospital hospital = hospitals.get( hospitalId );
        if ( hospital == null ) {
            throw new IDException("error.crs.cannot_find_hospital_id_format", hospitalId);
        }

        Section section = hospital.getSection( sectionId );
        if ( section == null ) {
            throw new IDException("error.crs.cannot_find_section_id_format", sectionId);
        }

        Doctor doctor = section.getDoctor( diplomaId );
        if ( doctor == null ) {
            throw new IDException("error.crs.cannot_find_doctor_id_format", diplomaId);
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

    public synchronized Hospital createHospital( Integer id, String name ) throws DuplicateInfoException {
        if ( hospitals.containsKey( id ) ) {
            throw new DuplicateInfoException("error.crs.given_hospital_id_exists_format", id);
        }

        Hospital hospital = new Hospital( id, name );
        hospitals.put( id, hospital );
        return hospital;
    }

    public synchronized HashMap<Long, Patient> getPatients() {
        return patients;
    }

    public synchronized Patient createPatient(String name, long nationalId ) throws DuplicateInfoException {
        if (patients.containsKey( nationalId ) ) {
            throw new DuplicateInfoException("error.crs.given_national_id_id_exists_format", nationalId);
        }

        Patient patient = new Patient( name, nationalId );
        patients.put( nationalId, patient );
        return patient;
    }

    public synchronized HashMap<Integer, Hospital> getHospitals() {
        return hospitals;
    }

    public synchronized LinkedList<Rendezvous> getRendezvouses() {
        return rendezvouses;
    }

    public synchronized void saveTablesToDisk(String fullPath) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( fullPath ));

            oos.writeObject(hospitals);
            oos.writeObject(rendezvouses);
            oos.writeObject(patients);

        } catch (Exception e) {
            // should not happen
            System.out.println(e.getMessage());
        }
    }

    public synchronized void loadTablesFromDisc(String fullPath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream( fullPath ));

            HashMap<Integer, Hospital> inhost = (HashMap<Integer, Hospital>) ois.readObject();
            if ( inhost == null ) {
                return;
            }

            LinkedList<Rendezvous> inrandez = (LinkedList<Rendezvous>) ois.readObject();
            if ( inrandez == null ) {
                return;
            }

            HashMap<Long, Patient> patients = (HashMap<Long, Patient>) ois.readObject();
            if ( patients == null ) {
                return;
            }

            this.hospitals = inhost;
            this.rendezvouses = inrandez;
            this.patients = patients;

        } catch ( Exception e ) {
            // should not happen
            System.out.println(e.getMessage());
        }

    }
}
