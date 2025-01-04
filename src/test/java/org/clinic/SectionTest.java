package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.person.Doctor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SectionTest {

    @Test
    void getId() {
        Section testSection = new Section(1, "TestSection");
        Assertions.assertEquals(1, testSection.getId());
    }

    @Test
    void getName() {
        String name = "TestSection";
        Section testSection = new Section(1, name);
        Assertions.assertEquals(name, testSection.getName());
    }

    @Test
    void getDoctor() {
        Section testSection = new Section(1, "TestSection");
        Doctor testDoctor = new Doctor("TestDoctor", 1, 1, 1);
        testSection.addDoctor(testDoctor);

        Assertions.assertEquals(testDoctor, testSection.getDoctor(1));
        Assertions.assertNull(testSection.getDoctor(2));
    }

    @Test
    void addDoctor() {
        Section testSection = new Section(1, "TestSection");
        Doctor testDoctor1 = new Doctor("TestDoctor1", 1, 1, 1);
        Doctor testDoctor2 = new Doctor( "TestDoctor2", 1, 2, 1);
        Doctor testDoctor3 = new Doctor( "TestDoctor3", 2, 1, 1);
        Doctor testDoctor4 = new Doctor( "testDoctor4", 2, 2, 1);

        // Asserts when an equal doctor is present
        // Test cases for being equal is bellow (testEquals)

        Assertions.assertDoesNotThrow(
            () -> testSection.addDoctor(testDoctor1)
        );
        Assertions.assertDoesNotThrow(
            () -> testSection.addDoctor(testDoctor4)
        );

        Assertions.assertThrows(
            DuplicateInfoException.class,
            () -> testSection.addDoctor(testDoctor2)
        );
        Assertions.assertThrows(
            DuplicateInfoException.class,
            () -> testSection.addDoctor(testDoctor3)
        );

    }

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