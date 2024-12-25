package org.clinic.person;

import org.clinic.Schedule;

public class Doctor extends Person {
    private final int diploma_id;
    private Schedule schedule;

    public Doctor(String name, long national_id, int diploma_id, int maxPatientPerDay) {
        super(name, national_id);
        this.diploma_id = diploma_id;

        this.schedule = new Schedule( this, maxPatientPerDay );
    }

    public int getDiplomaId() {
        return diploma_id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Doctor sec = (Doctor) obj;

        return this.getDiplomaId() == sec.getDiplomaId();
    }

    @Override
    public String toString() {
        return "[Doctor]\n" + super.toString();
    }

}
