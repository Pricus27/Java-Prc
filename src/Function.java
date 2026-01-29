import javax.swing.JOptionPane;
import java.io.*;
// import java.util.Scanner;

public class Function { 
static String filename = null ; 
static FileWriter writer = null;


    public static void Create() {
        String filename = null; 
    
       filename = JOptionPane.showInputDialog("Enter File Name");
            try {
                    File Fobj = new File(filename);
                    Fobj.createNewFile();
                    JOptionPane.showMessageDialog(null, "File Successfully Created: " + filename);
            } catch (IOException e) {
                    System.out.println(e.getMessage());
             }
    }



    //
    //
    // write
     public static void Insert() {
        //JOptionPane.showInputDialog(null,"Enter Filename:");

        //  if(filename.isEmpty()){
        //     JOptionPane.showMessageDialog(null, "Please create a file first");
        //     return;
        //  }
        
        String empNum = JOptionPane.showInputDialog("Enter Student #: ");
        String firstN = JOptionPane.showInputDialog(null, "Enter First Name: ");
        String middleN = JOptionPane.showInputDialog(null, "Enter First Name: ");
        String lastN = JOptionPane.showInputDialog(null, "Enter Last Name: ");
        String bdate = JOptionPane.showInputDialog(null, "Ex:YYYY-MM-DD\n" +"Enter Bdate: ");    
        String position = JOptionPane.showInputDialog(null, "Enter COLLEGE DEPARTMENT: ");
        String salary = JOptionPane.showInputDialog(null, "Enter major: ");

        String record = empNum + "  \t  " + firstN + "  \t  " + middleN+   " \t " + lastN + "  \t  " + bdate + "  \t  " + position + "  \t  " + salary;

        try(BufferedReader Br = new BufferedReader(new InputStreamReader(System.in))){
             writer = new FileWriter(filename);
             writer.write(record);
             writer.close();
            

        }catch(IOException e){
            System.out.print(e.getMessage());
        }
    }


    //
    //
    //

     public static void Update() {
        // I UPDATE ANG FILE
    }


    //
    //
    //
    public static void Delete() {
         filename = JOptionPane.showInputDialog("Enter File Name");
         File Fobj = new File(filename);
        Fobj.delete();
        JOptionPane.showMessageDialog(null, "File Successfully De;eted: " + filename);

    }


    //
    //
    //
    public static void ShowRecords() {
        // show 
    }



}
