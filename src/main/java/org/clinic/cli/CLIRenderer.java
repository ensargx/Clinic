package org.clinic.cli;

import org.clinic.CRS;

import java.util.Scanner;

public class CLIRenderer {
    private CRS crs;

    public CLIRenderer(CRS crs) {
        this.crs = crs;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';
        String renderBuf =
                "0- Exit" + newLine + newLine
                + "1- Hospitals" + newLine
                + "2- Patients" + newLine
                + "3- Rendezvouses" + newLine + newLine
                + "8- Save To Disc" + newLine
                + "9- Load From Disc" + newLine + newLine
                + "Enter: ";
        Scanner scanner = new Scanner(System.in);
        while ( !shouldExit ) {
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
                        new HospitalsRenderer(crs).render();
                        break;
                    case 2:
                        new PatientsRenderer(crs).render();
                        break;
                    case 3:
                        new RendezvousesRenderer(crs).render();
                        break;
                    case 8:
                        saveToDisc();
                        break;
                    case 9:
                        loadFromDisc();
                        break;
                    case 0:
                        shouldExit = true;
                        break;
                    default:
                        System.out.println("Please Enter A Valid Choice!");
                }
            }
        }
    }

    private void saveToDisc() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path to save the data:");
        String filePath = scanner.nextLine();
        try {
            crs.saveTablesToDisk(filePath);
            System.out.println("Data successfully saved to " + filePath);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadFromDisc() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path to load the data:");
        String filePath = scanner.nextLine();
        try {
            crs.loadTablesFromDisc(filePath);
            System.out.println("Data successfully loaded from " + filePath);
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

}
