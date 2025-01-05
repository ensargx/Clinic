package org.clinic;

import org.clinic.hospital.Rendezvous;
import org.clinic.person.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class RendezvousTest {

    @Test
    void getPatient() {
        Patient testPatient = new Patient("TestPatient1", 123);
        Date testDate = new Date();

        Rendezvous testRendezvous = new Rendezvous(testPatient, testDate);

        Assertions.assertEquals(testPatient, testRendezvous.getPatient());
    }

    @Test
    void getDate() {
        Patient testPatient = new Patient("TestPatient1", 123);
        Date testDate = new Date();

        Rendezvous testRendezvous = new Rendezvous(testPatient, testDate);

        Assertions.assertEquals(testDate, testRendezvous.getDate());
    }
}