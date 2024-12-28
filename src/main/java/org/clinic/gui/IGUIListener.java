package org.clinic.gui;

import org.clinic.Hospital;
import org.clinic.person.Patient;

import java.util.HashMap;

public interface IGUIListener {
    HashMap<Integer, Hospital> getHospitals();
    void onHospitalCreated(String name, Integer id);

    HashMap<Long, Patient> getPatients();
    void onPatientCreated( String name, Integer nationalId );

    void onSectionCreated( Hospital hospital, String name, Integer id );
}
