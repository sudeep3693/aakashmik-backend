package aakashmik.important.Controllers;

import aakashmik.important.Entities.Users;
import aakashmik.important.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class HomeController {

//    @Autowired
//    UserService userService;
//
//
//    Users user1 = new Users("Sudeep","Pokhara",19);
//    Users user2 = new Users("Sagar","Baglung",20);
//    Users user3 = new Users("Susmita","Kathmandu",16);
//    @GetMapping("/users")
//    public List<Users> getAllUsers(){
//        return userService.getUsers();
//
//    }
//
//    @GetMapping("/adduser")
//    public String postUser(){
//        int id1 = userService.addUser(user1);
//        int id2 =userService.addUser(user2);
//        int id3 = userService.addUser(user3);
//
//        return "Added uses with id as below"+id1+" "+id2+" "+id3;
//    }

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<Users> getUsers(){
    System.out.println("inside home container");
        List<Users> userlist;
        try {
            userlist = userService.getUsers();
        }
        catch (Exception e){
            System.out.println(e +" error found");
            userlist = null;
        }
        return userlist;
    }


    @GetMapping("/users/{id}")
    public List<Users> getById(@PathVariable("id") String id){

        List<Users> usersList;
        try{
            usersList = userService.getUsersById(id);
        }
        catch (Exception e){
            System.out.println(e +" error found");
            usersList = null;
        }
        System.out.println("by id successful");
        return usersList;
    }

    //Location Wise
    @GetMapping("/users/location/{id}")
    public List<Users> getByLocation(@PathVariable("id") String id){

        List<Users> usersList;
        try{
            usersList = userService.getUsersByLocation(id,10.10d);
        }
        catch (Exception e){
            System.out.println(e +" error found in location fetching");
            usersList = null;
        }
        System.out.println("by Location successful");
        return usersList;
    }

    @PostMapping("/postDetailUnverified")
    public ResponseEntity<String> postDetail(@RequestBody Users data) {
        try {
            System.out.println(data);
            String result = userService.PostUnverified(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e + " error found");
            return new ResponseEntity<>("Failed to process request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
