import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { UserInterface } from "../../interfaces/UserInterface"
import axios from "axios"
import { User } from "./User"

export const AllUsers: React.FC = () => {

    let userInput: number = 0

    const gatherInput = (input: any) => {
        userInput = input.target.value
    }
    
    const [users, setUser] = useState<UserInterface[]>([])

    const navigate = useNavigate()

    useEffect(() => {
        getAllUsers()
    }, [])

    // get all users
    const getAllUsers = async () => {
        const response = await axios.get("http://localhost:8080/users", {withCredentials:true})

        setUser(response.data)
    }

    // fire an employee
    const deleteUser = async () => {
        const response = await axios.delete("http://localhost:8080/users/" + userInput, {withCredentials:true})
        .then((response) => {
            alert(response.data)
        })
        .then(() => getAllUsers())
    }

    return(
        <div className="container">
            <div className="reimbursement-container">
                <div className="navbar">
                    <button className="home-button" id="home" onClick={() => navigate("/home")}>Home</button>
                    <button className="home-button" id="logout" onClick={() => navigate("/")}>Logout</button>
                </div>

                <br /><br />

                <div className="user-table">
                    <table>
                        <caption>All Users</caption>
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {users.map((user, index) => 
                            <User {...user}></User>
                        )}
                        </tbody>
                    </table>
                </div>

                <button className="home-button" onClick={getAllUsers}>Refresh</button>

                <br />

                <div className="delete-container">
                    <h4>Who would you like to remove?</h4>
                    <input type="number" placeholder="1" name="userId" onChange={gatherInput}/>
                    <button className="home-button" onClick={deleteUser}>Submit</button>
                </div>
            </div>
        </div>
    )
}