package com.brightlightshop.client4.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Component {

    public static void numericTextField(TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        TextField numField = new TextField();
        numField.setTextFormatter(new TextFormatter<Object>(change -> {
            // Deletion should always be possible.
            if (change.isDeleted()) {
                return change;
            }

            // How would the text look like after the change?
            String txt = change.getControlNewText();

            // There shouldn't be leading zeros.
            if (txt.matches("0\\d+")) {
                return null;
            }

            // Try parsing and check if the result is in [0, 64].
            try {
                int n = Integer.parseInt(txt);
                return 1 <= n ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
    }

    public static void setDisableChoiceBox(ChoiceBox<String> choiceBox) {
        choiceBox.setDisable(true);
        choiceBox.setValue(null);
    }

    public static void setEnableChoiceBox(ChoiceBox<String> choiceBox) {
        choiceBox.setDisable(false);
        choiceBox.setValue(null);
    }



}
