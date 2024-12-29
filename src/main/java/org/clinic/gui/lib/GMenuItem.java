package org.clinic.gui.lib;

import org.clinic.lang.Language;

import javax.swing.*;

public class GMenuItem extends JMenuItem {
    private final String text;

    public GMenuItem(String translatableText) {
        super();
        this.text = translatableText;
        this.setText(getTitle());
        Language.AddLanguageCallback(()->{
            this.setText(getTitle());
        });
    }

    public String getTitle() {
        return Language.Get(text);
    }

}
