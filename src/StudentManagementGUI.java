import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StudentManagementGUI {
    static final String DIRECTORY_PATH = "C:\\StudentRecords"; // Change to desired directory
    static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        new File(DIRECTORY_PATH).mkdirs(); // Ensure directory exists
        displayMainMenu();
    }

    // Main menu
    static void displayMainMenu() {
        String[] options = {"Create Student Record", "View and Read Records", "Exit"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(
                null,
                "Student Management System",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            switch (choice) {
                case 0 -> createStudentRecord();
                case 1 -> viewAndReadRecords();
                case 2 -> System.exit(0);
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    // Create student record
    static void createStudentRecord() {
        String fullName = JOptionPane.showInputDialog("Enter Full Name:");
        String department = JOptionPane.showInputDialog("Enter College Department:");
        String schoolId = JOptionPane.showInputDialog("Enter School ID:");
        String bloodType = JOptionPane.showInputDialog("Enter Blood Type:");
        String birthDate = JOptionPane.showInputDialog("Enter Birthdate (YYYY-MM-DD):");

        if (fullName == null || department == null || schoolId == null || bloodType == null || birthDate == null
                || fullName.isEmpty() || department.isEmpty() || schoolId.isEmpty()
                || bloodType.isEmpty() || birthDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        Student student = new Student(fullName, department, schoolId, bloodType, birthDate);
        studentList.add(student);
        saveStudentToFile(student);

        JOptionPane.showMessageDialog(null, "Student record created successfully.");
    }

    // Save student record to file
    static void saveStudentToFile(Student student) {
        String fileName = DIRECTORY_PATH + File.separator + student.getFullName() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Full Name: " + student.getFullName());
            writer.println("Department: " + student.getDepartment());
            writer.println("School ID: " + student.getSchoolId());
            writer.println("Blood Type: " + student.getBloodType());
            writer.println("Birthdate: " + student.getBirthDate());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
        }
    }

    // View and read student records
    static void viewAndReadRecords() {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "No records found.");
            return;
        }

        StringBuilder fileList = new StringBuilder("Available Files:\n");
        for (File file : files) {
            fileList.append("- ").append(file.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, fileList.toString());

        String fileName = JOptionPane.showInputDialog("Enter the file name to read (with .txt extension):");
        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name cannot be empty.");
            return;
        }

        File selectedFile = new File(DIRECTORY_PATH + File.separator + fileName);
        if (!selectedFile.exists()) {
            JOptionPane.showMessageDialog(null, "File not found. Please select a valid file.");
            return;
        }

        // Read file content
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            JOptionPane.showMessageDialog(null, content.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
        }
    }
}

// Student class
class Student {
    private final String fullName;
    private final String department;
    private final String schoolId;
    private final String bloodType;
    private final String birthDate;

    public Student(String fullName, String department, String schoolId, String bloodType, String birthDate) {
        this.fullName = fullName;
        this.department = department;
        this.schoolId = schoolId;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
