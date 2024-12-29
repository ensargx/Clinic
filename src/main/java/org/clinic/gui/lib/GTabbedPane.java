package org.clinic.gui.lib;

import org.clinic.lang.Language;

import javax.swing.*;

public class GTabbedPane extends JTabbedPane {
    public GTabbedPane() {
        Language.AddLanguageCallback(()->{
            for ( int i = 0; i < this.getTabCount(); ++i ) {
                GTabPanel panel = (GTabPanel) this.getComponentAt(i);
                if ( panel != null ) {
                    this.setTitleAt(i, panel.getPanelTitle());
                }
            }
        });
    }
}
