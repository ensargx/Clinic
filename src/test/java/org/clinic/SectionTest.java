package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.hospital.Section;
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

        /**
         Asserts when an equal doctor is present
         Test cases for being equal is at {@link org.clinic.person.DoctorTest#testEquals()}
         */

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
        Section testSection1 = new Section(1, "TestSection");
        Section testSection2 = new Section(1, "TestSection");
        Section testSection3 = new Section(2, "TestSection");

        // Cannot have same ID
        Assertions.assertEquals(testSection1, testSection2);

        // Can have same name
        Assertions.assertNotEquals(testSection2, testSection3);
    }
}