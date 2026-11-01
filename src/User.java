public abstract class User {
    protected int id;
    protected String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    // Abstract methods that subclasses must implement
    public abstract void displayInfo();
    public abstract String getPassword();
    public abstract String getGender();
    public abstract String getPhoneNumber();
    public abstract String getIcNumber();
}
