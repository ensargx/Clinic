package org.clinic.gui.lib;

@FunctionalInterface
public interface GCallback<T> {
    void onSelect(T value);
}