package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.exception.IDException;
import org.clinic.hospital.Hospital;
import org.clinic.hospital.Section;
import org.clinic.person.Doctor;
import org.clinic.person.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class CRSTest {

    @Test
    void makeRendezvous() {
        CRS testCrs = new CRS();

        Integer patientId = 1;
        Integer hospitalId = 2;
        Integer sectionId = 3;
        Integer doctorId = 5;
        Integer maxPatients = 2;
        Date date = new Date();

        testCrs.createHospital(hospitalId, "TestHospital");
        Hospital testHospital = testCrs.getHospitals().get(2);

        Section testSection = new Section(sectionId, "TestSection");
        testHospital.addSection(testSection);

        Doctor testDoctor = new Doctor("TestDoctor", 4, doctorId, maxPatients);
        testSection.addDoctor(testDoctor);

        testCrs.createPatient("TestPatient", patientId);

        // Patient ID Not Found
        Assertions.assertThrows(
            IDException.class,
            () -> testCrs.makeRendezvous(patientId+1, hospitalId, sectionId, doctorId, date)
        );

        // Hospital ID Not Found
        Assertions.assertThrows(
            IDException.class,
            () -> testCrs.makeRendezvous(patientId, hospitalId+1, sectionId, doctorId, date)
        );

        // Section ID Not Found
        Assertions.assertThrows(
            IDException.class,
            () -> testCrs.makeRendezvous(patientId, hospitalId, sectionId+1, doctorId, date)
        );

        // Doctor ID Not Found
        Assertions.assertThrows(
            IDException.class,
            () -> testCrs.makeRendezvous(patientId, hospitalId, sectionId, doctorId+1, date)
        );

        // First Patient
        Assertions.assertTrue(
            testCrs.makeRendezvous(patientId, hospitalId, sectionId, doctorId, date)
        );

        // Second Patient
        Assertions.assertTrue(
            testCrs.makeRendezvous(patientId, hospitalId, sectionId, doctorId, date)
        );

        // Third Patient ( > maxPatients )
        Assertions.assertFalse(
            testCrs.makeRendezvous(patientId, hospitalId, sectionId, doctorId, date)
        );
    }

    @Test
    void createHospital() {
        CRS testCrs = new CRS();
        testCrs.createHospital(2, "TestHospital");
        Hospital testHospital = testCrs.getHospitals().get(2);

        Assertions.assertNotNull(testHospital);
        Assertions.assertThrows(
            DuplicateInfoException.class,
            () -> testCrs.createHospital(2, "TestHospitalDuplicate")
        );
    }

    @Test void createPatient() {
        CRS testCrs = new CRS();
        Patient testPatient = new Patient("TestPatient", 1);

        Assertions.assertDoesNotThrow(
            () -> testCrs.createPatient("TestPatient", 1)
        );
        Assertions.assertThrows(
            DuplicateInfoException.class,
            () -> testCrs.createPatient("TestPatientCopy", 1)
        );
        Assertions.assertEquals(testPatient, testCrs.getPatients().get(1L));
    }

}