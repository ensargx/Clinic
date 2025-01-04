package org.clinic;

import org.clinic.person.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ScheduleTest {

    @Test
    void addRendezvous() {
        Patient testPatient = new Patient("TestPatient", 123);
        Date testDate1 = new Date(1045958400000L); // hard to find non deprecated function
        Date testDate2 = new Date(1045958400000L);
        Date testDate3 = new Date(1045958400000L);
        Date testDate4 = new Date(1045958400000L);

        Schedule testSchedule = new Schedule(3);

        Assertions.assertNotNull(testSchedule.addRendezvous(testPatient, testDate1));
        Assertions.assertNotNull(testSchedule.addRendezvous(testPatient, testDate2));
        Assertions.assertNotNull(testSchedule.addRendezvous(testPatient, testDate3));
        Assertions.assertNull(testSchedule.addRendezvous(testPatient, testDate4));

    }

}