package aakashmik.important.Services;


import aakashmik.important.Entities.Users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


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


