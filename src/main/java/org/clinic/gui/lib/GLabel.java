package org.clinic.gui.lib;

import org.clinic.lang.Language;
import org.clinic.util.Format;

import javax.swing.*;

public class GLabel extends JLabel {
    private final String translatable;
    private Object[] args;
    private boolean isFormat = false;

    public GLabel( String translatableText ) {
        super();
        this.translatable = translatableText;
        this.setText( getString(translatable) );
        createCallback();
    }

    public GLabel( String translatableFormat, Object ... args ) {
        super();
        this.isFormat = true;
        this.translatable = translatableFormat;
        this.args = args;
        this.setText( getString(translatable) );
        createCallback();
    }

    private String getString(String text) {
        if ( !isFormat ) {
            return Language.Get(text);
        } else {
            String toFormat = Language.Get(text);
            return Format.FormatMessage(toFormat, args);
        }
    }

    private void createCallback() {
        Language.AddLanguageCallback(()->{
                this.setText( getString(translatable) );
        });
    }
}
