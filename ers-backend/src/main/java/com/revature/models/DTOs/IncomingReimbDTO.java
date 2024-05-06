package com.revature.models.DTOs;

public class IncomingReimbDTO {
    private String description;
    private int amount;
    private int userId;

    public IncomingReimbDTO() {
    }

    public IncomingReimbDTO(String description, int amount, int userId) {
        this.description = description;
        this.amount = amount;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
