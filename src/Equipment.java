public class Equipment {
    private int id;
    private String name; // For equipment name
    private String relatedActivity; //For exercise name
    private String status; // Available or Under Maintenance

    public Equipment(int id, String name, String relatedActivity, String status){
        this.id = id;
        this.name = name;
        this.relatedActivity = relatedActivity;
        this.status = status;
    }

    //Getter
    public int getId(){return id;}
    public String getName(){return name;}
    public String getRelatedActivity(){return relatedActivity;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}

    public void display(){
        System.out.printf("| %-3d | %-18s | %-22s | %-17s |\n",
                                id, name, relatedActivity, status);
    }

}
