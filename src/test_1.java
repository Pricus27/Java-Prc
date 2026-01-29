import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class test_1 extends Application {
    private static File currentDirectory = new File("C:\\StudentsRecord"); 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        Button createFileButton = new Button("Create New Student Record File");
        createFileButton.setOnAction(e -> createStudentRecordFile());

        Button displayFilesButton = new Button("Display List of .txt Files");
        displayFilesButton.setOnAction(e -> displayTxtFiles());

        Button retrieveRecordsButton = new Button("Retrieve Student Records from File");
        retrieveRecordsButton.setOnAction(e -> retrieveStudentRecords());

        Button changeDirectoryButton = new Button("Change Directory");
        changeDirectoryButton.setOnAction(e -> changeDirectory());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Exiting program. Goodbye!", ButtonType.OK);
            alert.showAndWait();
            System.exit(0);
        });

        VBox vbox = new VBox(createFileButton, displayFilesButton, retrieveRecordsButton, changeDirectoryButton, exitButton);
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void createStudentRecordFile() {
        String fileName = JOptionPane.showInputDialog("Enter the name of the new file:");
        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name cannot be empty.");
            return;
        }

        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }

        File file = new File(currentDirectory, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            JOptionPane.showMessageDialog(null, "Enter student records (ID, First Name, Last Name). Type 'END' to finish.");

            while (true) {
                String id = JOptionPane.showInputDialog("Enter Student ID (or type 'END' to stop):");
                if (id != null && id.equalsIgnoreCase("END")) {
                    break;
                }

                if (id == null || id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID cannot be empty.");
                    continue;
                }

                String firstName = JOptionPane.showInputDialog("Enter First Name:");
                String lastName = JOptionPane.showInputDialog("Enter Last Name:");

                writer.write(id + "," + firstName + "," + lastName + "\n");
            }
            JOptionPane.showMessageDialog(null, "File '" + file.getAbsolutePath() + "' created successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while creating the file: " + e.getMessage());
        }
    }

    private static void displayTxtFiles() {
        File[] files = currentDirectory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "No .txt files found in the directory.");
            return;
        }

        StringBuilder fileList = new StringBuilder("List of .txt Files in: " + currentDirectory.getAbsolutePath() + "\n");
        for (File file : files) {
            fileList.append("- ").append(file.getName()).append("\n");
        }

        JOptionPane.showMessageDialog(null, fileList.toString());
    }

    private static void retrieveStudentRecords() {
        File[] files = currentDirectory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "No .txt files found in the directory.");
            return;
        }

        String[] fileNames = Arrays.stream(files).map(File::getName).toArray(String[]::new);
        String fileName = (String) JOptionPane.showInputDialog(null, "Select a file to retrieve records from:", "Retrieve Student Records", JOptionPane.QUESTION_MESSAGE, null, fileNames, fileNames[0]);

        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No file selected.");
            return;
        }

        File selectedFile = new File(currentDirectory, fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            StringBuilder records = new StringBuilder("Student Records from file '" + fileName + "':\n");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    records.append("ID: ").append(parts[0]).append(", First Name: ").append(parts[1]).append(", Last Name: ").append(parts[2]).append("\n");
                }
            }

            JOptionPane.showMessageDialog(null, records.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static void changeDirectory() {
        String path = JOptionPane.showInputDialog("Enter the path of the new directory:");
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Path cannot be empty.");
            return;
        }

        File newDirectory = new File(path);
        if (newDirectory.exists() && newDirectory.isDirectory()) {
            currentDirectory = newDirectory;
            JOptionPane.showMessageDialog(null, "Directory changed to: " + currentDirectory.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "Invalid directory. Please try again.");
        }
    }
}
