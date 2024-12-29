package org.clinic.gui.lib;

import org.clinic.lang.Language;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class GIntegerField extends JFormattedTextField {
    private String placeholderTr;

    public GIntegerField(int columns) {
        super(getNumberFormatter());
        this.setColumns(columns);
    }

    public GIntegerField() {
        super(getNumberFormatter());
    }

    public Integer getInteger() {
        try {
            return Integer.parseInt(this.getText());
        } catch ( NumberFormatException e ) {
            return 0;
        }
    }

    public boolean isEmpty() {
        return this.getText().isEmpty();
    }

    public void clear() {
        this.setText("0");
    }

    public boolean isInteger() {
        try {
            Integer.parseInt(this.getText());
            return true;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }

    private static NumberFormatter getNumberFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(Integer.MIN_VALUE);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false);

        return numberFormatter;
    }

    public void setPlaceholder( String placeholderTr ) {
        this.placeholderTr = placeholderTr;
    }

    public String getPlaceholderTr() {
        return placeholderTr;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent( pG );

        if ( placeholderTr == null || placeholderTr.isEmpty() || !getText().isEmpty() ) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        String placeholder = Language.Get( placeholderTr );
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top);
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
