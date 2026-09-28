package typetrainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Loads the bundled practice sentences. */
public final class PracticeTexts {

    private static final String RESOURCE = "practice-texts.txt";

    private PracticeTexts() {}

    /**
     * Reads the sentences from the classpath, so the app runs from any directory. It used to read
     * an absolute path that only existed on the original author's machine.
     */
    public static List<String> load() {
        try (InputStream in = PracticeTexts.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            return parse(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** One sentence per line; blank lines and surrounding spaces are ignored. */
    static List<String> parse(BufferedReader reader) {
        return reader.lines().map(String::strip).filter(line -> !line.isEmpty()).toList();
    }
}
