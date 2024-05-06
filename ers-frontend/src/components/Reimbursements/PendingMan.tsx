import { useEffect, useState } from "react"
import { ReimbInterface } from "../../interfaces/ReimbInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import './AllReimbs.css'
import { ReimbMan } from "./ReimbMan"

export const PendingMan: React.FC = () => {

    let userInput: number = 0
    let status: string = ""

    const gatherInput = (input: any) => {
        if(input.target.name === "reimbId"){
            userInput = input.target.value
        }else if(input.target.name === "status"){
            status = input.target.value
        }
    }

    // storing state that consists of Array of ReimbInterface objects
    const [reimbs, setReimb] = useState<ReimbInterface[]>([])

    const navigate = useNavigate()

    // get all reimbursements upon page rendering
    useEffect(() => {
        getAllReimbs()
    }, [])

    // get all reimbursements
    const getAllReimbs = async () => {
        const response = await axios.get("http://localhost:8080/reimbs/pending/manager", {withCredentials:true})

        setReimb(response.data)
    }

    // resolve desired reimbursement
    const resolveReimb = async () => {
        const response = await axios.patch("http://localhost:8080/reimbs/"+userInput, {status}, {withCredentials:true})
        .then((response) => {
            alert(response.data)
        })
        .then(() => getAllReimbs())
    }

    return(
        <div className="container">
            <div className="reimbursement-container">
                <div className="navbar">
                    <button className="home-button" id="home" onClick={() => navigate("/home")}>Home</button>
                    <button className="home-button" id="logout" onClick={() => navigate("/")}>Logout</button>
                </div>

                <br /><br />

                <div className="reimbursement-table">
                    <table>
                        <caption>Pending Reimbursements</caption>
                        <thead>
                            <tr>
                                <th>Reimbursement Ticket</th>
                                <th>Description</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>User ID</th>
                            </tr>
                        </thead>
                        <tbody>
                        {reimbs.map((reimb, index) => 
                            <ReimbMan {...reimb}></ReimbMan>
            
                        )}
                        </tbody>
                    </table>
                </div>

                <br />

                <div className="delete-container">
                    <h4>Resolve a Reimbursement:</h4>
                    
                    <div className="reimb-container">
                        <h5>Reimbursement Ticket Number</h5>
                        <input type="number" placeholder="1" name="reimbId" onChange={gatherInput}/>
                    </div>
                
                    <div className="reimb-container">
                        <h5>New Status</h5>
                        <input type="text" placeholder="APPROVED/DENIED" name="status" onChange={gatherInput}/>
                    </div>
                    <br /><br />
                    <button className="home-button" onClick={resolveReimb}>Submit</button>
                </div>
            </div>
        </div>
    )
}