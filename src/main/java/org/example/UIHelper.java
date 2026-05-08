package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

// Shared helper methods used by ALL panels
// Instead of repeating styling code in every panel
public class UIHelper {

    // standard colors used everywhere
    public static final String NAV_DEFAULT =
            "-fx-pref-width: 140px; -fx-pref-height: 38px; " +
                    "-fx-font-size: 12px; -fx-font-weight: bold; " +
                    "-fx-background-color: #e8e0d5; -fx-text-fill: #5a4a3a; " +
                    "-fx-background-radius: 6px; -fx-cursor: hand;";

    // ─────────────────────────────
    // Creates a standard text input field
    // ─────────────────────────────
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

    // ─────────────────────────────
    // Creates a number-only input (decimals allowed)
    // ─────────────────────────────
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
        // only allow digits and one dot
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*\\.?[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }

    // ─────────────────────────────
    // Creates a whole-number-only input
    // ─────────────────────────────
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
        // only allow whole numbers
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }

    // ─────────────────────────────
    // Creates a multi-line message text area
    // ─────────────────────────────
    public static TextArea makeMessageBox(String placeholder) {
        TextArea area = new TextArea();
        area.setPromptText(placeholder);
        area.setMaxWidth(380);
        area.setPrefWidth(380);
        area.setPrefHeight(100);
        area.setWrapText(true); // wraps vertically not horizontally
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

    // ─────────────────────────────
    // Creates a read-only output area
    // ─────────────────────────────
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

    // ─────────────────────────────
    // Creates a bigger records display area
    // ─────────────────────────────
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

    // ─────────────────────────────
    // Creates a styled ComboBox
    // ─────────────────────────────
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

    // ─────────────────────────────
    // Creates a colored action button
    // ─────────────────────────────
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

    // ─────────────────────────────
    // Creates a section title label
    // ─────────────────────────────
    public static Label makeTitle(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f; -fx-padding: 0 0 10 0;"
        );
        return lbl;
    }

    // ─────────────────────────────
    // Creates a small field label
    // ─────────────────────────────
    public static Label makeLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #4a3728;"
        );
        return lbl;
    }

    // ─────────────────────────────
    // Centers any node inside an HBox
    // ─────────────────────────────
    public static HBox centered(javafx.scene.Node node) {
        HBox box = new HBox(node);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }
}