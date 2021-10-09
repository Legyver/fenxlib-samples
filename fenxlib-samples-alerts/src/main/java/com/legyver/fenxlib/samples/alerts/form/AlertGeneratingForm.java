package com.legyver.fenxlib.samples.alerts.form;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class AlertGeneratingForm extends Control {

    private final StringProperty value = new SimpleStringProperty();

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AlertGeneratingFormSkin(this);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
