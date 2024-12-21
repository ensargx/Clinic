package org.clinic.gui.menu;

import org.clinic.lang.Language;

import javax.swing.*;

public class Menu extends JMenu {
    private final String title;

    public Menu(String translatableKey) {
        super(translatableKey);
        title = translatableKey;
        this.setText(getTitle());
        Language.AddLanguageCallback(()->{
            this.setText(getTitle());
        });
    }

    public String getTitle() {
        return Language.Get(title);
    }
}
