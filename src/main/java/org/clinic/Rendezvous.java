package org.clinic;

import java.io.Serializable;
import java.util.Date;

public class Rendezvous implements Serializable {
    private Doctor doctor;
    private Patient patient;
    private Date dateTime;

    public Rendezvous(Doctor doctor, Patient patient, Date dateTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDate() {
        return dateTime;
    }
}
