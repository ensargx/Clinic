package org.clinic.gui;

import org.clinic.hospital.Hospital;
import org.clinic.hospital.Rendezvous;
import org.clinic.hospital.Section;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

// Naming this IGUIAdapter could be more precise
public interface IGUIListener {
    HashMap<Integer, Hospital> getHospitals();
    void onHospitalCreated(String name, Integer id);

    HashMap<Long, Patient> getPatients();
    void onPatientCreated( String name, Integer nationalId );

    LinkedList<Rendezvous> getRendezvouses();
    void onRendezvousCreated(Patient patient, Hospital hospital, Section section, Doctor doctor, Date date);

    void onSectionCreated( Hospital hospital, String name, Integer id );
    void onDoctorCreated(Section section, String name, Integer nationalId, Integer diplomaId, Integer maxPatients);

    void onFileLoad(String fullPath);
    void onFileSave(String fullPath);
    String getSavedFilePath();
}
