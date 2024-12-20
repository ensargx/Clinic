package org.clinic;

import org.clinic.gui.GUI;

public class Main {
    private static boolean isGui;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        GUI gui = new GUI();
        gui.render();

        System.out.println("Hello world!");
    }
}