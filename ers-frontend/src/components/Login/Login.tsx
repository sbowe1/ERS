import { useNavigate } from "react-router-dom"
import './Login.css'
import { useState } from "react"
import axios from "axios"
import { state } from "../globalStore/Stores"

export const Login: React.FC = () => {
    const [user, setUser] = useState({
        username: "",
        password: ""
    })

    const navigate = useNavigate()

    const storeValues = (input:any) => {
        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        }else{
            setUser((user) => ({...user, password:input.target.value}))
        }
    }

    const login = async () => {
        // sending POST request to backend to authenticate login
        const response = await axios.post("http://localhost:8080/users/login", 
        user, {withCredentials:true})
        .then((response) => {
            // store data into global store
            state.userSession = response.data

            // upon successful login, redirect to home page
            navigate('home')
        }).catch((error) => {
            console.log(error.data)
        })    
    }


    return(
        <div className="login">
            <div className="login-container">
                <h3>Welcome to the Employee Reimbursement System</h3>
                <div className="input-container">
                    <input type="text" placeholder="Username" name="username" onChange={storeValues}/>
                </div>
                <div className="input-container">
                    <input type="password" placeholder="Password" name="password" onChange={storeValues}/>
                </div>

                <button className="login-button" onClick={login}>Sign In</button>
                <button className="login-button" onClick={() => {navigate('/register')}}>Register</button>
            </div>
        </div>
    )
}
