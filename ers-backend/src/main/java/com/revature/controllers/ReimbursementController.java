package com.revature.controllers;

import com.revature.daos.ReimbursementDAO;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reimbs")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReimbursementController {
    private UserService userService;
    private ReimbursementDAO reimbDAO;
    private ReimbursementService reimbService;

    @Autowired

    public ReimbursementController(UserService userService, ReimbursementDAO reimbDAO, ReimbursementService reimbService) {
        this.userService = userService;
        this.reimbDAO = reimbDAO;
        this.reimbService = reimbService;
    }

    // Create a new Reimbursement (Employee User Story)
    @PostMapping()
    public ResponseEntity<String> newReimbursement(@RequestBody IncomingReimbDTO reimbDTO, HttpSession session){
        // login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }

        // attaching the userId from our session
        reimbDTO.setUserId((int) session.getAttribute("userId"));

        Reimbursement reimb = reimbService.addReimb(reimbDTO);

        return ResponseEntity.status(201).body("Reimbursement #" + reimb.getReimbId() + " was created successfully.");
    }

    //  See all of their own Reimbursements (Employee User Story)
    @GetMapping
    public ResponseEntity<?> getReimbs(HttpSession session){
        // login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }

        // extract userId from session
        int userId = (int) session.getAttribute("userId");

        return ResponseEntity.ok(reimbService.getReimbs(userId));
    }

    //See only "PENDING" Reimbursements (Employee User Story)
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReimbs(HttpSession session){
        // login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }

        // extract userId from session
        int userId = (int) session.getAttribute("userId");

        return ResponseEntity.ok(reimbService.getPendingReimbs(userId));
    }

    // See all Reimbursements (Manager User Story)
    @GetMapping("/manager")
    public ResponseEntity<?> getAllReimbs(HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }
        return ResponseEntity.ok(reimbService.getAllReimbs());
    }

    // See all "PENDING" Reimbursements (Manager User Story)
    @GetMapping("/pending/manager")
    public ResponseEntity<?> getAllPending(HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }
        return ResponseEntity.ok(reimbService.getAllPending());
    }

    // Resolve a Reimbursement (Manager User Story)
    @PatchMapping("/{reimbId}")
    public ResponseEntity<?> resolveReimb(@PathVariable int reimbId, @RequestBody String status, HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }
        if(session.getAttribute("role").equals("Employee")){
            return ResponseEntity.status(401).body("Unauthorized Access");
        }
        Reimbursement r = reimbDAO.findById(reimbId).get();

        if((int) session.getAttribute("userId") == r.getUser().getUserId()){
            return ResponseEntity.badRequest().body("You cannot resolve your own request!");
        }

        status = status.split(":")[1].split("}")[0].replaceAll("\"", "");

        OutgoingReimbDTO reimbDTO = reimbService.resolveReimb(reimbId, status);

        return ResponseEntity.ok("Ticket #" + reimbId + " was updated.");
    }

    // Update Reimbursement Description (Employee User Story)
    @PatchMapping("/update/{reimbId}")
    public ResponseEntity<?> updateDescription(@PathVariable int reimbId, @RequestBody String description, HttpSession session){
        // validity checks
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("Please Sign In to use this function.");
        }

        description = description.split(":")[1].split("}")[0].replaceAll("\"", "");

        OutgoingReimbDTO reimbDTO = reimbService.updateDescription(reimbId, description);

        return ResponseEntity.ok("Ticket #" + reimbId + " description was updated.");
    }
}
