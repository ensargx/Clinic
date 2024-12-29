package org.clinic.gui.lib;

import org.clinic.lang.Language;

import javax.swing.*;

public class GLabel extends JLabel {
    private final String translatable;
    public GLabel( String translatableText ) {
        super();
        this.translatable = translatableText;
        this.setText( getString(translatable) );
        createCallback();
    }

    private String getString(String text) {
        return Language.Get(text);
    }

    private void createCallback() {
        Language.AddLanguageCallback(()->{
                this.setText( getString(translatable) );
        });
    }
}
