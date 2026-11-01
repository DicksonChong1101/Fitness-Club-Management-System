public class DataInitializer {

    public static void setupDefaultTrainers() {
        // Fictional demo accounts only; never use these credentials with real user data.
        // Non-numeric phone/IC placeholders are intentional; constructors accept strings.
        Trainer t1 = new Trainer(1, "DemoAlice", "DemoOnly01", "Female", "DEMO-PHONE-01", "DEMO-ID-01", "Junior");
        Trainer t2 = new Trainer(2, "DemoJohn", "DemoOnly02", "Male", "DEMO-PHONE-02", "DEMO-ID-02", "Junior");
        Trainer t3 = new Trainer(3, "DemoMike", "DemoOnly03", "Male", "DEMO-PHONE-03", "DEMO-ID-03", "Senior");
        Trainer t4 = new Trainer(4, "DemoSarah", "DemoOnly04", "Female", "DEMO-PHONE-04", "DEMO-ID-04", "Senior");
        Trainer t5 = new Trainer(5, "DemoDavid", "DemoOnly05", "Male", "DEMO-PHONE-05", "DEMO-ID-05", "Senior");
        Trainer t6 = new Trainer(6, "DemoEmma", "DemoOnly06", "Female", "DEMO-PHONE-06", "DEMO-ID-06", "Junior");
        Trainer t7 = new Trainer(7, "DemoJames", "DemoOnly07", "Male", "DEMO-PHONE-07", "DEMO-ID-07", "Senior");

        MainSystem.trainerManager.addTrainer(t1);
        MainSystem.trainerManager.addTrainer(t2);
        MainSystem.trainerManager.addTrainer(t3);
        MainSystem.trainerManager.addTrainer(t4);
        MainSystem.trainerManager.addTrainer(t5);
        MainSystem.trainerManager.addTrainer(t6);
        MainSystem.trainerManager.addTrainer(t7);
    }


    public static void setupActivitiesOnly() {
        //
        Trainer t1 = MainSystem.trainerManager.getTrainerByName("DemoAlice");
        Trainer t2 = MainSystem.trainerManager.getTrainerByName("DemoJohn");
        Trainer t3 = MainSystem.trainerManager.getTrainerByName("DemoMike");
        Trainer t4 = MainSystem.trainerManager.getTrainerByName("DemoSarah");
        Trainer t5 = MainSystem.trainerManager.getTrainerByName("DemoDavid");
        Trainer t6 = MainSystem.trainerManager.getTrainerByName("DemoEmma");
        Trainer t7 = MainSystem.trainerManager.getTrainerByName("DemoJames");


        if (t1 == null) {
            System.out.println("Error: Trainers not initialized. Please restart.");
            return;
        }

        // ACTIVITY 1: Dumbbell Curl - 2 time slots, 2 coaches
        Activity a1 = new Activity("Dumbbell Curl", "Basic");
        a1.addSchedule(new Schedule(1, "Dumbbell Curl", "Monday", " 8AM - 10AM", t1, "Basic", 10, 100));
        a1.addSchedule(new Schedule(2, "Dumbbell Curl", "Wednesday", "9AM - 11AM", t2, "Basic", 10, 100));
        MainSystem.activityManager.addActivity(a1);

        // ACTIVITY 2: Bench Press - 2 time slots, 2 coaches
        Activity a2 = new Activity("Bench Press", "Silver");
        a2.addSchedule(new Schedule(3, "Bench Press", "Tuesday", " 8AM - 10AM", t3, "Silver", 8, 150));
        a2.addSchedule(new Schedule(4, "Bench Press", "Thursday", "9AM - 11AM", t4, "Silver", 8, 150));
        MainSystem.activityManager.addActivity(a2);

        // ACTIVITY 3: Squat - 2 time slots, 2 coaches
        Activity a3 = new Activity("Squat", "Gold");
        a3.addSchedule(new Schedule(5, "Squat", "Monday", "10AM - 12PM", t5, "Gold", 5, 200));
        a3.addSchedule(new Schedule(6, "Squat", "Friday", "8AM - 10AM", t6, "Gold", 5, 200));
        MainSystem.activityManager.addActivity(a3);

        // ACTIVITY 4: Deadlift - 2 time slots, 2 coaches
        Activity a4 = new Activity("Deadlift", "Silver");
        a4.addSchedule(new Schedule(7, "Deadlift", "Wednesday", "10AM - 12PM", t7, "Silver", 8, 150));
        a4.addSchedule(new Schedule(8, "Deadlift", "Saturday", "9AM - 11AM", t1, "Silver", 8, 150));
        MainSystem.activityManager.addActivity(a4);

        // ACTIVITY 5: Pull-up - 2 time slots, 2 coaches
        Activity a5 = new Activity("Pull-up", "Basic");
        a5.addSchedule(new Schedule(9, "Pull-up", "Tuesday", "10AM - 12PM", t2, "Basic", 10, 100));
        a5.addSchedule(new Schedule(10, "Pull-up", "Thursday", "8AM - 10AM", t3, "Basic", 10, 100));
        MainSystem.activityManager.addActivity(a5);
    }

    public static void setupActivities() {
        setupDefaultTrainers();
        setupActivitiesOnly();
    }

    //add setup (default)
    public static void setupEquipment(){
        //follow the exercise name to create each equipment,
        //default status is "Available"
        MainSystem.equipmentManager.addEquipment(new Equipment(1, "Dumbbell", "Dumbbell Curl", "Available"));
        MainSystem.equipmentManager.addEquipment(new Equipment(2, "Bench", "Bench Press", "Available"));
        MainSystem.equipmentManager.addEquipment(new Equipment(3, "Squat Rack", "Squat", "Available"));
        MainSystem.equipmentManager.addEquipment(new Equipment(4, "Barbell", "Deadlift", "Available"));
        MainSystem.equipmentManager.addEquipment(new Equipment(5, "Pull-up Bar", "Pull-up", "Available"));
    }

    public static void initAll() {
        MainSystem.trainerManager.loadFromFile("trainers.json");
        if (MainSystem.trainerManager.getCount() == 0) {
            setupActivities();
        } else {
            setupActivitiesOnly();
        }

        MainSystem.equipmentManager.loadFromFile("equipment.json");
        if (MainSystem.equipmentManager.getCount() == 0) {
            setupEquipment();
        }

        MainSystem.manager.loadFromFile("members.json", MainSystem.activityManager);
    }

}
