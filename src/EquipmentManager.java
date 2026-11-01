import java.io.*;

public class EquipmentManager {
    private Equipment[] equipments;
    private int count;

    public int getCount(){
        return count;
    }


    public EquipmentManager(int maxSize) {
        equipments = new Equipment[maxSize];
        count = 0;
    }

    public void addEquipment(Equipment e) {
        if (count < equipments.length) {
            equipments[count++] = e;
        }
    }

    // only for display the equipment status table
    public void showEquipmentTable() {
        if (count == 0) {
            System.out.println("No equipment found.");
            return;
        }
        System.out.println("\n+=======================================================================+");
        System.out.println("|                          EQUIPMENT STATUS                             |");
        System.out.println("+=======================================================================+");
        System.out.printf("| %-3s | %-18s | %-22s | %-17s |\n", "ID", "Equipment", "Related Activity", "Status");
        System.out.println("|-----+--------------------+------------------------+-------------------|");
        for (int i = 0; i < count; i++) {
            equipments[i].display();
        }
        System.out.println("+=======================================================================+");
    }

    // follow equipment id to updates equipment status
    public boolean updateEquipmentStatus(int id, String newStatus) {
        for (int i = 0; i < count; i++) {
            if (equipments[i].getId() == id) {
                equipments[i].setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    // collect all equipment (use for trainer choose)
    public void listEquipmentForUpdate() {
        System.out.println("\n--- Equipment List ---");
        for (int i = 0; i < count; i++) {
            System.out.println(equipments[i].getId() + ". " + equipments[i].getName() +
                    " (" + equipments[i].getRelatedActivity() + ") - Current: " + equipments[i].getStatus());
        }
    }

    //JSON Saving data:
    public void saveToFile(String filename) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < count; i++) {
            Equipment e = equipments[i];
            json.append("  {\n");
            json.append("    \"id\": ").append(e.getId()).append(",\n");
            json.append("    \"name\": \"").append(escapeJson(e.getName())).append("\",\n");
            json.append("    \"relatedActivity\": \"").append(escapeJson(e.getRelatedActivity())).append("\",\n");
            json.append("    \"status\": \"").append(escapeJson(e.getStatus())).append("\"\n");
            json.append("  }");
            if (i < count - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString());
            System.out.println("Equipment data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving equipment data: " + e.getMessage());
        }
    }

    //
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No saved equipment data found. Using default equipment.");
            return;
        }
        String content;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            content = sb.toString();
        } catch (IOException e) {
            System.out.println("Error reading equipment file: " + e.getMessage());
            return;
        }

        content = content.trim();
        if (!content.startsWith("[") || !content.endsWith("]")) return;
        String body = content.substring(1, content.length() - 1).trim();
        if (body.isEmpty()) return;

        String[] objStrs = splitJsonObjects(body);

        equipments = new Equipment[20];
        count = 0;
        for (String objStr : objStrs) {
            Equipment e = parseEquipmentFromJson(objStr);
            if (e != null && count < equipments.length) {
                equipments[count++] = e;
            }
        }
        System.out.println("Loaded " + count + " equipment items from " + filename);
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


    private Equipment parseEquipmentFromJson(String objStr) {
        int id = extractInt(objStr, "\"id\":");
        String name = extractString(objStr, "\"name\":");
        String relatedActivity = extractString(objStr, "\"relatedActivity\":");
        String status = extractString(objStr, "\"status\":");
        if (id == -1 || name == null) return null;
        return new Equipment(id, name, relatedActivity, status);
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
        while (endVal < json.length() && Character.isDigit(json.charAt(endVal))) endVal++;
        if (endVal == startVal) return -1;
        try {
            return Integer.parseInt(json.substring(startVal, endVal));
        } catch (NumberFormatException e) { return -1; }
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