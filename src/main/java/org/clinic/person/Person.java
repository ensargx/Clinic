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
        return "Name: " + name + "\nID: " + national_id;
    }
}
