import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInput {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            System.out.print("Enter Transaction ID: ");
            String transactionId = sc.nextLine();

            // Transaction Type
            String transactionType;
            while (true) {
                System.out.print("Enter Transaction Type (Credit/Debit): ");
                transactionType = sc.nextLine();
                if (transactionType.equalsIgnoreCase("Credit") ||
                        transactionType.equalsIgnoreCase("Debit")) break;
                System.out.println("🚨 ALERT: Enter only Credit or Debit!");
            }

            // Transaction Mode
            String transactionMode;
            while (true) {
                System.out.print("Enter Transaction Mode (UPI/IMPS/NEFT/RTGS/INTERNATIONAL): ");
                transactionMode = sc.nextLine();
                if (transactionMode.equalsIgnoreCase("UPI") ||
                        transactionMode.equalsIgnoreCase("IMPS") ||
                        transactionMode.equalsIgnoreCase("NEFT") ||
                        transactionMode.equalsIgnoreCase("RTGS") ||
                        transactionMode.equalsIgnoreCase("INTERNATIONAL")) break;
                System.out.println("🚨 ALERT: Invalid Mode! Try again.");
            }

            System.out.print("Enter Sender ID: ");
            String senderId = sc.nextLine();

            System.out.print("Enter Sender Account: ");
            String senderAccount = sc.nextLine();

            // Sender Mobile Validation
            String senderMobile;
            while (true) {
                System.out.print("Enter Sender Mobile (10 digits starting 6-9): ");
                senderMobile = sc.nextLine();
                if (senderMobile.matches("[6-9][0-9]{9}")) break;
                System.out.println("🚨 ALERT: Invalid Sender Mobile!");
            }

            // Device Validation
            String senderDevice;
            while (true) {
                System.out.print("Enter Sender Device (Android/iPhone/Laptop/ATM): ");
                senderDevice = sc.nextLine();
                if (senderDevice.equalsIgnoreCase("Android") ||
                        senderDevice.equalsIgnoreCase("iPhone") ||
                        senderDevice.equalsIgnoreCase("Laptop") ||
                        senderDevice.equalsIgnoreCase("ATM")) break;
                System.out.println("🚨 ALERT: Invalid Device!");
            }

            System.out.print("Enter Sender Location: ");
            String senderLocation = sc.nextLine();

            System.out.print("Enter Receiver ID: ");
            String receiverId = sc.nextLine();

            System.out.print("Enter Receiver Account: ");
            String receiverAccount = sc.nextLine();

            // Receiver Mobile Validation
            String receiverMobile;
            while (true) {
                System.out.print("Enter Receiver Mobile (10 digits starting 6-9): ");
                receiverMobile = sc.nextLine();
                if (receiverMobile.matches("[6-9][0-9]{9}")) break;
                System.out.println("🚨 ALERT: Invalid Receiver Mobile!");
            }

            System.out.print("Enter Receiver Location: ");
            String receiverLocation = sc.nextLine();

            // Auth Type Validation
            String authType;
            while (true) {
                System.out.print("Enter Authentication Type (PIN/OTP/Biometric/NONE): ");
                authType = sc.nextLine();
                if (authType.equalsIgnoreCase("PIN") ||
                        authType.equalsIgnoreCase("OTP") ||
                        authType.equalsIgnoreCase("Biometric") ||
                        authType.equalsIgnoreCase("NONE")) break;
                System.out.println("🚨 ALERT: Invalid Auth Type!");
            }

            // Amount Validation
            double amount;
            while (true) {
                System.out.print("Enter Transaction Amount: ");
                amount = sc.nextDouble();
                if (amount > 0) break;
                System.out.println("🚨 ALERT: Amount must be greater than 0!");
            }
            sc.nextLine(); // clear buffer

            // ---------------- Transaction Time ----------------
            String transactionTime;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            while (true) {
                System.out.print("Enter Transaction Time (HH:mm format, 24hr): ");
                transactionTime = sc.nextLine();
                try {
                    LocalTime.parse(transactionTime, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("🚨 ALERT: Invalid time format! Example: 02:30 or 14:45");
                }
            }

            // ---------------- BUILD JSON ----------------
            String json = String.format("""
            {
              "transaction_id": "%s",
              "transaction_type": "%s",
              "transaction_mode": "%s",
              "amount": %f,
              "sender_id": "%s",
              "sender_account": "%s",
              "sender_mobile": "%s",
              "sender_device": "%s",
              "sender_location": "%s",
              "receiver_id": "%s",
              "receiver_account": "%s",
              "receiver_mobile": "%s",
              "receiver_location": "%s",
              "auth_type": "%s",
              "transaction_time": "%s"
            }
            """,
                    transactionId,
                    transactionType,
                    transactionMode,
                    amount,
                    senderId,
                    senderAccount,
                    senderMobile,
                    senderDevice,
                    senderLocation,
                    receiverId,
                    receiverAccount,
                    receiverMobile,
                    receiverLocation,
                    authType,
                    transactionTime
            );

            // ---------------- CALL SPRING API ----------------
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/transactions"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- API RESPONSE ---");
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}