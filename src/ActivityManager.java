public class ActivityManager {
    private Activity[] activities = new Activity[10];
    private int count = 0;

    public void addActivity(Activity a) {
        activities[count++] = a;
    }

    public int getActivityCount() {
        return count;
    }

    public void showAllActivities() {
        if(count == 0) {
            System.out.println("No activities available!");
            return;
        }

        System.out.println("\n+===================================================================================================+");
        System.out.println("|No. Activity      | Tier      | Time Slot 1                      | Time Slot 2                     |");
        System.out.println("|---------------------------------------------------------------------------------------------------+");
        for(int i = 0; i < count; i++) {
            System.out.print("|"+(i+1) + ". ");
            activities[i].display();
        }
        System.out.println("|---------------------------------------------------------------------------------------------------|");
        System.out.println("+===================================================================================================+\n");

        showPriceTable();
    }

    public void showPriceTable() {
        if (count == 0) return;
        System.out.println("\n+===============================================================+");
        System.out.println("|                            PRICE LIST                         |");
        System.out.println("+===============================================================+");
        System.out.printf("| %-18s | %-28s |%8s   |\n", "Activity", "Time Slot","Price");
        System.out.println("|--------------------+------------------------------+-----------|");
        for (int i = 0; i < count; i++) {
            activities[i].printPriceDetails();
        }
        System.out.println("+===============================================================+");
    }

    public Activity getActivityByNumber(int num) {
        if(num >= 1 && num <= count) {
            return activities[num - 1];
        }
        return null;
    }

    public Activity[] getAllActivities() {
        return activities;
    }

    public Schedule getScheduleById(int scheduleId) {
        for (int i = 0; i < count; i++) {
            Activity a = activities[i];
            if (a == null) continue;
            // need to ger Activity inside the  schedules array，from Activity add getSchedules method
            Schedule[] scheds = a.getSchedules();  //from Activity
            for (Schedule s : scheds) {
                if (s != null && s.getId() == scheduleId) return s;
            }
        }
        return null;
    }
}