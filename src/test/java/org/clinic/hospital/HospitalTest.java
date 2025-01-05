package org.clinic.hospital;

import org.clinic.exception.DuplicateInfoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HospitalTest {

    @Test
    void getSection() {
        Hospital testHospital = new Hospital(123, "Test1");
        Section testSection = new Section(1, "Section1");
        testHospital.addSection(testSection);
        Assertions.assertEquals(testSection, testHospital.getSection(1));
        Assertions.assertNull(testHospital.getSection(10));
    }

    @Test
    void addSection() {
        Hospital testHospital = new Hospital(123, "Test1");
        Section testSection = new Section(1, "Section1");
        Section testSection2 = new Section(1, "Section2");
        testHospital.addSection( testSection );

        Assertions.assertEquals(testSection, testHospital.getSections().getFirst());
        Assertions.assertThrows(
            DuplicateInfoException.class,
            () -> testHospital.addSection(testSection2)
        );
    }

    @Test
    void getId() {
        Hospital testHospital = new Hospital(123, "Test1");
        Assertions.assertEquals( 123, testHospital.getId() );
    }

    @Test
    void getName() {
        Hospital testHospital = new Hospital(123, "Test1");
        Assertions.assertEquals( "Test1", testHospital.getName() );
    }
}