package typetrainer;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * Scores typing against one target line: which characters are right, how far through the line the
 * typist is, and their speed and accuracy. Has no JavaFX dependency so it can be unit-tested.
 */
public class TypingSession {

    /** How a target character looks given what has been typed so far. */
    public enum CharState {
        CORRECT,
        WRONG,
        PENDING
    }

    /** Words-per-minute uses the standard five characters per word. */
    private static final double CHARS_PER_WORD = 5.0;

    private final String target;
    private final Supplier<Instant> clock;
    private String typed = "";
    private Instant startedAt;
    private int keystrokes;
    private int correctKeystrokes;

    public TypingSession(String target, Supplier<Instant> clock) {
        if (target.isEmpty()) {
            // An empty target made progress 0/0 = NaN.
            throw new IllegalArgumentException("Target text must not be empty");
        }
        this.target = target;
        this.clock = clock;
    }

    public String target() {
        return target;
    }

    /** Records the new contents of the input field. */
    public void update(String newTyped) {
        if (startedAt == null && !newTyped.isEmpty()) {
            startedAt = clock.get();
        }
        // Count each newly typed character once, for accuracy; backspacing is not a keystroke.
        for (int i = typed.length(); i < newTyped.length(); i++) {
            keystrokes++;
            if (i < target.length() && newTyped.charAt(i) == target.charAt(i)) {
                correctKeystrokes++;
            }
        }
        typed = newTyped;
    }

    public CharState stateAt(int index) {
        if (index >= typed.length()) {
            return CharState.PENDING;
        }
        return typed.charAt(index) == target.charAt(index) ? CharState.CORRECT : CharState.WRONG;
    }

    public int correctChars() {
        int correct = 0;
        for (int i = 0; i < Math.min(typed.length(), target.length()); i++) {
            if (typed.charAt(i) == target.charAt(i)) {
                correct++;
            }
        }
        return correct;
    }

    /** Fraction of the target typed correctly, 0 to 1. */
    public double progress() {
        return (double) correctChars() / target.length();
    }

    public boolean isComplete() {
        return typed.equals(target);
    }

    /** Net words per minute: correct characters only, since the first keystroke. */
    public double wordsPerMinute() {
        if (startedAt == null) {
            return 0;
        }
        double minutes = Duration.between(startedAt, clock.get()).toMillis() / 60_000.0;
        return minutes <= 0 ? 0 : correctChars() / CHARS_PER_WORD / minutes;
    }

    /** Share of keystrokes that were right when typed, 0 to 1; 1 before any typing. */
    public double accuracy() {
        return keystrokes == 0 ? 1 : (double) correctKeystrokes / keystrokes;
    }
}
