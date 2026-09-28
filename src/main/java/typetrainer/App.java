package typetrainer;

import java.time.Instant;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/** Typing practice: type each sentence exactly, with live highlighting, speed and accuracy. */
public class App extends Application {

    private static final Font MONO = Font.font("Monospaced", 18);

    private List<String> practiceTexts;
    private int currentTextIndex = 0;
    private TypingSession session;

    private final TextFlow targetText = new TextFlow();
    private final TextField typingField = new TextField();
    private final ProgressBar progressBar = new ProgressBar(0);
    private final Label stats = new Label();
    private final Label lineCounter = new Label();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        practiceTexts = PracticeTexts.load();

        typingField.setPromptText("Start typing...");
        typingField.setFont(MONO);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        targetText.setPrefHeight(80);

        VBox root = new VBox(10, lineCounter, targetText, typingField, progressBar, stats);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        typingField.textProperty().addListener((obs, oldValue, newValue) -> onTyped(newValue));

        // Refresh speed once a frame so WPM keeps moving while the typist pauses.
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                showStats();
            }
        }.start();

        startLine(0);

        stage.setTitle("TypeTrainer");
        stage.setScene(new Scene(root, 640, 280));
        stage.show();
    }

    private void startLine(int index) {
        currentTextIndex = index;
        session = new TypingSession(practiceTexts.get(index), Instant::now);
        lineCounter.setText("Sentence " + (index + 1) + " of " + practiceTexts.size());
        typingField.clear();
        render();
    }

    private void onTyped(String typed) {
        session.update(typed);
        render();

        if (session.isComplete()) {
            if (currentTextIndex + 1 < practiceTexts.size()) {
                // Defer: clearing the field inside its own change listener re-enters it.
                Platform.runLater(() -> startLine(currentTextIndex + 1));
            } else {
                typingField.setDisable(true);
                lineCounter.setText("All sentences completed!");
            }
        }
    }

    private void render() {
        targetText.getChildren().clear();
        String target = session.target();
        for (int i = 0; i < target.length(); i++) {
            Text ch = new Text(String.valueOf(target.charAt(i)));
            ch.setFont(MONO);
            ch.setFill(
                    switch (session.stateAt(i)) {
                        case CORRECT -> Color.FORESTGREEN;
                        case WRONG -> Color.CRIMSON;
                        case PENDING -> Color.DIMGRAY;
                    });
            targetText.getChildren().add(ch);
        }
        progressBar.setProgress(session.progress());
        showStats();
    }

    private void showStats() {
        if (session != null) {
            stats.setText(
                    "%.0f WPM    %.0f%% accuracy"
                            .formatted(session.wordsPerMinute(), session.accuracy() * 100));
        }
    }
}
