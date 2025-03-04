package org.clinic.person;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private final long national_id;

    public Person(String name, long national_id) {
        this.name = name;
        this.national_id = national_id;
    }

    public String getName() {
        return name;
    }

    public long getNationalId() {
        return national_id;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + national_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Person sec = (Person) obj;

        return this.getNationalId() == sec.getNationalId();
    }
}
