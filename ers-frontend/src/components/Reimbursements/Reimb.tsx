import axios from "axios"
import { ReimbInterface } from "../../interfaces/ReimbInterface"

export const Reimb: React.FC<ReimbInterface> = (reimb : ReimbInterface) => {

    let description: string = ""

    const gatherInput = (input: any) => {
        if(input.target.name === "description"){
            description = input.target.value
        }
    }

    const updateDescription = async () => {
        const response = await axios.patch("http://localhost:8080/reimbs/update/"+reimb.reimbId, {description}, {withCredentials: true})
        .then((response) => {
            alert(response.data)
        })
    }

    return(
        <tr>
            <td>{reimb.reimbId}</td>
            <td>{reimb.description}</td>
            <td>${reimb.amount}</td>
            <td>{reimb.status}</td>
            {reimb.status === "PENDING" && 
                <td>
                    <input type="text" placeholder="New Description" name="description" id="new-description" onChange={gatherInput}/>
                    <button className="home-button" id="submit-description" onClick={updateDescription}>Submit</button>
                </td>
            }
        </tr>
    )
}