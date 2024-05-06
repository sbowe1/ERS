import axios from "axios"
import { useNavigate } from "react-router-dom"
import { state } from "../globalStore/Stores"

export const Profile: React.FC = () => {

    let username: string = ""
    let password: string = ""

    const gatherInput = (input: any) => {
        if(input.target.name === "username"){
            username = input.target.value
        }else if(input.target.name === "password"){
            password = input.target.value
        }
    }

    const navigate = useNavigate()

    const updateUsername = async () => {
        const response = await axios.patch("http://localhost:8080/users/profile/"+state.userSession.userId, 
            {username}, {withCredentials:true})
        .then((response) => {
            alert(response.data)
        })
    }

    const updatePassword = async () => {
        const response = await axios.patch("http://localhost:8080/users/profile/"+state.userSession.userId, 
            {password}, {withCredentials:true})
        .then((response) => {
            alert(response.data)
        })
    }

    return(
        <div className="new-reimb-container">
            <div className="reimbursement-container">
                <div className="navbar">
                    <button className="home-button" id="home" onClick={() => navigate("/home")}>Home</button>
                    <button className="home-button" id="logout" onClick={() => navigate("/")}>Logout</button>
                </div>

                <br /><br />

                <h2>Update Your Profile</h2>
                <h3>Please select one to update.</h3>

                <div className="reimb-container" id="profile-form">
                    <h4>Update Username</h4>
                    <input type="text" placeholder="Username" name="username" onChange={gatherInput}/>
                    <br /><br />
                    <button className="home-button" onClick={updateUsername}>Submit Username</button>
                </div>
                
                <div className="reimb-container" id="profile-form">
                    <h4>Update Password</h4>
                    <input type="password" placeholder="Password" name="password" onChange={gatherInput}/>
                    <br /><br />
                    <button className="home-button" onClick={updatePassword}>Submit Password</button>
                </div>
            </div>
         </div>
    )
}