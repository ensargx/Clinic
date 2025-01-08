package org.clinic.cli;

import org.clinic.hospital.Schedule;
import org.clinic.hospital.Rendezvous;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class ScheduleRenderer {
    private Schedule schedule;

    public ScheduleRenderer(Schedule schedule) {
        this.schedule = schedule;
    }

    public void render() {
        boolean shouldExit = false;
        char newLine = '\n';
        Scanner scanner = new Scanner(System.in);

        while (!shouldExit) {
            StringBuilder renderBuf = new StringBuilder();
            renderBuf.append("Doctor's Schedule:").append(newLine)
                    .append("0- Back").append(newLine)
                    .append("All Appointments:").append(newLine);

            LinkedList<Rendezvous> sessions = schedule.getSessions();
            if (sessions.isEmpty()) {
                renderBuf.append("No appointments available.").append(newLine);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (Rendezvous rendezvous : sessions) {
                    renderBuf.append(sdf.format(rendezvous.getDate()))
                            .append(" - Patient: ").append(rendezvous.getPatient().getName()).append(newLine);
                }
            }

            renderBuf.append("Enter: ");
            System.out.println(renderBuf);

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    shouldExit = true;
                } else {
                    System.out.println("Please Enter A Valid Choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
    }
}
