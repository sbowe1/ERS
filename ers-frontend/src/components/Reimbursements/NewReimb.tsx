import { useState } from "react";
import { ReimbInterface } from "../../interfaces/ReimbInterface";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './ReimbForm.css'

export const NewReimb: React.FC = () => {

    const [reimb, setReimb] = useState<ReimbInterface>({
        description: "",
        amount: 0,
        status: "PENDING"
    })

    const navigate = useNavigate()

    const gatherInput = (input: any) => {
        if(input.target.name === "description"){
            setReimb((reimb) => ({...reimb, description:input.target.value}))
        }else{
            setReimb((reimb) => ({...reimb, amount:input.target.value}))
        }

        console.log(reimb)
    }

    const newReimb = async () => {
        const response = await axios.post("http://localhost:8080/reimbs", 
        reimb, {withCredentials: true})

        alert(response.data)
    }

    return(
        <div className="new-reimb-container">
            <div className="reimbursement-container">
                <div className="navbar">
                    <button className="home-button" id="home" onClick={() => navigate("/home")}>Home</button>
                    <button className="home-button" id="logout" onClick={() => navigate("/")}>Logout</button>
                </div>

                <h2>New Reimbursement Form</h2>

                <div className="reimb-container">
                    <h4>What is the reimbursement for?</h4>
                    <input type="text" placeholder="Description" name="description" onChange={gatherInput}/>
                </div>
                
                <div className="reimb-container">
                    <h4>How much is the reimbursement?</h4>
                    <input type="number" placeholder="123" name="amount" onChange={gatherInput}/>
                </div>

                <br />
                <br />
                <br />
                <br />
                <br />

                <button className="home-button" onClick={newReimb}>Submit</button>
            </div>
        </div>
    )
}