package org.clinic.hospital;

import org.clinic.exception.DuplicateInfoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HospitalTest {

    @Test
    void getSection() {
        Hospital testHospital = new Hospital(123, "Test1");
        Section testSection = new Section(1, "Section1");
        Section testSectionCopy = new Section(1, "TestSectionCopy");
        testHospital.addSection(testSection);

        Assertions.assertEquals(testSection, testHospital.getSection(1));
        Assertions.assertNull(testHospital.getSection(10));
        Assertions.assertThrows(
                DuplicateInfoException.class,
                () -> testHospital.addSection(testSectionCopy)
        );
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
}