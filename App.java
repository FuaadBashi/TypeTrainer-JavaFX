package typetrainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

    private static String exampleText = "/Users/fuaad/Java Projects/TypeTrainer/demo/src/main/java/typetrainer/typeracer_texts.txt";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Target text
        String targetText = "The quick brown fox jumps over the lazy dog.";

        // Main Layout
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        // Text Display Area (WebView for styling)
        WebView targetTextView = new WebView();
        targetTextView.setPrefHeight(100);
        updateTargetText(targetTextView, targetText, "");

        // Typing Input Field
        TextField typingField = new TextField();
        typingField.setPromptText("Type here...");
        typingField.setStyle("-fx-font-size: 16px; -fx-font-family: 'Courier New';");

        // Progress Bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);

        // Layout Components
        root.getChildren().addAll(
            new Label("Target Text:"), 
            targetTextView, 
            new Label("Your Input:"), 
            typingField, 
            new Label("Progress:"), 
            progressBar
        );

        // Typing Field Listener
        typingField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update highlighted text
            updateTargetText(targetTextView, targetText, newValue);

            // Update progress bar
            int correctChars = 0;
            for (int i = 0; i < newValue.length() && i < targetText.length(); i++) {
                if (newValue.charAt(i) == targetText.charAt(i)) {
                    correctChars++;
                }
            }
            progressBar.setProgress((double) correctChars / targetText.length());
            if (progressBar.getProgress() == 100){

            }
        });

        // Scene and Stage
        Scene scene = new Scene(root, 500, 300);
        stage.setTitle("TypeRacer UI");
        stage.setScene(scene);
        stage.show();
    }

    // Method to update the target text with highlighting
    private void updateTargetText(WebView targetTextView, String targetText, String userInput) {
        StringBuilder styledText = new StringBuilder();
        styledText.append("<html><body style='font-size:16px; font-family:Courier New;'>");

        for (int i = 0; i < targetText.length(); i++) {
            if (i < userInput.length()) {
                if (userInput.charAt(i) == targetText.charAt(i)) {
                    // Correct character: Green
                    styledText.append("<span style='color:green;'>").append(targetText.charAt(i)).append("</span>");
                } else {
                    // Incorrect character: Red
                    styledText.append("<span style='color:red;'>").append(targetText.charAt(i)).append("</span>");
                }
            } else {
                // Unmatched characters: Default color
                styledText.append(targetText.charAt(i));
            }
        }

        styledText.append("</body></html>");

        // Update the WebView content
        targetTextView.getEngine().loadContent(styledText.toString());
    }

}


    
