package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the primary stage
        primaryStage.setTitle("GPT Code Debugger");

        // Create UI elements
        Label instructionLabel = new Label("Enter your code snippet below to debug:");
        TextArea codeInputArea = new TextArea();
        Button debugButton = new Button("Debug Code");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Handle Debug Button Click
        debugButton.setOnAction(event -> {
            String codeSnippet = codeInputArea.getText().trim();
            if (!codeSnippet.isEmpty()) {
                try {
                    String debugResult = Main.debugCodeSnippet(codeSnippet);
                    resultArea.setText(debugResult);
                } catch (Exception e) {
                    resultArea.setText("Error: " + e.getMessage());
                }
            } else {
                resultArea.setText("Please enter a code snippet to debug.");
            }
        });

        // Set up the layout
        VBox layout = new VBox(10, instructionLabel, codeInputArea, debugButton, resultArea);
        Scene scene = new Scene(layout, 600, 400);

        // Display the GUI
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}