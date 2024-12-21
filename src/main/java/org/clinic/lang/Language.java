package org.clinic.lang;

import java.util.*;
import java.util.concurrent.Callable;

public class Language {
    private static HashMap<String, ArrayList<String>> languages = new HashMap<>(Map.ofEntries(
            Map.entry("Türkçe", new ArrayList<>(List.of("tr", "TR"))),
            Map.entry("English", new ArrayList<>(List.of("en", "US")))
    ));
    private static ResourceBundle activeResource = getDefaultBundle();
    private static ArrayList<Runnable> callbacks = new ArrayList<>();

    public static String Get( String translatableKey ) {
        return activeResource.getString( translatableKey );
    }

    public static boolean AddLanguageCallback( Runnable runnable ) {
        if ( callbacks.contains( runnable ) ) {
            return false;
        }

        callbacks.add( runnable );
        return true;
    }

    private static void LanguageCallback() {
        for ( Runnable runnable : callbacks ) {
            runnable.run();
        }
    }

    public static Set<String> getLanguages() {
        return languages.keySet();
    }

    public static void SetLanguage(String lang) {
        if ( !languages.containsKey( lang ) ) {
            return;
        }

        ArrayList<String> val = languages.get( lang );

        String language = val.get(0);
        String country = val.get(1);

        activeResource = getBundle( language, country );

        LanguageCallback();
    }

    private static ResourceBundle getBundle(String lang, String country) {
        Locale local = Locale.of(lang, country);
        return PropertyResourceBundle.getBundle("Localization", local);
    }

    private static ResourceBundle getDefaultBundle() {
        Locale def = Locale.getDefault();

        if ( def.getLanguage().equalsIgnoreCase("tr") ) {
            return getBundle("tr", "TR");
        } else {
            return getBundle("en", "US");
        }
    }
}