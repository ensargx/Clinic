package org.clinic;

import org.clinic.gui.GUI;
import org.clinic.gui.IGUIListener;

public class Main {
    private static boolean isGui;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        CRS crs = new CRS();

        GUI gui = new GUI(new IGUIListener() {
            @Override
            public void onHospitalAdded(String name, Integer id) {
                System.out.println("test name: "+name+" id: "+id);
                try {
                    crs.createHospital( id ,name );
                } catch ( DuplicateInfoException err ) {
                    GUI.ErrorMessage( err.getMessage() );
                }
            }
        });

        gui.render( crs.getHospitals() );

        System.out.println("Hello world!");
    }
}