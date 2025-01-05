package org.clinic.util;

public class Format {
    public static String FormatMessage(String template, Object... args) {
        for (Object arg : args) {
            template = template.replaceFirst("\\{}", arg.toString());
        }
        return template;
    }
}
