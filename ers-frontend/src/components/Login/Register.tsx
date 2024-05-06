import { useState } from "react"
import { UserInterface } from "../../interfaces/UserInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"

export const Register: React.FC = () => {

    const [user, setUser] = useState<UserInterface>({
        firstName: "",
        lastName: "",
        username: "",
        password: "",
        role: "Employee"
    })

    const navigate = useNavigate()

    const storeValues = (input:any) => {
        if(input.target.name === "firstName"){
            setUser((user) => ({...user, firstName:input.target.value}))
        }else if(input.target.name === "lastName"){
            setUser((user) => ({...user, lastName:input.target.value}))
        }else if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        }else{
            setUser((user) => ({...user, password:input.target.value}))
        }
    }

    // function that sends a POST request to our server to register a new employee
    const register = async () => {
        const response = await axios.post("http://localhost:8080/users", user)

        alert(response.data)

        navigate("/")
    }

    return(
        <div className="login">
            <div className="login-container">
                <h3>Create an Account</h3>
                <div className="input-container">
                    <input type="text" placeholder="First Name" name="firstName" onChange={storeValues}/>
                </div>
                <div className="input-container">
                    <input type="text" placeholder="Last Name" name="lastName" onChange={storeValues}/>
                </div>
                <div className="input-container">
                    <input type="text" placeholder="Username" name="username" onChange={storeValues}/>
                </div>
                <div className="input-container">
                    <input type="password" placeholder="Password" name="password" onChange={storeValues}/>
                </div>

                <button className="login-button" onClick={register}>Submit</button>
                <button className="login-button" onClick={() => {navigate('/')}}>Cancel</button>
            </div>
        </div>
    )
}