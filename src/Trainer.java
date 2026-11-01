public class Trainer extends User {
    private String level;
    private String password;
    private double totalRevenue;
    private String gender;
    private String phoneNumber;
    private String icNumber;
    private Schedule[] schedules = new Schedule[10];
    private int scheduleCount = 0;

    public Trainer(int id, String name, String password, String gender,
                   String phoneNumber, String icNumber, String level) {
        super(id, name);
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.icNumber = icNumber;
        this.level = level;
        this.totalRevenue = 0;
    }

    // Implementing abstract methods from User
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
        System.out.println("Trainer: " + name + " (ID: " + id + ")");
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("IC: " + icNumber);
        System.out.println("Level: " + level);
        System.out.printf("Total Revenue: RM %.2f\n", totalRevenue);
    }

    // Trainer-specific methods
    public String getLevel() { return level; }
    public double getTotalRevenue() { return totalRevenue; }
    public Schedule[] getSchedules() { return schedules; }

    public void setLevel(String level) { this.level = level; }
    public void setPassword(String password) { this.password = password; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setIcNumber(String icNumber) { this.icNumber = icNumber; }
    public void addRevenue(double amount) { this.totalRevenue += amount; }

    public void addSchedule(Schedule s) {
        schedules[scheduleCount++] = s;
    }

    public void viewMyClasses() {
        System.out.println("\n+==============================================================================+");
        System.out.printf("|                          Trainer: %-10s (%-6s)                        |\n", name, level);
        System.out.println("+==============================================================================+");
        if (scheduleCount == 0) {
            System.out.println("|   No classes assigned.                                                      |");
            System.out.println("+==============================================================================+");
            return;
        }
        for (int i = 0; i < scheduleCount; i++) {
            Schedule s = schedules[i];
            System.out.println("  Class:     " + s.getClassName() + " | " + s.getDay() + " " + s.getTime() + " | Max: " + s.getMaxSlot());
            System.out.println("+==============================================================================+");
            System.out.printf("| %-5s | %-12s | %-6s | %-44s |\n", "ID", "Name", "Gender", "Phone");
            System.out.println("|-------+--------------+--------+----------------------------------------------|");
            s.showStudentsDetailed();
            System.out.println("+==============================================================================+");
        }
    }
}