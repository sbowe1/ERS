package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "reimbursement")
@Component
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbId;

    private String description;

    private int amount;

    private String status = "PENDING";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    // Constructors
    public Reimbursement() {
    }

    public Reimbursement(String description, int amount, User user) {
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    public Reimbursement(int reimbId, String description, int amount, User user) {
        this.reimbId = reimbId;
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    // Getters and Setters
    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
