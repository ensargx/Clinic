package org.clinic.cli;

import org.clinic.CRS;
import org.clinic.exception.DuplicateInfoException;
import org.clinic.hospital.Hospital;
import org.clinic.hospital.Section;
import org.clinic.person.Doctor;

import java.util.Scanner;

public class DoctorsRenderer {
    private CRS crs;
    private Hospital hospital;
    private Section section;

    public DoctorsRenderer(CRS crs, Hospital hospital, Section section) {
        this.crs = crs;
        this.hospital = hospital;
        this.section = section;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';
        Scanner scanner = new Scanner(System.in);

        while (!shouldExit) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("Hospital: ").append(hospital.getName()).append(newLine)
                    .append("Section: ").append(section.getName()).append(newLine).append(newLine)
                    .append("0- Back").append(newLine).append(newLine)
                    .append("1- Add Doctor").append(newLine)
                    .append("2- View Doctor Schedule").append(newLine)
                    .append("All Doctors:").append(newLine);

            if (section.getDoctors().isEmpty()) {
                renderBuf.append("No doctors in this section.").append(newLine);
            } else {
                for (Doctor doctor : section.getDoctors()) {
                    renderBuf.append(doctor.getDiplomaId()).append(" - ").append(doctor.getName()).append(newLine);
                }
            }

            renderBuf.append("Enter: ");
            System.out.println(renderBuf);

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewDoctorSchedule();
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

    private void addDoctor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Doctor Name: ");
        String nameInput = scanner.nextLine();

        System.out.println("Enter National ID: ");
        String nationalIdInput = scanner.nextLine();

        System.out.println("Enter Diploma ID: ");
        String diplomaIdInput = scanner.nextLine();

        System.out.println("Enter Max Patients Per Day: ");
        String maxPatientsInput = scanner.nextLine();

        try {
            String name = nameInput;
            long nationalId = Long.parseLong(nationalIdInput);
            int diplomaId = Integer.parseInt(diplomaIdInput);
            int maxPatientsPerDay = Integer.parseInt(maxPatientsInput);

            Doctor doctor = new Doctor(name, nationalId, diplomaId, maxPatientsPerDay);
            section.addDoctor(doctor);
            System.out.println("Doctor successfully added!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields!");
        } catch (DuplicateInfoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewDoctorSchedule() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Doctor Diploma ID: ");
        String input = scanner.nextLine();

        try {
            int diplomaId = Integer.parseInt(input);
            Doctor doctor = section.getDoctor(diplomaId);

            if (doctor != null) {
                new ScheduleRenderer(doctor.getSchedule()).render();
            } else {
                System.out.println("Doctor not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Diploma ID!");
        }
    }
}
