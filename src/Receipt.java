public class Receipt {
    private String receiptNumber;
    private String memberName;
    private String memberId;
    private String className;
    private String trainerName;
    private String day;
    private String time;
    private double amount;
    private String paymentMethod;
    private String dateTime;

    public Receipt(String memberName, String memberId, String className,
                   String trainerName, String day, String time,
                   double amount, String paymentMethod) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.className = className;
        this.trainerName = trainerName;
        this.day = day;
        this.time = time;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.dateTime = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        this.receiptNumber = generateReceiptNumber();
    }

    private String generateReceiptNumber() {
        return "RCP" + (int)(Math.random() * 100000000);
    }

    public void printReceipt() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("               FITNESS CLUB RECEIPT");
        System.out.println("=".repeat(50));
        System.out.println("Receipt No: " + receiptNumber);
        System.out.println("Date: " + dateTime);
        System.out.println("-".repeat(50));
        System.out.println("MEMBER INFORMATION");
        System.out.println("  Member ID: " + memberId);
        System.out.println("  Name: " + memberName);
        System.out.println("-".repeat(50));
        System.out.println("CLASS DETAILS");
        System.out.println("  Class: " + className);
        System.out.println("  Trainer: " + trainerName);
        System.out.println("  Day: " + day);
        System.out.println("  Time: " + time);
        System.out.println("-".repeat(50));
        System.out.println("PAYMENT DETAILS");
        System.out.println("  Payment Method: " + paymentMethod);
        System.out.println("  Amount: RM " + String.format("%.2f", amount));
        System.out.println("-".repeat(50));
        System.out.println("Thank you for choosing FITNESS CLUB!");
        System.out.println("=".repeat(50));
    }
}