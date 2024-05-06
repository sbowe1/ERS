package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.UserDAO;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementService {
    private ReimbursementDAO reimbDAO;
    private UserDAO userDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbDAO, UserDAO userDAO) {
        this.reimbDAO = reimbDAO;
        this.userDAO = userDAO;
    }


    // create new reimbursement
    public Reimbursement addReimb(IncomingReimbDTO reimbDTO){
        Reimbursement reimb = new Reimbursement(reimbDTO.getDescription(), reimbDTO.getAmount(), null);

        // adding corresponding user
        User user = userDAO.findById(reimbDTO.getUserId()).get();
        reimb.setUser(user);

        return reimbDAO.save(reimb);
    }

    // get all of your own reimbursements
    public List<OutgoingReimbDTO> getReimbs(int userId){
        // get all from DB
        List<Reimbursement> allReimbs = reimbDAO.findAllByUserUserId(userId);

        // create a new OutgoingReimbDTO for every item received
        List<OutgoingReimbDTO> outReimbs = new ArrayList<>();
        for(Reimbursement r : allReimbs){
            OutgoingReimbDTO outReimb = new OutgoingReimbDTO(
                    r.getReimbId(), r.getDescription(), r.getAmount(), r.getStatus(), r.getUser().getUserId());

            outReimbs.add(outReimb);
        }

        return outReimbs;
    }

    // get all your "PENDING" reimbursements
    public List<OutgoingReimbDTO> getPendingReimbs(int userId){
        List<Reimbursement> allReimbs = reimbDAO.findAllByStatusAndUserUserId("PENDING", userId);

        List<OutgoingReimbDTO> outReimbs = new ArrayList<>();
        for(Reimbursement r : allReimbs){
            OutgoingReimbDTO outReimb = new OutgoingReimbDTO(
                    r.getReimbId(), r.getDescription(), r.getAmount(), r.getStatus(), r.getUser().getUserId());

            outReimbs.add(outReimb);
        }

        return outReimbs;
    }

    // get all reimbursements (manager story)
    public List<OutgoingReimbDTO> getAllReimbs(){
        List<Reimbursement> allReimbs = reimbDAO.findAll();

        List<OutgoingReimbDTO> outReimbs = new ArrayList<>();
        for(Reimbursement r : allReimbs){
            OutgoingReimbDTO outReimb = new OutgoingReimbDTO(
                    r.getReimbId(), r.getDescription(), r.getAmount(), r.getStatus(), r.getUser().getUserId()
            );
            outReimbs.add(outReimb);
        }
        return outReimbs;
    }

    // get all pending reimbursements (manager story)
    public List<OutgoingReimbDTO> getAllPending(){
        List<Reimbursement> allReimbs = reimbDAO.findAllByStatus("PENDING");

        List<OutgoingReimbDTO> outReimbs = new ArrayList<>();
        for(Reimbursement r : allReimbs){
            OutgoingReimbDTO outReimb = new OutgoingReimbDTO(
                    r.getReimbId(), r.getDescription(), r.getAmount(), r.getStatus(), r.getUser().getUserId()
            );
            outReimbs.add(outReimb);
        }
        return outReimbs;
    }

    // resolve reimbursement (manager story)
    public OutgoingReimbDTO resolveReimb(int reimbId, String status){
        Reimbursement oldReimb = reimbDAO.findById(reimbId).get();

        oldReimb.setStatus(status);
        reimbDAO.save(oldReimb);

        OutgoingReimbDTO updatedReimb = new OutgoingReimbDTO(
                oldReimb.getReimbId(), oldReimb.getDescription(), oldReimb.getAmount(), oldReimb.getStatus(),
                oldReimb.getUser().getUserId()
        );

        return updatedReimb;
    }

    // update reimbursement description (employee story)
    public OutgoingReimbDTO updateDescription(int reimbId, String description){
        Reimbursement oldReimb = reimbDAO.findById(reimbId).get();

        oldReimb.setDescription(description);
        reimbDAO.save(oldReimb);

        OutgoingReimbDTO updatedReimb = new OutgoingReimbDTO(
                oldReimb.getReimbId(), oldReimb.getDescription(), oldReimb.getAmount(), oldReimb.getStatus(),
                oldReimb.getUser().getUserId()
        );

        return updatedReimb;
    }
}
