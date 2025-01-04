package org.clinic.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void testEquals() {
        Doctor testDoctor1 = new Doctor("TestDoctor", 1, 1, 1);
        Doctor testDoctor2 = new Doctor("TestDoctor", 1, 2, 1);
        Doctor testDoctor3 = new Doctor("TestDoctor", 2, 1, 1);
        Doctor testDoctor4 = new Doctor("TestDoctor", 2, 2, 1);

        // Cannot have same national ID
        Assertions.assertEquals(testDoctor1, testDoctor2);
        Assertions.assertEquals(testDoctor3, testDoctor4);

        // Cannot have same diploma ID
        Assertions.assertEquals(testDoctor1, testDoctor3);
        Assertions.assertEquals(testDoctor2, testDoctor4);

        // Can have same name & maxPatientPerDay
        Assertions.assertNotEquals(testDoctor1, testDoctor4);
        Assertions.assertNotEquals(testDoctor2, testDoctor3);
    }
}