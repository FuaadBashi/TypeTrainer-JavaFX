# TypeTrainer — JavaFX Typing Practice

A JavaFX desktop exercise that highlights correct and incorrect characters as you type, updates a progress bar, and advances through practice sentences.

## Code to explore

- [App.java](App.java): UI construction, text-change listener, highlighting, and progress calculation.
- [typeracer_texts.txt](typeracer_texts.txt): practice sentences.
- [pom.xml](pom.xml): Maven dependencies and JavaFX launch configuration.
- [module-info.java](module-info.java): module declarations.

## Setup status

The checkout currently stores Java files at the repository root, while the Maven file assumes the standard source layout. `App.java` also contains an absolute path to the original author's practice-text file.

To run it in a local JavaFX development environment:

1. Use JDK 17 or later and configure JavaFX Controls and Web modules.
2. Import `App.java` as `typetrainer.App`.
3. Update `exampleText` to the local path of `typeracer_texts.txt`.
4. Launch `typetrainer.App` in a graphical desktop session.

A reproducible Maven quick start still needs the source/resource layout and JavaFX versions aligned. The application currently measures character progress, not words per minute.
