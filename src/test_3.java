import java.util.Scanner;

public class test_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        
        int[] numbers = new int[3];
        
         
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = sc.nextInt();
        }
        
       
        int greatest = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > greatest) {
                greatest = numbers[i];
            }
        }
        
        
        System.out.println("The greatest number is: " + greatest);
        
        sc.close();
    }
}
