package typetrainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import typetrainer.TypingSession.CharState;

class TypingSessionTest {

    private Instant now = Instant.parse("2025-01-01T10:00:00Z");

    private TypingSession session(String target) {
        return new TypingSession(target, () -> now);
    }

    @Test
    void eachCharacterIsMarkedCorrectWrongOrPending() {
        TypingSession s = session("cat");

        s.update("cu");

        assertEquals(CharState.CORRECT, s.stateAt(0));
        assertEquals(CharState.WRONG, s.stateAt(1));
        assertEquals(CharState.PENDING, s.stateAt(2));
    }

    @Test
    void progressCountsOnlyCorrectCharacters() {
        TypingSession s = session("abcd");

        s.update("abxd");

        assertEquals(0.75, s.progress());
        assertFalse(s.isComplete());
    }

    @Test
    void theLineIsCompleteOnlyWhenTypedExactly() {
        TypingSession s = session("hi there");

        s.update("hi there");

        assertTrue(s.isComplete());
        assertEquals(1.0, s.progress());
    }

    @Test
    void wordsPerMinuteUsesFiveCharactersPerWordFromTheFirstKeystroke() {
        TypingSession s = session("0123456789");
        s.update("0");
        now = now.plus(Duration.ofSeconds(12));

        s.update("0123456789");

        // 10 correct characters = 2 words in 12 seconds = 10 WPM.
        assertEquals(10.0, s.wordsPerMinute(), 1e-9);
    }

    @Test
    void accuracyRemembersMistakesEvenAfterTheyAreCorrected() {
        TypingSession s = session("ab");

        s.update("x");
        s.update("");
        s.update("a");
        s.update("ab");

        assertEquals(2.0 / 3.0, s.accuracy(), 1e-9);
        assertTrue(s.isComplete());
    }

    @Test
    void anEmptyTargetIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> session(""));
    }

    @Test
    void blankLinesInThePracticeFileAreSkipped() {
        List<String> texts =
                PracticeTexts.parse(new BufferedReader(new StringReader("One.\n\n  Two.  \n\n")));

        assertEquals(List.of("One.", "Two."), texts);
    }

    @Test
    void theBundledPracticeTextsLoadFromTheClasspath() {
        assertFalse(PracticeTexts.load().isEmpty());
    }
}
