public class TrainerUi {

    //Trainer login system
    public static Trainer trainerLogin() {
        System.out.println("\n=== TRAINER LOGIN ===");
        System.out.println("(Enter 0 at any prompt to cancel)");
        System.out.print("Username (name): ");
        String name = MainSystem.input.nextLine();

        if(name.equals("0")){
            System.out.println("Login cancelled.");
            return null;
        }

        System.out.print("Password: ");
        String pass = MainSystem.input.nextLine();

        if (pass.equals("0")) {
            System.out.println("Login cancelled.");
            return null;
        }

        Trainer t = MainSystem.trainerManager.login(name, pass);
        if (t == null) {
            System.out.println("Invalid credentials.");
            return null;
        }
        MainSystem.currentTrainer = t;
        System.out.println("Login successful! Welcome " + t.getName());
        return t;
    }

    //trainer register (but no use)
    public static void trainerRegister() {
        System.out.println("\n=== TRAINER REGISTRATION ===");
        System.out.println("(Enter 0 anytime to cancel)");
        String name, pass, gender, phone, ic, level;

        while (true) {
            System.out.print("Enter name: ");
            name = MainSystem.input.nextLine();
            if (name.equals("0")) return;
            if (name.isEmpty() || InputHelper.isAllDigits(name)) {
                System.out.println("Invalid name!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter password: ");
            pass = MainSystem.input.nextLine();
            if (pass.equals("0")) return;
            if (pass.isEmpty() || InputHelper.containsInvalidOperator(pass)) {
                System.out.println("Invalid password!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Gender (Male/Female): ");
            gender = MainSystem.input.nextLine();
            if (gender.equals("0")) return;
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                gender = gender.substring(0,1).toUpperCase() + gender.substring(1).toLowerCase();
                break;
            }
            System.out.println("Invalid gender.");
        }
        while (true) {
            System.out.print("Phone number (e.g., 0123456789): ");
            phone = MainSystem.input.nextLine();
            if (phone.equals("0")) return;
            if (phone.isEmpty() || !phone.matches("\\d{10,11}")) {
                System.out.println("Phone must be 10-11 digits.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("IC number (12 digits, no dash): ");
            ic = MainSystem.input.nextLine();
            if (ic.equals("0")) return;
            if (ic.isEmpty() || !ic.matches("\\d{12}")) {
                System.out.println("IC must be 12 digits.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Level (Junior/Senior): ");
            level = MainSystem.input.nextLine();
            if (level.equals("0")) return;
            if (level.equalsIgnoreCase("Junior") || level.equalsIgnoreCase("Senior")) {
                level = level.substring(0,1).toUpperCase() + level.substring(1).toLowerCase();
                break;
            }
            System.out.println("Invalid level. Choose Junior or Senior.");
        }
        MainSystem.trainerManager.registerTrainer(name, pass, gender, phone, ic, level);
    }

    public static void updateEquipmentStatus() {
        MainSystem.equipmentManager.listEquipmentForUpdate();
        System.out.print("Enter Equipment ID to update (0 to cancel): ");
        int id = InputHelper.getInput();
        if (id == 0) return;

        System.out.print("Set status (1 for Available, 2 for Under Maintenance): ");
        int statusChoice = InputHelper.getInput();
        String newStatus;
        if (statusChoice == 1) newStatus = "Available";
        else if (statusChoice == 2) newStatus = "Under Maintenance";
        else {
            System.out.println("Invalid choice.");
            return;
        }

        boolean updated = MainSystem.equipmentManager.updateEquipmentStatus(id, newStatus);
        if (updated) {
            System.out.println("Equipment status updated successfully.");

            //JSON save
            MainSystem.equipmentManager.saveToFile("equipment.json");

        } else {
            System.out.println("Equipment ID not found.");
        }
    }

    public static void trainerPortal() {
        boolean exitPortal = false;

        while (!exitPortal) {
            if (MainSystem.currentTrainer == null) {

                System.out.println("\n=== TRAINER PORTAL ===");
                System.out.println("1. Login");
                System.out.println("2. Back");
                System.out.print("Choice: ");
                int choice = InputHelper.getInput();
                if (choice == 1) {
                    trainerLogin();
                } else if (choice == 2) {
                    exitPortal = true;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {

                boolean backToMain = false;
                while (!backToMain) {
                    System.out.println("\n=== TRAINER MENU ===");
                    System.out.println("Welcome, " + MainSystem.currentTrainer.getName());
                    System.out.println("1. View My Classes & Clients");
                    System.out.println("2. Update Equipment Status");
                    System.out.println("3. Logout");
                    System.out.println("4. Back");
                    System.out.print("Choice: ");
                    int opt = InputHelper.getInput();
                    switch (opt) {
                        case 1:
                            MainSystem.currentTrainer.viewMyClasses();
                            break;
                        case 2:
                            updateEquipmentStatus();
                            break;
                        case 3:
                            MainSystem.currentTrainer = null;
                            System.out.println("Logged out.");
                            backToMain = true;
                            break;
                        case 4:
                            backToMain = true;
                            exitPortal = true;
                            System.out.println("Returning to main menu.");
                            break;
                        default:
                            System.out.println("Invalid.");
                    }
                }
            }
        }
    }

    //showing the trainer revenue report
    public static void showRevenueReport() {
        System.out.println("\n=== TRAINER REVENUE REPORT ===");
        System.out.println("Please login to view your revenue report.");

        System.out.print("Enter Trainer Name (or 0 to cancel): ");
        String name = MainSystem.input.nextLine();

        if (name.equals("0")) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("Enter Password (or 0 to cancel): ");
        String password = MainSystem.input.nextLine();

        if(password.equals("0")){
            System.out.println("Cancelled.");
            return;
        }

        // Find trainer by name and password
        Trainer loggedTrainer = null;
        Trainer[] allTrainers = MainSystem.trainerManager.getAllTrainers();

        for (Trainer t : allTrainers) {
            if (t != null && t.getName().equalsIgnoreCase(name) && t.getPassword().equals(password)) {
                loggedTrainer = t;
                break;
            }
        }

        if (loggedTrainer == null) {
            System.out.println("Invalid Trainer Name or Password!");
            return;
        }

        // Display personal revenue report
        System.out.println("\n+================================================================================+");
        System.out.printf("                           REVENUE REPORT FOR TRAINER: %-20s       \n", loggedTrainer.getName());
        System.out.println("+================================================================================+");

        double grandTotal = 0;
        boolean hasAnyBooking = false;

        // check every trainer course
        for (Schedule schedule : loggedTrainer.getSchedules()) {   // this method from Trainer "getSchedules"
            if (schedule == null) continue;
            int studentCount = schedule.getMemberCount();
            if (studentCount == 0) continue;

            hasAnyBooking = true;
            System.out.println("\n  Course: " + schedule.getClassName());
            System.out.println("  Time: " + schedule.getDay() + " " + schedule.getTime());
            System.out.println("  Price per person: RM " + String.format("%.2f", schedule.getPrice()));
            System.out.println("  +------+----------------------+---------------------------+");
            System.out.println("  |  No. | Student Name         | Amount (RM)               |");
            System.out.println("  +------+----------------------+---------------------------+");

            Member[] students = schedule.getMembers();
            double courseTotal = 0;
            for (int i = 0; i < studentCount; i++) {
                Member m = students[i];
                if (m == null) continue;
                double amount = schedule.getPrice();
                courseTotal += amount;
                System.out.printf("  |  %-3d | %-20s | RM %18.2f     |\n", (i+1), m.getName(), amount);
            }
            System.out.println("  +------+----------------------+---------------------------+");
            System.out.printf("  |  Subtotal for this class:    | RM        %10.2f     |\n", courseTotal);
            System.out.println("  +------+----------------------+---------------------------+");
            grandTotal += courseTotal;
        }

        if (!hasAnyBooking) {
            System.out.println("\n  No students have booked any of your classes yet.");
        }

        System.out.println("\n  +==============================================+");
        System.out.printf("  |  TOTAL REVENUE (ALL CLASSES):  RM %10.2f |\n", grandTotal);
        System.out.println("  +==============================================+");
    }





}
