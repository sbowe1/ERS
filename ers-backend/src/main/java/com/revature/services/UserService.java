package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // create new user
    public User registerUser(IncomingUserDTO userDTO){
        // Check the name, username, and password are not empty/null
        if(userDTO.getFirstName().isBlank() || userDTO.getFirstName().equals(null)){
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if(userDTO.getLastName().isBlank() || userDTO.getLastName().equals(null)){
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if(userDTO.getUsername().isBlank() || userDTO.getUsername().equals(null)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if(userDTO.getPassword().isBlank() || userDTO.getPassword().equals(null)) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        User newUser = new User(userDTO.getUsername(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getPassword());

        return userDAO.save(newUser);
    }

    // login
    public Optional<User> loginUser(IncomingUserDTO userDTO){
        //TODO: validity checks

        return userDAO.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
    }

    // get all users (manager story)
    public List<OutgoingUserDTO> getAllUsers(){
        List<User> allUsers = userDAO.findAll();

        List<OutgoingUserDTO> outUsers = new ArrayList<>();
        for(User u : allUsers){
            OutgoingUserDTO outUser = new OutgoingUserDTO(
                    u.getUserId(), u.getFirstName(), u.getLastName(), u.getUsername(), u.getRole()
            );
            outUsers.add(outUser);
        }

        return outUsers;
    }

    // delete a user (manager story)
    public void deleteUser(int userId){
        userDAO.deleteById(userId);
    }

    // promote an employee (manager story)
    public void promoteUser(int userId){
        User u = userDAO.findById(userId).get();
        u.setRole("Manager");

        userDAO.save(u);
    }

    // update username or password (employee story)
    public void updateProfile(int userId, String attribute, String newValue){
        User u = userDAO.findById(userId).get();

        // update username
        if(attribute.equals("username")){
            u.setUsername(newValue);
            userDAO.save(u);
        }
        if(attribute.equals("password")){
            u.setPassword(newValue);
            userDAO.save(u);
        }
    }
}
