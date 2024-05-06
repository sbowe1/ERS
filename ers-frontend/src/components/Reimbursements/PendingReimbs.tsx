import { useEffect, useState } from "react"
import { ReimbInterface } from "../../interfaces/ReimbInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import { Reimb } from "./Reimb"
import './AllReimbs.css'

export const PendingReimbs: React.FC = () => {

    // storing state that consists of Array of ReimbInterface objects
    const [reimbs, setReimb] = useState<ReimbInterface[]>([])

    const navigate = useNavigate()

    // get all reimbursements upon page rendering
    useEffect(() => {
        getAllReimbs()
    }, [])

    // get all reimbursements
    const getAllReimbs = async () => {
        const response = await axios.get("http://localhost:8080/reimbs/pending", {withCredentials:true})

        setReimb(response.data)
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
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {reimbs.map((reimb, index) => 
                            <Reimb {...reimb}></Reimb>
            
                        )}
                        </tbody>
                    </table>
                </div>
                <button className="home-button" onClick={getAllReimbs}>Refresh</button>
            </div>
        </div>
        
    )
}