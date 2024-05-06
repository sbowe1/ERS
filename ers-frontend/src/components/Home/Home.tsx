import React from "react";
import './Home.css'
import { useNavigate } from "react-router-dom";
import { state } from "../globalStore/Stores"

export const Home: React.FC = () => {




    const navigate = useNavigate()

    return(
        <div className="home">
            <div className="home-container">
                <button className="home-button" id="logout" onClick={() => navigate("/")}>Logout</button>
                <br /><br />
                
                <h3>Welcome! Please Select an Action</h3>
                <div className="classic-options">
                    <button className="home-button" onClick={() => navigate('/new')}>Create A Reimbursement</button>
                    <button className="home-button" onClick={() => navigate('/reimbs')}>Your Reimbursements</button>
                    <button className="home-button" onClick={() => navigate('/pending')}>Your Pending Reimbursements</button>
                    <button className="home-button" onClick={() => navigate('/profile')}>Update Profile</button>
                </div>

                {state.userSession.role === "Manager" && <hr />}

                {state.userSession.role === "Manager" && 
                    <div className="manager-options">
                        <h3>Manager Actions:</h3>
                        <button className="home-button" onClick={() => navigate('/users')}>Manage Users</button>
                        <button className="home-button" onClick={() => navigate('/man-reimbs')}>All Reimbursements</button>
                        <button className="home-button" onClick={() => navigate('/man-pending')}>All Pending Reimbursements</button>
                    </div>
                } 
            </div>
        </div>
    )
}