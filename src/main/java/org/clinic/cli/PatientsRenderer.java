package org.clinic.cli;

import org.clinic.CRS;
import org.clinic.exception.DuplicateInfoException;
import org.clinic.person.Patient;

import java.util.HashMap;
import java.util.Scanner;

public class PatientsRenderer {
    private CRS crs;

    public PatientsRenderer(CRS crs) {
        this.crs = crs;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';
        Scanner scanner = new Scanner(System.in);

        while (!shouldExit) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("0- Back").append(newLine)
                    .append("1- New Patient").append(newLine)
                    .append("All Patients:").append(newLine);

            HashMap<Long, Patient> patients = crs.getPatients();
            if (patients.isEmpty()) {
                renderBuf.append("No patients registered yet.").append(newLine);
            } else {
                for (Patient patient : patients.values()) {
                    renderBuf.append("Patient Name: ").append(patient.getName())
                            .append(", National ID: ").append(patient.getNationalId())
                            .append(newLine);
                }
            }

            renderBuf.append("Enter your choice: ");
            System.out.println(renderBuf);

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addNewPatient();
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

    private void addNewPatient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Patient Name: ");
        String nameInput = scanner.nextLine();

        System.out.println("Enter National ID: ");
        String nationalIdInput = scanner.nextLine();

        try {
            String name = nameInput;
            long nationalId = Long.parseLong(nationalIdInput);

            Patient patient = crs.createPatient(name, nationalId);

            if (patient != null) {
                System.out.println("Patient successfully added!");
            } else {
                System.out.println("Error: Patient could not be created.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for National ID!");
        } catch (DuplicateInfoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
