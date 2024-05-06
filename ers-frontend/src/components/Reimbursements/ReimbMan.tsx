import { ReimbInterface } from "../../interfaces/ReimbInterface"
import { state } from "../globalStore/Stores"

export const ReimbMan: React.FC<ReimbInterface> = (reimb : ReimbInterface) => {


    return(
        <tr>
            <td>{reimb.reimbId}</td>
            <td>{reimb.description}</td>
            <td>${reimb.amount}</td>
            <td>{reimb.status}</td>
            {state.userSession.role === "Manager" && <td>{reimb.userId}</td>}
        </tr>
    )
}