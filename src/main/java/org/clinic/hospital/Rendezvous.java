package org.clinic.hospital;

import org.clinic.person.Patient;

import java.io.Serializable;
import java.util.Date;

public class Rendezvous implements Serializable {
    private Patient patient;
    private Date dateTime;

    public Rendezvous(Patient patient, Date dateTime) {
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDate() {
        return dateTime;
    }
}
