public class MemberUi {

    //For Register part (call method)
    public static void register() {

        System.out.println("\n=== Register ===");
        System.out.println("(Enter 0 anytime to cancel)");

        String name;
        String pass;
        String gender;
        String phoneNumber;
        String icNumber;

        // ===== NAME =====
        while (true) {
            System.out.print("Enter name: ");
            name = MainSystem.input.nextLine();

            if (name.equals("0")) {
                System.out.println("Register cancelled.");
                return;
            }

            if (name.length() == 0) {
                System.out.println("Name cannot be empty!");
                continue;
            }

            //form InputHelper
            if (InputHelper.isAllDigits(name)) {
                System.out.println("Name cannot be all numbers!");
                continue;
            }

            break;
        }

        // ===== PASSWORD =====
        while (true) {
            System.out.print("Enter password: ");
            pass = MainSystem.input.nextLine();

            if (pass.equals("0")) {
                System.out.println("Register cancelled.");
                return;
            }

            if (pass.length() == 0) {
                System.out.println("Password cannot be empty!");
                continue;
            }

            //From input helper
            if (InputHelper.containsInvalidOperator(pass)) {
                System.out.println("Password cannot contain + - * / !");
                continue;
            }

            break;
        }

        // ===== GENDER =====
        while (true) {
            System.out.print("Enter gender (Male/Female): ");
            gender = MainSystem.input.nextLine();

            if (gender.equals("0")) {
                System.out.println("Register cancelled.");
                return;
            }

            if (gender.length() == 0) {
                System.out.println("Gender cannot be empty!");
                continue;
            }

            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                gender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Invalid gender! Please enter Male or Female.");
            }
        }

        // ===== PHONE NUMBER =====
        while (true) {
            System.out.print("Enter phone number (e.g., 0123456789): ");
            phoneNumber = MainSystem.input.nextLine();

            if (phoneNumber.equals("0")) {
                System.out.println("Register cancelled.");
                return;
            }

            if (phoneNumber.length() == 0) {
                System.out.println("Phone number cannot be empty!");
                continue;
            }

            boolean validPhone = true;
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (!Character.isDigit(phoneNumber.charAt(i))) {
                    validPhone = false;
                    break;
                }
            }

            if (!validPhone) {
                System.out.println("Phone number must contain only numbers!");
                continue;
            }

            if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
                System.out.println("Phone number must be 10-11 digits!");
                continue;
            }

            break;
        }

        // ===== IC NUMBER =====
        while (true) {
            System.out.print("Enter IC number (12 digits) without - : ");
            icNumber = MainSystem.input.nextLine();

            if (icNumber.equals("0")) {
                System.out.println("Register cancelled.");
                return;
            }

            if (icNumber.length() == 0) {
                System.out.println("IC number cannot be empty!");
                continue;
            }

            boolean validIC = true;
            for (int i = 0; i < icNumber.length(); i++) {
                if (!Character.isDigit(icNumber.charAt(i))) {
                    validIC = false;
                    break;
                }
            }

            if (!validIC) {
                System.out.println("IC number must contain only numbers!");
                continue;
            }

            if (icNumber.length() != 12) {
                System.out.println("IC number must be 12 digits!");
                continue;
            }

            break;
        }

        MainSystem.manager.register(name, pass, gender, phoneNumber, icNumber);

        //successful register then save the data
        MainSystem.manager.saveToFile("members.json");
    }
    // Login part (call method)
    public static void login() {

        System.out.println("\n=== Login ===");
        System.out.println("(Enter 0 anytime to exit)");

        String name;
        String pass;

        // ===== NAME =====
        while (true) {
            System.out.print("Enter name: ");
            name = MainSystem.input.nextLine();

            if (name.equals("0")) {
                System.out.println("Login cancelled.");
                return;
            }

            if (name.length() == 0) {
                System.out.println("Name cannot be empty!");
                continue;
            }

            if (InputHelper.isAllDigits(name)) {
                System.out.println("Name cannot be all numbers!");
                continue;
            }

            break;
        }

        // ===== PASSWORD =====
        while (true) {
            System.out.print("Enter password: ");
            pass = MainSystem.input.nextLine();

            if (pass.equals("0")) {
                System.out.println("Login cancelled.");
                return;
            }

            if (pass.length() == 0) {
                System.out.println("Password cannot be empty!");
                continue;
            }

            if (InputHelper.containsInvalidOperator(pass)) {
                System.out.println("Password cannot contain + - * / !");
                continue;
            }

            break;
        }

        /*
        This Member m = manager.login(name,pass) is means using login
        method to collect the value and process it in the method.

         public Member login(String name, String pass){
            for(int i=0;i<memberCount;i++){

            if(members[i].getName().equals(name) &&
                    members[i].getPassword().equals(pass)){

                return members[i];
            }

        }
        return null;
    }

        So this is the method that allow the value can be find or not,
        if the user name is find, then will return
         */
        Member m = MainSystem.manager.login(name, pass);

        if (m != null) {
            MainSystem.currentUser = m;
            System.out.println("Login successful! ");
            System.out.println("Welcome " + m.getName());
        } else {
            System.out.println("Wrong name or password! ");
        }
    }



    public static void classScheduling() {
        if (MainSystem.currentUser == null) {
            System.out.println("Please login first!");
            return;
        }

        MainSystem.activityManager.showAllActivities();

        System.out.print("Select activity number (0 to cancel): ");
        int activityNum = InputHelper.getInput();//form InputHelper

        if (activityNum == 0) return;

        Activity selected = MainSystem.activityManager.getActivityByNumber(activityNum);

        if (selected == null) {
            System.out.println("Invalid activity!");
            return;
        }

        // Show time slot options for this activity
        selected.displayBookingOptions();

        System.out.print("Select time slot (1-2): ");
        int slotNum = InputHelper.getInput();

        if (slotNum == 0) return;

        Schedule selectedSchedule = selected.getScheduleByOption(slotNum);

        if (selectedSchedule == null) {
            System.out.println("Invalid time slot!");
            return;
        }

        if (selectedSchedule.isFull()) {
            System.out.println("This time slot is FULL!");
            return;
        }

        //Checking the function come from Schedule class
        if (selectedSchedule.isAlreadyBooked(MainSystem.currentUser)) {
            System.out.println("You have already booked this class!");
            return;
        }

        System.out.print("Confirm booking " + selected.getName() + " on " +
                selectedSchedule.getDay() + " " + selectedSchedule.getTime() +
                " with trainer " + selectedSchedule.getTrainerName() + "? (Y/N): ");
        String confirm = MainSystem.input.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            String paymentMethod = "";
            boolean paymentSuccess = false;

            System.out.print("Make a payment:\n1. Bank\n2. E-Wallet \n3. Cash\nEnter choice (0 to cancel): ");
            int payment = InputHelper.getInput();

            //cancel pay
            if(payment == 0){
                System.out.println("Payment cancelled. Booking not completed.");
                return;
            }

            if(payment == 1){
                System.out.print("Enter Account Number (16 digits) or 0 to cancel: ");
                String accountNum = MainSystem.input.nextLine();

                //cancel pay
                if (accountNum.equals("0")) {
                    System.out.println("Payment cancelled.");
                    return;
                }

                if(accountNum.length() == 16){
                    System.out.println("Payment successful!");
                    paymentMethod = "Bank Transfer";
                    paymentSuccess = true;
                } else {
                    System.out.println("Invalid account number! Payment failed.");
                    return;
                }
            }
            else if(payment == 2){
                System.out.print("Enter pin (or 0 to cancel): ");
                String pin = MainSystem.input.nextLine();

                //cancel pay
                if (pin.equals("0")) {
                    System.out.println("Payment cancelled.");
                    return;
                }

                System.out.println("Payment successful!");
                paymentMethod = "E-Wallet";
                paymentSuccess = true;
            }
            else if(payment == 3){
                System.out.print("Enter the cash amount you want to pay (or 0 to cancel): RM ");
                double cash = MainSystem.input.nextDouble();
                MainSystem.input.nextLine();// delete 'enter'

                //cancel pay
                if (cash == 0) {
                    System.out.println("Payment cancelled.");
                    return;
                }

                if(cash >= selectedSchedule.getPrice()) {
                    double change = cash - selectedSchedule.getPrice();
                    System.out.printf("Payment successful! Change: RM %.2f\n", change);
                    paymentMethod = "Cash";
                    paymentSuccess = true;
                } else {
                    System.out.println("Insufficient amount! Payment failed.");
                    return;
                }
            }
            else {
                System.out.println("Invalid payment option!");
                return;
            }

            if (paymentSuccess && selectedSchedule.bookSlot(MainSystem.currentUser)) {
                MainSystem.currentUser.addSchedule(selectedSchedule);

                Trainer trainer = selectedSchedule.getTrainer();
                trainer.addRevenue(selectedSchedule.getPrice());

                System.out.println("Booking successful!");

                //after booking successful then will save the member and trainer data
                MainSystem.manager.saveToFile("members.json");
                MainSystem.trainerManager.saveToFile("trainers.json");

                // Generate and print receipt
                Receipt receipt = new Receipt(
                        MainSystem.currentUser.getName(),
                        String.valueOf(MainSystem.currentUser.getId()),
                        selectedSchedule.getClassName(),
                        selectedSchedule.getTrainerName(),
                        selectedSchedule.getDay(),
                        selectedSchedule.getTime(),
                        selectedSchedule.getPrice(),
                        paymentMethod
                );
                receipt.printReceipt();
            } else {
                System.out.println("Booking failed!");
            }
        }
    }

    //member cancel booking
    public static void cancelBooking(){

        if (MainSystem.currentUser != null) {
            // collerct all the booking
            Schedule[] myScheds = MainSystem.currentUser.getMySchedules();
            java.util.ArrayList<Schedule> bookings = new java.util.ArrayList<>();
            for (Schedule s : myScheds) {
                if (s != null) bookings.add(s);
            }

            if (!bookings.isEmpty()) {
                System.out.println("\n=== Your Bookings (Select by number) ===");
                for (int i = 0; i < bookings.size(); i++) {
                    Schedule s = bookings.get(i);
                    System.out.printf("%d. %s | %s %s | Trainer: %s\n",
                            (i + 1), s.getClassName(), s.getDay(), s.getTime(), s.getTrainerName());
                }

                System.out.println("(Or enter 0 to use class name cancellation below)");
                System.out.print("Select booking number to cancel (or 0 for class name): ");
                int choice = InputHelper.getInput();

                if (choice == 0) {
                    System.out.print("Enter the class name to cancel (or 0 to cancel operation): ");
                    String className = MainSystem.input.nextLine();
                    if (className.equals("0")) {
                        System.out.println("Cancellation cancelled.");
                        return;
                    }
                    Schedule toCancel = null;
                    for (Schedule s : MainSystem.currentUser.getMySchedules()) {
                        if (s != null && s.getClassName().equalsIgnoreCase(className)) {
                            toCancel = s;
                            break;
                        }
                    }
                    if (toCancel == null) {
                        System.out.println("No booking found with that class name.");
                        return;
                    }
                    if (toCancel.cancelBooking(MainSystem.currentUser)) {
                        MainSystem.currentUser.removeSchedule(toCancel);
                        Trainer trainer = toCancel.getTrainer();
                        trainer.addRevenue(-toCancel.getPrice());
                        System.out.println("Booking cancelled successfully. Refund processed (simulated).");
                        MainSystem.manager.saveToFile("members.json");
                        MainSystem.trainerManager.saveToFile("trainers.json");
                    } else {
                        System.out.println("Cancellation failed.");
                    }
                    return;
                }

                if (choice > 0 && choice <= bookings.size()) {
                    Schedule toCancel = bookings.get(choice - 1);
                    System.out.print("Confirm cancel this booking? (Y/N): ");
                    String confirm = MainSystem.input.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (toCancel.cancelBooking(MainSystem.currentUser)) {
                            MainSystem.currentUser.removeSchedule(toCancel);
                            Trainer trainer = toCancel.getTrainer();
                            trainer.addRevenue(-toCancel.getPrice());
                            System.out.println("Booking cancelled successfully. Refund processed (simulated).");
                            MainSystem.manager.saveToFile("members.json");
                            MainSystem.trainerManager.saveToFile("trainers.json");
                        } else {
                            System.out.println("Cancellation failed.");
                        }
                    } else {
                        System.out.println("Cancellation cancelled.");
                    }
                    return;
                }
                else{
                    System.out.println("Invalid selection. Please enter a valid booking number or 0.");
                    return;
                }
            }
        }



        if(MainSystem.currentUser == null){
            System.out.println("Please login first!");
            return;
        }
        MainSystem.currentUser.viewMySchedule(); // display current booking, let member choose

        System.out.print("Enter the class name to cancel (or 0 to cancel operation): ");
        String className = MainSystem.input.nextLine();

        if (className.equals("0")) {
            return;
        }

        //checking the Schedule object from user booking

        Schedule toCancel = null;

        //check user booking (the object of schedule)
        for(Schedule s : MainSystem.currentUser.getMySchedules()){
            if(s != null && s.getClassName().equalsIgnoreCase(className)){
                toCancel = s;
                break;
            }
        }

        if (toCancel == null){
            System.out.println("No booking found with that class name.");
            return;
        }

        //cancel booking: from schedule delete member, then delete the muSchedule from "Member"
        if (toCancel.cancelBooking(MainSystem.currentUser)) {
            MainSystem.currentUser.removeSchedule(toCancel);   // need add removeSchedule from the "Member"
            // return trainer income (minus the course price)
            Trainer trainer = toCancel.getTrainer();
            trainer.addRevenue(-toCancel.getPrice());
            System.out.println("Booking cancelled successfully. Refund processed (simulated).");

            //after cancel booking successful then will save member and trainer data
            MainSystem.manager.saveToFile("members.json");
            MainSystem.trainerManager.saveToFile("trainers.json");

        } else {
            System.out.println("Cancellation failed.");
        }
    }


    //Member delete self account
    public static boolean deleteMemberAccount() {
        System.out.print("Confirm account deletion? (Y/N): ");
        String confirm = MainSystem.input.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Deletion cancelled.");
            return false;
        }
        boolean deleted = MainSystem.manager.deleteMember(MainSystem.currentUser.getId());
        if (deleted) {
            System.out.println("Your account has been deleted. Returning to main menu.");

            //delete successful will save the member data
            MainSystem.manager.saveToFile("members.json");
            return true;
        } else {
            System.out.println("Deletion failed.");
            return false;
        }
    }

    // Membership Menu (I didn't complete yet ya XD)
    public static void membershipMenu() {

        while (true) {

            System.out.println("\n=== MEMBERSHIP ===");

            if (MainSystem.currentUser == null) {
                System.out.println("1 Register");
                System.out.println("2 Login");
                System.out.println("3 Back");
                System.out.print("\nEnter choice: ");

                //from InputHelper
                int choice = InputHelper.getInput();

                if (choice == 1) register();
                else if (choice == 2) login();
                else if (choice == 3) return;
            } else {
                System.out.println("Logged in as: " + MainSystem.currentUser.getName());
                System.out.println("1. View Personal Profile");
                System.out.println("2. Book Class");
                System.out.println("3. My Schedule");
                System.out.println("4. Cancel Booking");
                System.out.println("5. Logout");
                System.out.println("6. Back");
                System.out.println("7. Delete My Account");

                System.out.print("\nEnter choice: ");
                int choice = InputHelper.getInput();

                if (choice == 2) {
                    classScheduling();
                }
                else if(choice==1){
                    MainSystem.currentUser.displayInfo();
                }
                else if (choice == 3) {
                    MainSystem.currentUser.viewMySchedule();
                } else if (choice == 5) {
                    MainSystem.currentUser = null;
                    System.out.println("Logged out.");
                }
                else if (choice == 4){
                    cancelBooking();
                }else if (choice == 6) {
                    return;
                }
                else if (choice == 7){
                    if(deleteMemberAccount()){
                        MainSystem.currentUser = null;
                        return;
                    }
                }
            }
        }
    }
}