import axios from "axios"
import { UserInterface } from "../../interfaces/UserInterface"

export const User: React.FC<UserInterface> = (user : UserInterface) => {

    const promoteUser = async () => {
        const response = await axios.patch("http://localhost:8080/users/"+user.userId, "", {withCredentials:true})
        .then((response) => {
            alert(response.data)
        })
    }

    return(
        <tr>
            <td>{user.userId}</td>
            <td>{user.firstName}</td>
            <td>{user.lastName}</td>
            <td>{user.username}</td>
            <td>{user.role}</td>
            {user.role === "Employee" && <td><button className="home-button" onClick={promoteUser}>Promote</button></td>}
        </tr>
    )
}