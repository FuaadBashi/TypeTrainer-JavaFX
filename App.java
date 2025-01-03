package typetrainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App extends Application {

    private List<String> practiceTexts;
    private final String exampleText = "/Users/fuaad/Java Projects/TypeTrainer/demo/src/main/java/typetrainer/typeracer_texts.txt";
    private int currentTextIndex = 0;
    private WebView targetTextView;
    private TextField typingField;
    private ProgressBar progressBar;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Load practice texts from the file
        try {
            practiceTexts = Files.readAllLines(Paths.get(exampleText));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (practiceTexts.isEmpty()) {
            System.out.println("Practice text file is empty!");
            return;
        }

        // Set up UI components
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        targetTextView = new WebView();
        targetTextView.setPrefHeight(100);

        typingField = new TextField();
        typingField.setPromptText("Type here...");
        typingField.setStyle("-fx-font-size: 16px; -fx-font-family: 'Courier New';");

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);

        root.getChildren().addAll(
            new Label("Target Text:"),
            targetTextView,
            new Label("Your Input:"),
            typingField,
            new Label("Progress:"),
            progressBar
        );

        // Add listener for typing input
        typingField.textProperty().addListener((observable, oldValue, newValue) -> {
            String currentTargetText = practiceTexts.get(currentTextIndex);

            // Update text highlighting
            updateTargetText(currentTargetText, newValue);

            // Update progress
            int correctChars = 0;
            for (int i = 0; i < newValue.length() && i < currentTargetText.length(); i++) {
                if (newValue.charAt(i) == currentTargetText.charAt(i)) {
                    correctChars++;
                }
            }

            double progress = (double) correctChars / currentTargetText.length();
            progressBar.setProgress(progress);

            // Check if the current line is completed
            if (progress == 1.0) {
                currentTextIndex++;
                if (currentTextIndex < practiceTexts.size()) {
                    typingField.clear();
                    progressBar.setProgress(0);
                    updateTargetText(practiceTexts.get(currentTextIndex), "");
                } else {
                    typingField.setDisable(true);
                    System.out.println("All lines completed!");
                }
            }
        });

        // Initialize with the first target text
        updateTargetText(practiceTexts.get(currentTextIndex), "");

        // Set up stage and scene
        Scene scene = new Scene(root, 500, 300);
        stage.setTitle("TypeTrainer");
        stage.setScene(scene);
        stage.show();
    }

    private void updateTargetText(String targetText, String userInput) {
        StringBuilder styledText = new StringBuilder();
        styledText.append("<html><body style='font-size:16px; font-family:Courier New;'>");

        for (int i = 0; i < targetText.length(); i++) {
            if (i < userInput.length()) {
                if (userInput.charAt(i) == targetText.charAt(i)) {
                    styledText.append("<span style='color:green;'>").append(targetText.charAt(i)).append("</span>");
                } else {
                    styledText.append("<span style='color:red;'>").append(targetText.charAt(i)).append("</span>");
                }
            } else {
                styledText.append(targetText.charAt(i));
            }
        }

        styledText.append("</body></html>");
        targetTextView.getEngine().loadContent(styledText.toString());
    }
}
