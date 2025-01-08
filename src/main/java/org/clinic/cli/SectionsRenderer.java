package org.clinic.cli;

import org.clinic.CRS;
import org.clinic.exception.DuplicateInfoException;
import org.clinic.hospital.Hospital;
import org.clinic.hospital.Section;

import java.util.LinkedList;
import java.util.Scanner;

public class SectionsRenderer {
    private CRS crs;
    private Hospital hospital;

    public SectionsRenderer(CRS crs, Hospital hospital) {
        this.crs = crs;
        this.hospital = hospital;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';

        Scanner scanner = new Scanner(System.in);

        while ( !shouldExit ) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("Hospital: ").append(hospital.getName()).append(newLine).append(newLine)
                    .append("0- Back").append(newLine).append(newLine)
                    .append("1- New Section").append(newLine)
                    .append("2- Enter Section ID").append(newLine)
                    .append("All Sections:").append(newLine);

            LinkedList<Section> sections = hospital.getSections();
            if (sections.isEmpty()) {
                renderBuf.append("No sections in this hospital.").append(newLine);
            } else {
                for (Section section : sections) {
                    renderBuf.append(section.getId()).append(" - ").append(section.getName()).append(newLine);
                }
            }

            renderBuf.append("Enter: ");

            System.out.println(renderBuf);
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        newSection();
                        break;
                    case 2:
                        enterId();
                        break;
                    case 0:
                        shouldExit = true;
                        break;
                    default:
                        System.out.println("Please Enter A Valid Choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
    }

    private void newSection() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Section ID: ");
        String idInput = scanner.nextLine();

        System.out.println("Enter Section Name: ");
        String nameInput = scanner.nextLine();

        try {
            int id = Integer.parseInt(idInput);
            Section section = new Section(id, nameInput);
            hospital.addSection(section);
            System.out.println("Section successfully added!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid Section ID!");
        } catch (DuplicateInfoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enterId() {
        System.out.println("Enter Section ID: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            int id = Integer.parseInt(input);
            Section section = hospital.getSection(id);
            if (section != null) {
                new DoctorsRenderer(crs, hospital, section).render();
            } else {
                System.out.println("Section not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Section ID!");
        }
    }

}
