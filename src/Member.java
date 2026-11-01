public class Member extends User {
    private String password;
    private String gender;
    private String phoneNumber;
    private String icNumber;
    private Schedule[] mySchedules = new Schedule[10];
    private int scheduleCount = 0;

    public Member(int id, String name, String password, String gender, String phoneNumber, String icNumber) {
        super(id, name);
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.icNumber = icNumber;
    }

    // All required getters (implementing abstract methods from User)
    @Override
    public String getPassword() { return password; }

    @Override
    public String getGender() { return gender; }

    @Override
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public String getIcNumber() { return icNumber; }

    @Override
    public void displayInfo() {
        System.out.println("Member: " + name + " (ID: " + id + ")");
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("IC: " + icNumber);
    }

    // Your existing methods remain the same
    public Schedule[] getMySchedules() { return mySchedules; }

    public void addSchedule(Schedule s) {
        mySchedules[scheduleCount++] = s;
    }

    public void viewMySchedule() {
        System.out.println("\n=== My Schedule ===");
        if (scheduleCount == 0) {
            System.out.println("No bookings yet.");
            return;
        }
        for (int i = 0; i < scheduleCount; i++) {
            System.out.println("Class: " + mySchedules[i].getClassName());
            System.out.println("Time: " + mySchedules[i].getDay() + " " + mySchedules[i].getTime());
            System.out.println("Trainer: " + mySchedules[i].getTrainerName());
            System.out.println("----------------------");
        }
    }

    public void removeSchedule(Schedule s) {
        int index = -1;
        for (int i = 0; i < scheduleCount; i++) {
            if (mySchedules[i] == s) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < scheduleCount - 1; i++) {
                mySchedules[i] = mySchedules[i + 1];
            }
            mySchedules[scheduleCount - 1] = null;
            scheduleCount--;
        }
    }
}