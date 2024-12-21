package org.clinic.gui;

import org.clinic.lang.Language;

import javax.swing.*;

public class TabbedPane extends JTabbedPane {
    public TabbedPane() {
        Language.AddLanguageCallback(()->{
            for ( int i = 0; i < this.getTabCount(); ++i ) {
                TabPanel panel = (TabPanel) this.getComponentAt(i);
                if ( panel != null ) {
                    this.setTitleAt(i, panel.getPanelTitle());
                }
            }
        });
    }
}
