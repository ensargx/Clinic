package org.clinic.gui;

import org.clinic.lang.Language;

import javax.swing.*;
import java.awt.*;

public class GButton extends JButton {
    private String trText;
    public GButton( String translatableText ) {
        super();
        this.trText = translatableText;
        this.setText( getString( trText ) );
        createCallback();
    }

    private String getString(String text) {
        return Language.Get(text);
    }

    private void createCallback() {
        Language.AddLanguageCallback(()->{
            this.setText( getString( trText ) );
        });
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
