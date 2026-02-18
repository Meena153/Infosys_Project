import java.util.*;

public class Transaction {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter User ID: ");
                int userId = sc.nextInt();
                System.out.print("Enter User Mobile Number: ");
                long userMobile = sc.nextLong();
                System.out.print("Enter Receiver Mobile Number: ");
                long receiverMobile = sc.nextLong();
                System.out.print("Enter Transaction Amount: ");
                double amount = sc.nextDouble();
                if (amount <= 0) {
                    throw new Exception("Amount must be greater than zero.");
                }
                if (String.valueOf(userMobile).length() != 10 ||
                        String.valueOf(receiverMobile).length() != 10) {
                    throw new Exception("Mobile number must be exactly 10 digits.");
                }
                if (amount > 50000) {
                    throw new Exception("Fraud Alert: Amount exceeds ₹50,000 limit.");
                }
                System.out.println("\n----- Transaction Details -----");
                System.out.println("User ID : " + userId);
                System.out.println("Sender Mobile Number : " + userMobile);
                System.out.println("Receiver Mobile Number : " + receiverMobile);
                System.out.println("Amount Transferred : " + amount);
                System.out.println("Transaction Successful");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter numbers only.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Please try again...");
        }
        sc.close();
    }
}