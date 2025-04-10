package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GPT Code Debugger");

       
        Label instructionLabel = new Label("Enter your code snippet below to debug:");
        // Assigns a CSS style class for external styling.
        instructionLabel.getStyleClass().add("instruction-label");

        TextArea codeInputArea = new TextArea();
        codeInputArea.setPromptText("Paste your code here...");
        codeInputArea.setWrapText(true);
        // Uses  CSS style class for the code input area.
        codeInputArea.getStyleClass().add("code-input");

        Button debugButton = new Button("Debug Code");
        debugButton.getStyleClass().add("debug-button");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        // Usea CSS style class for the result area.
        resultArea.getStyleClass().add("result-area");

        // Debug button action: for demonstration, simply copies the code.
        debugButton.setOnAction(event -> {
            String codeSnippet = codeInputArea.getText().trim();
            if (!codeSnippet.isEmpty()) {
                try {
                    String debugResult = DebuggerUtility.debugCodeSnippet(codeSnippet);
                    resultArea.setText(debugResult);
                } catch (Exception e) {
                    resultArea.setText("Error: " + e.getMessage());
                }
            } else {
                resultArea.setText("Please enter a code snippet to debug.");
            }
        });

        // Arranges components in a VBox with spacing and padding.
        VBox layout = new VBox(20, instructionLabel, codeInputArea, debugButton, resultArea);
        layout.setPadding(new Insets(25));
        VBox.setVgrow(codeInputArea, Priority.ALWAYS);
        VBox.setVgrow(resultArea, Priority.ALWAYS);

        Scene scene = new Scene(layout, 1200, 900);
        // Loads the external CSS stylesheet from the resources folder.
        URL cssUrl = getClass().getResource("/style.css");
        System.out.println("CSS URL: " + cssUrl);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("style.css not found!");
        }
        
        primaryStage.setScene(scene);
        primaryStage.show();

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Build and run commands:
// mvn clean install
// mvn exec:java -Dexec.mainClass="com.example.MainGUI"
