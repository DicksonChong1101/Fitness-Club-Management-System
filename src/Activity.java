public class Activity {
    private String name;
    private String tier;
    private Schedule[] schedules = new Schedule[5];
    private int scheduleCount = 0;

    public Activity(String name, String tier) {
        this.name = name;
        this.tier = tier;
    }

    public void addSchedule(Schedule s) {
        schedules[scheduleCount++] = s;
    }

    public String getName() {
        return name;
    }

    public String getTier() {
        return tier;
    }

    //trainer name and their tier
    public void display() {
        System.out.printf("%-14s | %-9s | ", name, tier);

        // Display first time slot and trainer
        if(scheduleCount > 0) {
            System.out.printf("%-10s %-12s (%-6s)",
                    schedules[0].getDay(), schedules[0].getTime(), schedules[0].getTrainerName());
        }

        // Display second time slot and trainer
        if(scheduleCount > 1) {
            System.out.printf(" | %-10s %-12s (%-6s)|",
                    schedules[1].getDay(), schedules[1].getTime(), schedules[1].getTrainerName());
        }

        System.out.println();
    }

    public void displayBookingOptions() {
        System.out.println("\n=== " + name + " (" + tier + ") ===");
        for(int i = 0; i < scheduleCount; i++) {
            String status = schedules[i].isFull() ? "FULL" : schedules[i].getCurrentSlot() + "/" + schedules[i].getMaxSlot() + " slots left";
            System.out.println((i+1) + ". " + schedules[i].getDay() + " " + schedules[i].getTime() +
                    " - Trainer: " + schedules[i].getTrainerName() + " [" + status + "]");
        }
        System.out.println("0. Cancel");
    }

    public Schedule getScheduleByOption(int option) {
        if(option >= 1 && option <= scheduleCount) {
            return schedules[option - 1];
        }
        return null;
    }

    public boolean hasAvailableSlot() {
        for(int i = 0; i < scheduleCount; i++) {
            if(!schedules[i].isFull()) {
                return true;
            }
        }
        return false;
    }

    public void printPriceDetails() {
        for (int i = 0; i < scheduleCount; i++) {
            Schedule s = schedules[i];
            String timeSlot = s.getDay() + " " + s.getTime();  // 已无前导空格
            System.out.printf("| %-18s | %-28s | RM %6.2f |\n", name, timeSlot, s.getPrice());
        }
    }

   //new method
    public Schedule[] getSchedules(){
        return schedules;
    }
}