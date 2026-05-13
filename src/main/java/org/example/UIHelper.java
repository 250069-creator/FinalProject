package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class UIHelper {

    public static final String NAV_DEFAULT =
            "-fx-pref-width: 140px; -fx-pref-height: 38px; " +
                    "-fx-font-size: 12px; -fx-font-weight: bold; " +
                    "-fx-background-color: #e8e0d5; -fx-text-fill: #5a4a3a; " +
                    "-fx-background-radius: 6px; -fx-cursor: hand;";


    public static TextField makeInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );
        return field;
    }

    public static TextField makeDecimalInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*\\.?[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }

    public static TextField makeIntegerInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );

        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }


    public static TextArea makeMessageBox(String placeholder) {
        TextArea area = new TextArea();
        area.setPromptText(placeholder);
        area.setMaxWidth(380);
        area.setPrefWidth(380);
        area.setPrefHeight(100);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #e8f4fd; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #a3c4e8; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #1a1a2e;"
        );
        return area;
    }

    public static TextArea makeOutputArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setMaxWidth(380);
        area.setPrefWidth(380);
        area.setPrefHeight(120);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #f0fff4; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #a5d6a7; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #1b3a1f;"
        );
        return area;
    }

    public static TextArea makeRecordsArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setMaxWidth(560);
        area.setPrefWidth(560);
        area.setPrefHeight(180);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c; " +
                        "-fx-font-family: monospace;"
        );
        return area;
    }


    public static ComboBox<String> makeComboBox(String prompt, String... items) {
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(items);
        box.setPromptText(prompt);
        box.setMaxWidth(380);
        box.setPrefWidth(380);
        box.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #fce4ec; " +
                        "-fx-border-color: #e5a0b5; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-background-radius: 6px;"
        );
        return box;
    }


    public static Button makeButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 9px 28px; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setOpacity(0.85));
        btn.setOnMouseExited(e -> btn.setOpacity(1.0));
        return btn;
    }


    public static Label makeTitle(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f; -fx-padding: 0 0 10 0;"
        );
        return lbl;
    }

    public static Label makeLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #4a3728;"
        );
        return lbl;
    }


    public static HBox centered(javafx.scene.Node node) {
        HBox box = new HBox(node);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }
}