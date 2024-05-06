package com.revature.daos;

import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
    public List<Reimbursement> findAllByUserUserId(int userId);

    public List<Reimbursement> findAllByStatus(String status);

    public List<Reimbursement> findAllByStatusAndUserUserId(String status, int userId);
}
