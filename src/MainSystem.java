import java.util.Scanner;

public class MainSystem {

    static Scanner input = new Scanner(System.in);
    static Member currentUser = null;
    static Trainer currentTrainer = null;

    // SO manager is an object that come from MemberManager class
   // create object to call method from each class
    static MemberManager manager = new MemberManager();
    static TrainerManager trainerManager = new TrainerManager();
    static ActivityManager activityManager = new ActivityManager();
    static EquipmentManager equipmentManager = new EquipmentManager(20);


    // Main System execute
    public static void main(String[] args) {

        DataInitializer.initAll();

        boolean showLogo = true;
        while (true) {
            if (showLogo) {
                UiHelper.showHomepage();
                showLogo = false;
            }

            System.out.println("\n===== MAIN SYSTEM =====");
            System.out.println("1. Membership Tiers (Basic, Silver, Gold)");
            System.out.println("2. Class Scheduling & Equipment Tracking");
            System.out.println("3. Trainer & Client Portals");
            System.out.println("4. Monthly Revenue Report");
            System.out.println("5. BMI CALCULATOR");
            System.out.println("6. Exit");
            System.out.print("\nPlease Enter Your Choice(1-6): ");
            int choice = InputHelper.getInput();

            if (choice == 1) {
                MemberUi.membershipMenu();
                showLogo = true;
            } else if (choice == 2) {
                boolean back = false;
                while (!back) {
                    System.out.println("\n=== CLASS & EQUIPMENT ===");
                    System.out.println("1. View Class Schedule");
                    System.out.println("2. View Equipment Status");
                    System.out.println("3. Back");
                    System.out.print("Enter choice (1-3): ");
                    int sub = InputHelper.getInput();
                    switch (sub) {
                        case 1:
                            activityManager.showAllActivities();
                            break;
                        case 2:
                            equipmentManager.showEquipmentTable();
                            break;
                        case 3:
                            back = true;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
                showLogo = true;
            } else if (choice == 3) {
                TrainerUi.trainerPortal();
            } else if (choice == 4) {
                TrainerUi.showRevenueReport();
            } else if (choice == 5) {
                BmiHelper.run();
            } else if (choice == 6) {
                System.out.println("System exit.");
                break;
            }
        }
    }
}