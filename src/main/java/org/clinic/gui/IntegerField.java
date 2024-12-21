package org.clinic.gui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class IntegerField extends JFormattedTextField {
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
    public IntegerField(int columns) {
        super(getNumberFormatter());
        this.setColumns(columns);
    }

    /**
     * Constructs a new <code>TextField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     */
    public IntegerField() {
        super(getNumberFormatter());
    }

    public Integer getInteger() {
        return Integer.getInteger(this.getText());
    }

    public boolean isEmpty() {
        return this.getText().isEmpty();
    }

    public void clear() {
        this.setText("");
    }

    private static NumberFormatter getNumberFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(Integer.MIN_VALUE);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false); //this is the key

        return numberFormatter;
    }

    public void setPlaceholder( String placeholder ) {

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
