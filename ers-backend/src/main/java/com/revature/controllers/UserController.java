package com.revature.controllers;

import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody IncomingUserDTO userDTO, HttpSession session){
        Optional<User> optionalUser = userService.loginUser(userDTO);

        if(optionalUser.isEmpty()){
            return ResponseEntity.status(401).body("Login Failed!");
        }

        // if successful, store the use info in our session
        User u = optionalUser.get();
        session.setAttribute("userId", u.getUserId());
        session.setAttribute("username", u.getUsername());
        session.setAttribute("role", u.getRole());

        return  ResponseEntity.ok(new LoginDTO(u.getUserId(), u.getUsername(), u.getRole()));
    }

    // Insert a User
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody IncomingUserDTO userDTO){
        try{
            userService.registerUser(userDTO);
            return ResponseEntity.status(201).body("User " + userDTO.getUsername() + " was created successfully.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // See all Users (Manager User Story)
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session){
        // login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }
        List<OutgoingUserDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    // Delete a User (Manager User Story)
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId, HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }
        if((int) session.getAttribute("userId") == userId){
            return ResponseEntity.badRequest().body("You cannot remove yourself!");
        }

        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok("User was deleted!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Promote a User (Manager User Story)
    @PatchMapping("/{userId}")
    public ResponseEntity<?> promoteUser(@PathVariable int userId, HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }

        try{
            userService.promoteUser(userId);
            return ResponseEntity.ok("User was promoted!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update Profile Username or Password (Employee User Story)
    @PatchMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable int userId, HttpSession session, @RequestBody String input){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if((int) session.getAttribute("userId") != userId){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }

        String attribute = input.split(":")[0].replaceAll("\"", "").substring(1).trim();
        String newValue = input.split(":")[1].split("}")[0].replaceAll("\"", "").trim();

        try{
            userService.updateProfile(userId, attribute, newValue);
            return ResponseEntity.ok("User's " + attribute + " was updated successfully!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
