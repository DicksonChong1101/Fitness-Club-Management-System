public class Schedule {
    private int id;
    private String className;
    private String day;
    private String time;
    private Trainer trainer;
    private String tier;
    private int maxSlot;
    private int currentSlot;
    private Member[] members = new Member[10];
    private int memberCount = 0;
    private double price;



    public Schedule(int id, String className, String day, String time, Trainer trainer, String tier, int maxSlot
    , double price){
        this.id = id;
        this.className = className;
        this.day = day;
        this.time = time;
        this.trainer = trainer;
        this.tier = tier;
        this.maxSlot = maxSlot;
        this.currentSlot = 0;
        this.price = price;
        trainer.addSchedule(this);
    }

    // Getters
    public int getId() { return id; }
    public String getClassName() { return className; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getTrainerName() { return trainer.getName(); }
    public Trainer getTrainer() { return trainer; }
    public String getTier() { return tier; }
    public int getMaxSlot() { return maxSlot; }
    public int getCurrentSlot() { return currentSlot; }
    public double getPrice(){return price;}

    //get the array for member that already booking
    public Member[] getMembers(){
        return members;
    }

    //get the number of already booking member
    public int getMemberCount(){
        return memberCount;
    }



    public boolean isFull() { return currentSlot >= maxSlot; }

    public String getStatus() {
        if(isFull()) return "Full";
        return currentSlot + "/" + maxSlot;
    }

    public boolean bookSlot(Member m){
        // Check if member already booked this specific class
        for(int i = 0; i < memberCount; i++){
            if(members[i].getId() == m.getId()){
                System.out.println("You already booked this class!");
                return false;
            }
        }

        if (!isFull()){
            members[memberCount++] = m;
            currentSlot++;
            return true;
        } else {
            System.out.println("This class is full!");
            return false;
        }
    }

    //Checking
    public boolean isAlreadyBooked(Member m) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getId() == m.getId()) {
                return true;
            }
        }
        return false;
    }

    //member cancel booking
    public boolean cancelBooking(Member m){
        int index = -1;
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getId() == m.getId()) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;  //this for haven't fine member
        // delete the member, and move up
        for (int i = index; i < memberCount - 1; i++) {
            members[i] = members[i + 1];
        }
        members[memberCount - 1] = null;
        memberCount--;
        currentSlot--;
        return true;
    }


    public void display(){
        System.out.printf("%-3d| %-12s| %-10s| %-8s| %-10s| %-6s| %-6s\n",
                id, className, day, time, trainer.getName(), tier, getStatus());
    }

    public void showStudentsDetailed() {
        if (memberCount == 0) {
            System.out.println("|   No students yet.                                                           |");
            return;
        }
        for (int i = 0; i < memberCount; i++) {
            Member m = members[i];
            System.out.printf("| %-5d | %-12s | %-6s | %-15s                              |\n",
                    m.getId(),
                    truncate(m.getName(), 12),
                    m.getGender(),
                    m.getPhoneNumber());
        }
    }

    //support method, to avoid to long string (table)
    private String truncate(String str, int maxLen) {
        if (str == null) return "";
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }

    //method for reply to reservation while loading
    public void bookSlotDirectly(Member m) {

        //to avoid repeat add booking
        for (int i = 0; i < memberCount; i++){
            if (members[i].getId() == m.getId())
                return;
        }
        if (memberCount < members.length) {
            members[memberCount++] = m;
            currentSlot++;
        }
    }


}