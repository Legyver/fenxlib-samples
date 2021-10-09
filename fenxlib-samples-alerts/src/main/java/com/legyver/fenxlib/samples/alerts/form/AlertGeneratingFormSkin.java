package com.legyver.fenxlib.samples.alerts.form;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AlertGeneratingFormSkin extends SkinBase<AlertGeneratingForm> {

    private final HBox hBox;

    protected AlertGeneratingFormSkin(AlertGeneratingForm control) {
        super(control);
        hBox = new HBox();
        hBox.getChildren().add(new Label("Enter an integer"));

        TextField textField = new TextField();
        hBox.getChildren().add(textField);

        JFXButton button = new JFXButton("Go");
        button.setOnAction(event -> control.setValue(textField.getText()));
        hBox.getChildren().add(button);

        getChildren().add(hBox);
    }
}
