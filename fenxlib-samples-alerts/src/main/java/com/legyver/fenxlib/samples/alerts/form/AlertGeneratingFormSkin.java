package com.legyver.fenxlib.samples.alerts.form;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.controls.ControlsFactory;
import com.legyver.fenxlib.api.scene.controls.options.ButtonOptions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;

public class AlertGeneratingFormSkin extends SkinBase<AlertGeneratingForm> {
    private static final Logger logger = LogManager.getLogger(AlertGeneratingFormSkin.class);
    private final GridPane gridPane;

    protected AlertGeneratingFormSkin(AlertGeneratingForm control) {
        super(control);
        int rowHeight = 40;
        gridPane = new GridPane();
        gridPane.setHgap(10.0);
        gridPane.setPadding(new Insets(20));

        Label label = new Label("Enter an integer");
//        label.setPrefHeight(rowHeight);
        gridPane.add(label, 0, 0);

        TextField textField = new TextField();
//        textField.setPrefHeight(rowHeight);
        EventHandler<ActionEvent> submit = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                control.setValue(textField.getText());
            }
        };

        textField.setOnAction(submit);
        gridPane.add(textField, 1,0);

        try {
            Button button = ControlsFactory.make(Button.class, new ButtonOptions()
                    .text("Go"));
//            button.setPrefHeight(rowHeight);
            button.setOnAction(submit);
            gridPane.add(button, 2, 0);
        } catch (CoreException e) {
            logger.error(e);
        }

        getChildren().add(gridPane);
    }
}
