package org.clinic;

import org.clinic.exception.DuplicateInfoException;
import org.clinic.person.Doctor;

import java.io.Serializable;
import java.util.LinkedList;

public class Section implements Serializable {
    private final int id;
    private String name;
    private LinkedList<Doctor> doctors = new LinkedList<>();

    public Section(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void listDoctors() {
        doctors.forEach(System.out::println);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Doctor getDoctor(int diploma_id) {
        for ( Doctor doctor : doctors ) {
            if ( doctor.getDiplomaId() == diploma_id ) {
                return doctor;
            }
        }

        return null;
    }

    public void addDoctor(Doctor doctor) throws DuplicateInfoException {
        if ( doctors.contains( doctor ) ) {
            throw new DuplicateInfoException("The doctor already exists!");
        }

        doctors.add(doctor);
    }

    public LinkedList<Doctor> getDoctors() {
        return doctors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Section sec = (Section) obj;

        return this.getId() == sec.getId();
    }
}
