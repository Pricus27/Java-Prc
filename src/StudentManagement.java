import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.*;

public class StudentManagement {
    static List<Student> studentList = new ArrayList<>();
    static String currentFile = null;
    static String directoryPath = "C:\\StudentsRecord"; 
    static String path1 = Paths.get("").toAbsolutePath().toString();

    public static void main(String[] args) {
        displayMainMenu();
    }

    static void displayMainMenu() {
        String[] options = {"Create New File", "Read Existing File", "Exit"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null, "Student Information Management System", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0: createNewFile(); break;
                case 1: readFileFromDirectory(); break;
                case 2: System.exit(0); break;
                default: JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 2);
    }

    static void createNewFile() {
        currentFile = directoryPath + File.separator + JOptionPane.showInputDialog("Enter new file name:");
        if (currentFile != null && !currentFile.isEmpty()) {
            saveRecords();
            displayFileMenu();
        } else {
            JOptionPane.showMessageDialog(null, "File name cannot be empty.");
        }
    }

    static void readFileFromDirectory() {
        try {
            Path directory = Paths.get(directoryPath);
            System.out.println("C:\\StudentsRecord: " + directory.toAbsolutePath());

            try (Stream<Path> paths = Files.list(directory)) {
                List<Path> filePaths = paths.filter(Files::isRegularFile)
                                            .filter(path -> path.toString().toLowerCase().endsWith(".txt"))
                                            .collect(Collectors.toList());

                if (!filePaths.isEmpty()) {
                    String[] fileNames = filePaths.stream().map(path -> path.getFileName().toString()).toArray(String[]::new);
                    System.out.println("Files found: " + Arrays.toString(fileNames));

                    String selectedFile = (String) JOptionPane.showInputDialog(null, "Select a file to read:",
                            "Read File", JOptionPane.QUESTION_MESSAGE, null, fileNames, fileNames[0]);

                    if (selectedFile != null) {
                        currentFile = directoryPath + File.separator + selectedFile;
                        loadRecords();
                        JOptionPane.showMessageDialog(null, "Records loaded from file: " + selectedFile);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No text files found in the specified directory.");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error accessing directory: " + e.getMessage());
        }
    }

    static void displayFileMenu() {
        String[] options = {"Create Student Record", "Read Student Records", "Update Student Record",
                "Delete Student Record", "Save Records to File", "Load Records from File", "Switch File", "Exit"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null, "Managing File: " + currentFile, "File Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0: createRecord(); break;
                case 1: readRecords(); break;
                case 2: updateRecord(); break;
                case 3: deleteRecord(); break;
                case 4: saveRecords(); break;
                case 5: loadRecords(); break;
                case 6: displayMainMenu(); return;
                case 7: System.exit(0); break;
                default: JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
            }
        } while (choice != 7);
    }

    static void createRecord() {
        String id = JOptionPane.showInputDialog("Enter Student ID:");
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");

        if (id != null && firstName != null && lastName != null &&
                !id.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
            studentList.add(new Student(id, firstName, lastName));
            JOptionPane.showMessageDialog(null, "Student record added.");
        } else {
            JOptionPane.showMessageDialog(null, "All fields are required.");
        }
    }

    static void readRecords() {
        StringBuilder records = new StringBuilder("Student Records:\n");
        for (Student student : studentList) {
            records.append(student).append("\n");
        }
        JOptionPane.showMessageDialog(null, records.toString());
    }

    static void updateRecord() {
        String id = JOptionPane.showInputDialog("Enter Student ID to update:");
        if (id != null && !id.isEmpty()) {
            for (Student student : studentList) {
                if (student.getId().equals(id)) {
                    String firstName = JOptionPane.showInputDialog("Enter new First Name:");
                    String lastName = JOptionPane.showInputDialog("Enter new Last Name:");
                    if (firstName != null && lastName != null &&
                            !firstName.isEmpty() && !lastName.isEmpty()) {
                        student.setFirstName(firstName);
                        student.setLastName(lastName);
                        JOptionPane.showMessageDialog(null, "Student record updated.");
                    } else {
                        JOptionPane.showMessageDialog(null, "All fields are required.");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Student ID not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Student ID cannot be empty.");
        }
    }

    static void deleteRecord() {
        String id = JOptionPane.showInputDialog("Enter Student ID to delete:");
        if (id != null && !id.isEmpty()) {
            Iterator<Student> iterator = studentList.iterator();
            while (iterator.hasNext()) {
                Student student = iterator.next();
                if (student.getId().equals(id)) {
                    iterator.remove();
                    JOptionPane.showMessageDialog(null, "Student record deleted.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Student ID not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Student ID cannot be empty.");
        }
    }

    static void saveRecords() {
        if (currentFile != null && !currentFile.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile))) {
                for (Student student : studentList) {
                    writer.println(student.getId() + "," + student.getFirstName() + "," + student.getLastName());
                }
                JOptionPane.showMessageDialog(null, "Records saved to file.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving records: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }

    static void loadRecords() {
        if (currentFile != null && !currentFile.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                String line;
                studentList.clear();
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        studentList.add(new Student(parts[0], parts[1], parts[2]));
                    }
                }
                JOptionPane.showMessageDialog(null, "Records loaded from file.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading records: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }
}

class Student {
    private String id;
    private String firstName;
    private String lastName;

    public Student(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return id + " - " + firstName + " " + lastName;
    }
}
