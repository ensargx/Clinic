package org.clinic.gui;

import org.clinic.Hospital;

import java.util.HashMap;

public interface IGUIListener {
    HashMap<Integer, Hospital> getHospitals();
    void onHospitalAdded(String name, Integer id);
}
