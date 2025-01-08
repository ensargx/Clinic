package org.clinic.cli;

import org.clinic.CRS;
import org.clinic.exception.DuplicateInfoException;
import org.clinic.hospital.Hospital;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HospitalsRenderer {
    private CRS crs;

    public HospitalsRenderer(CRS crs) {
        this.crs = crs;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';

        Scanner scanner = new Scanner(System.in);

        while ( !shouldExit ) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("0- Back").append(newLine).append(newLine)
                    .append("1- New Hospital").append(newLine)
                    .append("2- Enter Hospital ID").append(newLine);

            HashMap<Integer, Hospital> hospitals = crs.getHospitals();

            if (hospitals.isEmpty()) {
                renderBuf.append("No hospitals available.").append(newLine);
            } else {
                renderBuf.append("All Hospitals:").append(newLine);
                for (Map.Entry<Integer, Hospital> entry : hospitals.entrySet()) {
                    renderBuf.append(entry.getKey()).append(" - ").append(entry.getValue().getName()).append(newLine);
                }
            }

            renderBuf.append("Enter: ");
            System.out.println(renderBuf);

            String input = scanner.nextLine();
            Integer choice = null;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException err) {
                System.out.println("Please Enter Correctly!");
            }

            if ( choice != null ) {
                switch ( choice ) {
                    case 1:
                        newHospital();
                        break;
                    case 2:
                        enterId();
                    case 0:
                        shouldExit = true;
                        break;
                    default:
                        System.out.println("Please Enter A Valid Choice!");
                }
            }
        }
    }

    private void newHospital() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID: ");
        String idInput = scanner.nextLine();

        System.out.println("Enter Name: ");
        String nameInput = scanner.nextLine();

        try {
            Integer id = Integer.valueOf(idInput);
            crs.createHospital(id, nameInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
        } catch (DuplicateInfoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enterId() {
        System.out.println("Enter ID: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            Integer id = Integer.valueOf(input);
            Hospital hospital = crs.getHospitals().get(id);
            new SectionsRenderer(crs, hospital).render();
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
        }
    }

}
