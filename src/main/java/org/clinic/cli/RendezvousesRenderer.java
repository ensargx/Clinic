package org.clinic.cli;

import org.clinic.CRS;
import org.clinic.hospital.Rendezvous;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class RendezvousesRenderer {
    private CRS crs;

    public RendezvousesRenderer(CRS crs) {
        this.crs = crs;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';
        Scanner scanner = new Scanner(System.in);

        while (!shouldExit) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("0- Back").append(newLine)
                    .append("1- New Rendezvous").append(newLine)
                    .append("All Rendezvouses:").append(newLine);

            LinkedList<Rendezvous> rendezvouses = crs.getRendezvouses();
            if (rendezvouses.isEmpty()) {
                renderBuf.append("No rendezvouses available.").append(newLine);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (Rendezvous rendezvous : rendezvouses) {
                    renderBuf.append("Patient: ").append(rendezvous.getPatient().getName())
                            .append(", Date: ").append(sdf.format(rendezvous.getDate()))
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
                        addNewRendezvous();
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

    private void addNewRendezvous() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Patient National ID: ");
        String patientIdInput = scanner.nextLine();

        System.out.println("Enter Hospital ID: ");
        String hospitalIdInput = scanner.nextLine();

        System.out.println("Enter Section ID: ");
        String sectionIdInput = scanner.nextLine();

        System.out.println("Enter Doctor's Diploma ID: ");
        String diplomaIdInput = scanner.nextLine();

        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();

        try {
            long patientId = Long.parseLong(patientIdInput);
            int hospitalId = Integer.parseInt(hospitalIdInput);
            int sectionId = Integer.parseInt(sectionIdInput);
            int diplomaId = Integer.parseInt(diplomaIdInput);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = sdf.parse(dateInput);

            boolean success = crs.makeRendezvous(patientId, hospitalId, sectionId, diplomaId, appointmentDate);

            if (success) {
                System.out.println("Rendezvous successfully created!");
            } else {
                System.out.println("Failed to create rendezvous. Please try again.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields!");
        } catch (Exception e) {
            System.out.println("Error adding rendezvous: " + e.getMessage());
        }
    }
}
