import java.io.*;
import java.util.*;

public class MemberManager {
    Member [] members = new Member[100];
    int memberCount = 0;
    int nextID = 1001;

    //Register - Updated
    public void register (String name, String pass, String gender, String phoneNumber, String icNumber){

        Member newMember = new Member(nextID, name, pass, gender, phoneNumber, icNumber);

        members[memberCount] = newMember;

        System.out.println("Register successful! ");
        System.out.println("Your ID: " + nextID);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("IC: " + icNumber);

        memberCount++;
        nextID++;
    }

    //Login
    public Member login(String name, String pass){
        for(int i = 0; i < memberCount; i++){

            if(members[i].getName().equals(name) &&
                    members[i].getPassword().equals(pass)){

                return members[i];
            }

        }
        return null;
    }

    //Show members function
    public void showMembers(){

        if(memberCount == 0){
            System.out.println("No members.");
            return;
        }

        System.out.println("\n=== Member List ===");

        for(int i = 0; i < memberCount; i++){
            if(members[i] != null){
                System.out.println("ID: " + members[i].getId()
                        + " | Name: " + members[i].getName()
                        + " | Gender: " + members[i].getGender()
                        + " | Phone: " + members[i].getPhoneNumber()
                        + " | IC: " + members[i].getIcNumber());
            }
        }
    }

    //Delete
    public boolean deleteMember(int id){

        int index = -1;

        for(int i = 0; i < memberCount; i++){
            if (members[i].getId() == id){
                index = i;
                break;
            }
        }

        if (index == -1){
            return false;
        }

        for(int i = index; i < memberCount - 1; i++){
            members[i] = members[i + 1];
        }

        memberCount--;
        return true;
    }

    //Save all the members to the JSON file
    public void saveToFile(String filename) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < memberCount; i++) {
            Member m = members[i];
            json.append("  {\n");
            json.append("    \"id\": ").append(m.getId()).append(",\n");
            json.append("    \"name\": \"").append(escapeJson(m.getName())).append("\",\n");
            json.append("    \"password\": \"").append(escapeJson(m.getPassword())).append("\",\n");
            json.append("    \"gender\": \"").append(escapeJson(m.getGender())).append("\",\n");
            json.append("    \"phoneNumber\": \"").append(escapeJson(m.getPhoneNumber())).append("\",\n");
            json.append("    \"icNumber\": \"").append(escapeJson(m.getIcNumber())).append("\",\n");
            // 保存预订的课程 ID 列表
            json.append("    \"bookedScheduleIds\": [");
            Schedule[] myScheds = m.getMySchedules();
            List<Integer> ids = new ArrayList<>();
            for (Schedule s : myScheds) {
                if (s != null) ids.add(s.getId());
            }
            for (int j = 0; j < ids.size(); j++) {
                json.append(ids.get(j));
                if (j < ids.size() - 1) json.append(", ");
            }
            json.append("]\n");
            json.append("  }");
            if (i < memberCount - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString());
            System.out.println("Member data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving member data: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename, ActivityManager actManager) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No saved member data found. Starting fresh.");
            return;
        }
        String content;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            content = sb.toString();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        content = content.trim();
        if (!content.startsWith("[") || !content.endsWith("]")) return;
        String body = content.substring(1, content.length() - 1).trim();
        if (body.isEmpty()) return;

        String[] objStrs = splitJsonObjects(body);
        for (String objStr : objStrs) {
            Member m = parseMemberFromJson(objStr, actManager);
            if (m != null && memberCount < members.length) {
                members[memberCount++] = m;
                if (m.getId() >= nextID) nextID = m.getId() + 1;
            }
        }
        System.out.println("Loaded " + memberCount + " members from " + filename);
    }

    private String[] splitJsonObjects(String body) {
        List<String> list = new ArrayList<>();
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

    private Member parseMemberFromJson(String objStr, ActivityManager actManager) {
        int id = extractInt(objStr, "\"id\":");
        String name = extractString(objStr, "\"name\":");
        String password = extractString(objStr, "\"password\":");
        String gender = extractString(objStr, "\"gender\":");
        String phone = extractString(objStr, "\"phoneNumber\":");
        String ic = extractString(objStr, "\"icNumber\":");
        if (id == -1 || name == null) return null;
        Member m = new Member(id, name, password, gender, phone, ic);
        // 恢复预订记录
        int[] bookedIds = extractIntArray(objStr, "\"bookedScheduleIds\":");
        for (int sid : bookedIds) {
            Schedule s = actManager.getScheduleById(sid);
            if (s != null) {
                m.addSchedule(s);
                s.bookSlotDirectly(m);
            }
        }
        return m;
    }

    private int[] extractIntArray(String json, String key) {
        int keyIdx = json.indexOf(key);
        if (keyIdx == -1) return new int[0];
        int startBracket = json.indexOf("[", keyIdx);
        if (startBracket == -1) return new int[0];
        int endBracket = json.indexOf("]", startBracket);
        if (endBracket == -1) return new int[0];
        String arrayContent = json.substring(startBracket + 1, endBracket);
        String[] parts = arrayContent.split(",");
        List<Integer> list = new ArrayList<>();
        for (String p : parts) {
            p = p.trim();
            if (!p.isEmpty()) {
                try {
                    list.add(Integer.parseInt(p));
                } catch (NumberFormatException e) {}
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);
        return res;
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

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}