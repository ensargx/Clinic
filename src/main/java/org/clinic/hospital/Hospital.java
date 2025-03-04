package org.clinic.hospital;

import org.clinic.exception.DuplicateInfoException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Hospital implements Serializable {
    private final int id;
    private String name;
    private LinkedList<Section> sections = new LinkedList<>();

    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Section getSection(int id) {
        for( Section section : sections ) {
            if ( section.getId() == id ) {
                return section;
            }
        }

        return null;
    }

    private Section getSection(String name) {
        for( Section section : sections ) {
            if ( Objects.equals( section.getName(), name ) ) {
                return section;
            }
        }

        return null;
    }

    public void addSection(Section section) throws DuplicateInfoException {
        if ( sections.contains( section ) ) {
            throw new DuplicateInfoException( "error.hospital.section_already_exists_format", section.getName());
        }

        sections.add( section );
    }

    public int getId() {
        return id;
    }

    public LinkedList<Section> getSections() {
        return sections;
    }

    public String getName() {
        return name;
    }
}
