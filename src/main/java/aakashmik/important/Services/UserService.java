package aakashmik.important.Services;


import aakashmik.important.Entities.Users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    Users user;


    public ObjectMapper objectMapper = new ObjectMapper();


    public List<Users> getUsers() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contacts.json");
        if (inputStream == null) {
            System.out.println("file not found");
            return new ArrayList<>();
        }
        System.out.println("file exists");
        return objectMapper.readValue(inputStream, new TypeReference<List<Users>>() {

        });
    }

    public List<Users> getUnverifiedUsers() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("src/main/data/unverified.json");
        if (inputStream == null) {
            System.out.println("file not found");
            return new ArrayList<>();
        }
        System.out.println("unverified file exists");
        return objectMapper.readValue(inputStream, new TypeReference<List<Users>>() {

        });
    }

    // Calculate distance between two coordinates using Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }





   public List<Users> getUsersByLocation(String id, double radius) throws IOException {

        try{
            String searchId = id.trim().toLowerCase();

            int index = searchId.indexOf("&&");
            String[] coordinates = searchId.split("&&");

            String Longi = coordinates[0];
            String Lati = coordinates[1];




            return getUsers().stream()
                    .filter(location -> calculateDistance(Double.parseDouble(Lati),Double.parseDouble(Longi), location.getLatitude(), location.getLongitude()) <= radius)
                    .collect(Collectors.toList());
        }
        catch(Exception e){
            System.out.println("error while fetching by location" + e);
            return new ArrayList<>();
        }



   }




    public List<Users> getUsersById(String id) throws IOException {

        String searchId = id.trim().toLowerCase();


            try {


                List<Users> matchedUsers = getUsers().stream()
                        .filter(user -> (user.getName() != null && user.getName().toLowerCase().contains(searchId)) ||
                                (user.getDistrict() != null && user.getDistrict().toLowerCase().contains(searchId)) ||
                                (user.getCity() != null && user.getCity().toLowerCase().contains(searchId)))
                        .collect(Collectors.toList());

                System.out.println("Success fetched by id");
                return matchedUsers.isEmpty() ? null : matchedUsers;  // Return null if no match
            } catch (Exception e) {
                System.out.println("Error detected: " + e);
                return new ArrayList<>(); // Or handle the exception as needed
            }
    }


    public String PostUnverified(Users user) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("name", user.getName());
        obj.put("number", user.getNumber());
        obj.put("category", user.getCategory());
        obj.put("province", user.getProvince());
        obj.put("district", user.getDistrict());
        obj.put("city", user.getCity());
        obj.put("latitude", user.getLatitude());
        obj.put("longitude", user.getLongitude());

        // File path: relative path to the 'data' folder
        File file = new File("data/HandleUnVerified.json");

        // Ensure the directory exists
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // If the file doesn't exist, create it with an empty array
        if (!file.exists()) {
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("[\n]"); // Create an empty JSON array
                fileWriter.flush();
            }
        }

        // Read the existing JSON array from the file
        List<JSONObject> jsonArray = readJsonArrayFromFile(file);

        // Append the new object to the existing array
        jsonArray.add(obj);

        // Write the updated JSON array back to the file
        writeJsonArrayToFile(file, jsonArray);

        return "success";
    }

    private List<JSONObject> readJsonArrayFromFile(File file) throws IOException {
        // Initialize an empty list if the file is empty or contains no valid JSON
        List<JSONObject> jsonArray = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            jsonArray = objectMapper.readValue(fileReader, new TypeReference<List<JSONObject>>() {});
            System.out.println("testing"+jsonArray);
        } catch (Exception e) {
            // Handle the case where file is empty or parsing fails (fallback to empty list)
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void writeJsonArrayToFile(File file, List<JSONObject> jsonArray) throws IOException {
        // Write the updated JSON array back to the file
        try (FileWriter fileWriter = new FileWriter(file)) {
            System.out.println("Absolute path of created file: " + file.getAbsolutePath());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, jsonArray);
        }
    }


}





//    @Autowired
//    private UserDao userDao;
//
//    public List<Users> getUsers(){
//
//        return userDao.getUsers();
//    }
//
//    public Users getUser(int id){
//        return userDao.getUser(id);
//    }
//
//    public int addUser(Users user){
//        return (int) userDao.addUser(user);
//    }


