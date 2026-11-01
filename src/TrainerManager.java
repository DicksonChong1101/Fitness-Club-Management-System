import java.io.*;

public class TrainerManager {

    private Trainer[] trainers = new Trainer[10];
    private int count = 0;
    private int nextTrainerId = 2001;

    public Trainer[] getAllTrainers() {
        return trainers;
    }

    public void addTrainer(Trainer t){
        try {
            trainers[count++] = t;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Trainer limit reached! ");
        }
    }

    public Trainer getTrainerByName(String name){
        try {
            for(int i = 0; i < count; i++){
                if(trainers[i].getName().equalsIgnoreCase(name)){
                    return trainers[i];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Accessing trainer limit are reached! " );
        } catch (NullPointerException e) {
            System.out.println("Error: Trainer or name is null ");
        }
        return null;
    }

    public int getCount() { return count; }

    //Trainer Login **
    public Trainer login(String name, String password){
        for(int i = 0; i < count; i++){
            if (trainers[i].getName().equals(name) &&
                    trainers[i].getPassword().equals(password)){
                return trainers[i];
            }
        }
        return null;
    }

    //Trainer Register
    public void registerTrainer(String name, String password, String gender,
                                String phoneNumber, String icNumber, String level){
        Trainer newTrainer = new Trainer(nextTrainerId, name, password, gender,
                phoneNumber, icNumber, level);

        addTrainer(newTrainer);
        System.out.println("\n=== Registration Successful ===");
        System.out.println("Your Trainer ID: " + nextTrainerId);
        newTrainer.displayInfo();
        nextTrainerId++;
    }

    //delete trainer account (choice) - no use
    public boolean deleteTrainer(int id) {
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (trainers[i].getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;
        for (int i = index; i < count - 1; i++) {
            trainers[i] = trainers[i + 1];
        }
        trainers[count - 1] = null;
        count--;
        return true;
    }


    public void showAllTrainers(){
        try {
            for(int i = 0; i < count; i++){
                System.out.println(trainers[i].getName() + " (" + trainers[i].getLevel() + ")");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array error showing trainers: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error: Trainer data is null");
        }
    }

    public void saveToFile(String filename) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < count; i++) {
            Trainer t = trainers[i];
            json.append("  {\n");
            json.append("    \"id\": ").append(t.getId()).append(",\n");
            json.append("    \"name\": \"").append(escapeJson(t.getName())).append("\",\n");
            json.append("    \"password\": \"").append(escapeJson(t.getPassword())).append("\",\n");
            json.append("    \"gender\": \"").append(escapeJson(t.getGender())).append("\",\n");
            json.append("    \"phoneNumber\": \"").append(escapeJson(t.getPhoneNumber())).append("\",\n");
            json.append("    \"icNumber\": \"").append(escapeJson(t.getIcNumber())).append("\",\n");
            json.append("    \"level\": \"").append(escapeJson(t.getLevel())).append("\",\n");
            json.append("    \"totalRevenue\": ").append(t.getTotalRevenue()).append("\n");
            json.append("  }");
            if (i < count - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString());
            System.out.println("Trainer data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving trainer data: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No saved trainer data found. Using default trainers.");
            return;
        }
        String content;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            content = sb.toString();
        } catch (IOException e) {
            System.out.println("Error reading trainer file: " + e.getMessage());
            return;
        }

        content = content.trim();
        if (!content.startsWith("[") || !content.endsWith("]")) return;
        String body = content.substring(1, content.length() - 1).trim();
        if (body.isEmpty()) return;

        String[] objStrs = splitJsonObjects(body);
        for (String objStr : objStrs) {
            Trainer t = parseTrainerFromJson(objStr);
            if (t != null && count < trainers.length) {
                trainers[count++] = t;
                if (t.getId() >= nextTrainerId) nextTrainerId = t.getId() + 1;
            }
        }
        System.out.println("Loaded " + count + " trainers from " + filename);
    }

    private String[] splitJsonObjects(String body) {
        java.util.List<String> list = new java.util.ArrayList<>();
        int braceLevel = 0;
        int start = 0;
        for (int i = 0; i < body.length(); i++) {
            char c = body.charAt(i);
            if (c == '{') braceLevel++;
            else if (c == '}') braceLevel--;
            if (braceLevel == 0 && i > start && body.charAt(i) == ',') {
                list.add(body.substring(start, i).trim());
                start = i + 1;
            }
        }
        if (start < body.length()) list.add(body.substring(start).trim());
        return list.toArray(new String[0]);
    }

    private Trainer parseTrainerFromJson(String objStr) {
        int id = extractInt(objStr, "\"id\":");
        String name = extractString(objStr, "\"name\":");
        String password = extractString(objStr, "\"password\":");
        String gender = extractString(objStr, "\"gender\":");
        String phone = extractString(objStr, "\"phoneNumber\":");
        String ic = extractString(objStr, "\"icNumber\":");
        String level = extractString(objStr, "\"level\":");
        double revenue = extractDouble(objStr, "\"totalRevenue\":");
        if (id == -1 || name == null) return null;
        Trainer t = new Trainer(id, name, password, gender, phone, ic, level);
        t.addRevenue(revenue); //recover total revenue
        return t;
    }

    private String extractString(String json, String key) {
        int keyIdx = json.indexOf(key);
        if (keyIdx == -1) return null;
        int startQuote = json.indexOf("\"", keyIdx + key.length());
        if (startQuote == -1) return null;
        int endQuote = json.indexOf("\"", startQuote + 1);
        if (endQuote == -1) return null;
        return json.substring(startQuote + 1, endQuote);
    }

    private int extractInt(String json, String key) {
        int keyIdx = json.indexOf(key);
        if (keyIdx == -1) return -1;
        int startVal = keyIdx + key.length();
        while (startVal < json.length() && Character.isWhitespace(json.charAt(startVal))) startVal++;
        int endVal = startVal;
        while (endVal < json.length() && (Character.isDigit(json.charAt(endVal)) || json.charAt(endVal) == '-')) endVal++;
        if (endVal == startVal) return -1;
        try {
            return Integer.parseInt(json.substring(startVal, endVal));
        } catch (NumberFormatException e) { return -1; }
    }

    private double extractDouble(String json, String key) {
        int keyIdx = json.indexOf(key);
        if (keyIdx == -1) return 0.0;
        int startVal = keyIdx + key.length();
        while (startVal < json.length() && Character.isWhitespace(json.charAt(startVal))) startVal++;
        int endVal = startVal;
        while (endVal < json.length() && (Character.isDigit(json.charAt(endVal)) || json.charAt(endVal) == '.' || json.charAt(endVal) == '-')) endVal++;
        if (endVal == startVal) return 0.0;
        try {
            return Double.parseDouble(json.substring(startVal, endVal));
        } catch (NumberFormatException e) { return 0.0; }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}