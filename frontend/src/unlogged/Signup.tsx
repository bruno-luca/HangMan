import {FC, useState} from 'react'
import "../assets/css/Login.css"
import axios from 'axios'

interface SignupPropsI {
    setPageIndex: (pageIndex: number) => void
}

type CreateUserResponse = {
    operation: string;
    status: boolean;
    errorMessage: string;
};

const Signup: FC<SignupPropsI> = ({setPageIndex}) => {
    const [usr, setUsr] = useState<string>("")
    const [pwd, setPwd] = useState<string>("")

    const validateCredentials = async (e: FormEvent) => {
        e.preventDefault()

        try {
            const headers = new Headers()
            headers.append("Content-Type", "application/json")

            const content = JSON.stringify({
                "username": usr,
                "password": pwd,
            })

            const requestOpt = {
                "method": "POST",
                "headers": headers,
                "body": content,
                "redirect": "follow"
            }

            const {data} = await axios.post<CreateUserResponse>("http://localhost:8080/backend/signup", requestOpt)
            console.log(data)
            if(data.status){
                console.log("T'apposto")
                setPageIndex(1)
                return
            }
            
            setPageIndex(2)
            
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log("error message: ", error.message);
                // 👇️ error: AxiosError<any, any>
                return error.message;
            } else {
                console.log("unexpected error: ", error);
                return "An unexpected error occurred";
            }
        }
    }
    
    return(  
        <>
        <div className="container">
            <h2>Sign Up</h2>
            <form onSubmit={validateCredentials}>
                <input type="text" placeholder="Username" required onChange={(e) => {setUsr(e.target.value)}}/>
                <input type="password" placeholder="Password" required onChange={(e) => {setPwd(e.target.value)}}/>
                <button type="submit" className="button">Sign Up</button>
            </form>
        </div>
        </>
    )
}

export default Signup