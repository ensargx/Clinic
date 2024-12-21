package org.clinic.gui.menu;

import org.clinic.lang.Language;

import javax.swing.*;

public class MenuItem extends JMenuItem {
    private final String title;

    public MenuItem(String translatableKey) {
        super(translatableKey);
        this.title = translatableKey;
        this.setText(getTitle());
        Language.AddLanguageCallback(()->{
            this.setText(getTitle());
        });
    }

    public String getTitle() {
        return Language.Get(title);
    }
}
