import javax.swing.JOptionPane;
// import java.util.Scanner;
// import java.io.*;

public class Final_project {
    public static void main(String[] args){
        int choice;
        boolean exit = false;

        while(!exit){
            JOptionPane.showMessageDialog(null, "STUDENT ID \n1. Create student  file \n2. Insert New Record "
                    + "\n3. Update Existing Record \n4. Delete Record \n5. Show Records \n6. Search Records \n7. Exit");
            
            choice = Integer.parseInt(JOptionPane.showInputDialog("Enter your Choice:"));

            //For choices gar
            switch(choice){
                case 1:
                Function.Create();
                case 2:
                Function.Insert();
                case 3:
                Function.Update();
                case 4:
                Function.Delete();
                case 5:
                Function.ShowRecords();
                case 6:
                //
                case 7:
                exit = true;
            }
        }
    }
}

