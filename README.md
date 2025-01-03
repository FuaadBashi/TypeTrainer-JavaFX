# TypeTrainer

## Overview
TypeTrainer is a JavaFX-based desktop application designed to help users improve their typing speed and accuracy through engaging and interactive practice sessions. The application highlights text as you type and provides real-time feedback, such as accuracy and progress tracking.

## Features
- **Dynamic Text Highlighting**: Characters are highlighted green for correct entries and red for incorrect ones.
- **Progress Tracking**: A progress bar updates as the user types to indicate completion percentage.
- **Practice Texts**: Includes a variety of preset texts for typing practice, loaded from a file.
- **Responsive UI**: Built with JavaFX for a modern and user-friendly interface.

## Technologies Used
- **Java** (Version 17 or higher)
- **JavaFX** (Controls, FXML, and Web modules)
- **Maven** for dependency management and project structure

## File Structure
### Key Files:
1. `App.java`: The main entry point for the application, responsible for initializing the GUI and managing user interactions.
2. `typeracer_texts.txt`: A text file containing various phrases and sentences for typing practice.
3. `pom.xml`: Maven configuration file that manages dependencies and build settings.

### Project Directory Layout
```
/typetrainer
|-- /src
    |-- /main
        |-- /java
            |-- typetrainer
                |-- App.java
        |-- /resources
            |-- typeracer_texts.txt
|-- pom.xml
```

## Prerequisites
1. **Java Development Kit (JDK)**: Version 17 or higher.
2. **Maven**: Ensure Maven is installed and configured on your system.

## Installation
1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd typetrainer
    ```
2. Build the project using Maven:
    ```bash
    mvn clean install
    ```
3. Run the application:
    ```bash
    mvn javafx:run
    ```

## How to Use
1. Launch the application.
2. Select or view the target text displayed in the application.
3. Start typing in the input field below the target text.
4. Monitor your progress using the progress bar and observe real-time text highlighting for feedback.

## Customization
To modify the practice texts, edit the `typeracer_texts.txt` file located in the `/resources` directory. Add or remove lines as needed to customize the typing practice content.

## Dependencies
- JavaFX Controls (version 21.0.1)
- JavaFX FXML (version 21.0.1)
- JavaFX Web (version 17.0.2)

Dependencies are managed through the `pom.xml` file:
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-web</artifactId>
        <version>17.0.2</version>
    </dependency>
</dependencies>
```

## License
This project is licensed under the MIT License. Feel free to use, modify, and distribute as needed.

## Contact
For any questions or feedback, please contact Fuaad Shurie at fuaad.shurie@example.com.

