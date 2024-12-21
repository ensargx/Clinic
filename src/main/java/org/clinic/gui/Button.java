package org.clinic.gui;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    /**
     * Creates a button with no set text or icon.
     */
    public Button() {
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon the Icon image to display on the button
     */
    public Button(Icon icon) {
        super(icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text the text of the button
     */
    public Button(String text) {
        super(text);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     * @since 1.3
     */
    public Button(Action a) {
        super(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text the text of the button
     * @param icon the Icon image to display on the button
     */
    public Button(String text, Icon icon) {
        super(text, icon);
    }

    public void setFlexibleSize(int minW, int minH, int maxW, int maxH) {
        Dimension minSize = new Dimension(minW, minH);
        Dimension maxSize = new Dimension(maxW, maxH);
        Dimension preferredSize = new Dimension((minW + maxW) / 2, (minH + maxH) / 2);

        this.setMinimumSize(minSize);
        this.setMaximumSize(maxSize);
        this.setPreferredSize(preferredSize);
    }
}
