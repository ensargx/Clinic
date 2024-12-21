package org.clinic.gui;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class TextField extends JTextField {
    /**
     * Constructs a new <code>JTextField</code> that uses the given text
     * storage model and the given number of columns.
     * This is the constructor through which the other constructors feed.
     * If the document is <code>null</code>, a default model is created.
     *
     * @param doc     the text storage to use; if this is <code>null</code>,
     *                a default will be provided by calling the
     *                <code>createDefaultModel</code> method
     * @param text    the initial string to display, or <code>null</code>
     * @param columns the number of columns to use to calculate
     *                the preferred width &gt;= 0; if <code>columns</code>
     *                is set to zero, the preferred width will be whatever
     *                naturally results from the component implementation
     * @throws IllegalArgumentException if <code>columns</code> &lt; 0
     */
    public TextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text and columns.  A default model is created.
     *
     * @param text    the text to be displayed, or <code>null</code>
     * @param columns the number of columns to use to calculate
     *                the preferred width; if columns is set to zero, the
     *                preferred width will be whatever naturally results from
     *                the component implementation
     */
    public TextField(String text, int columns) {
        super(text, columns);
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * @param columns the number of columns to use to calculate
     *                the preferred width; if columns is set to zero, the
     *                preferred width will be whatever naturally results from
     *                the component implementation
     */
    public TextField(int columns) {
        super(columns);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text. A default model is created and the number of
     * columns is 0.
     *
     * @param text the text to be displayed, or <code>null</code>
     */
    public TextField(String text) {
        super(text);
    }

    /**
     * Constructs a new <code>TextField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     */
    public TextField() {
    }

    public void setPlaceholder( String placeholder ) {

    }

    public void clear() {

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
