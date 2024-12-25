package org.clinic;

import org.clinic.person.Doctor;
import org.clinic.person.Patient;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Schedule implements Serializable {
    private LinkedList<Rendezvous> sessions = new LinkedList<>();
    private int maxPatientPerDay;
    private Doctor doctor;

    public Schedule(Doctor doctor, int maxPatientPerDay) {
        this.maxPatientPerDay = maxPatientPerDay;
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Rendezvous addRendezvous(Patient patient, Date desiredDate) {
        if ( !ensureDate( desiredDate ) ) {
            return null;
        }

        Rendezvous rendezvous = new Rendezvous( doctor, patient, desiredDate );

        sessions.add( rendezvous );

        return rendezvous;
    }

    // helper method to ensure date is available to create Rendezvous
    private boolean ensureDate(Date date) {
        int rendezvousOnDesired = 0;

        for ( Rendezvous rendezvous : sessions ) {
            if ( isSameDay( rendezvous.getDate(), date ) ) {
                ++rendezvousOnDesired;
                if ( rendezvousOnDesired > maxPatientPerDay ) {
                    return false;
                }
            }
        }

        return true;
    }

    // helper method for checking if two dates are in the same day
    private boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d1).equals(sdf.format(d2));
    }

}
