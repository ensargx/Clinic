package org.clinic.gui;

import javax.swing.JFrame;

public class GUI {

    public void render() {
        JFrame frame = new JFrame();
        frame.setTitle( "Test Title!" );
        frame.setSize( 350, 400 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
