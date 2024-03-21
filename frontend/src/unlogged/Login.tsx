import {FC, FormEvent, useState} from 'react'
import axios from "axios"
import "../assets/css/Login.css"

interface LoginPropsI {
    setPageIndex: (pageIndex: number) => void
    setUsername: (username: String) => void
}

type CreateUserResponse = {
    operation: string;
    privilege: number;
    username: string;
    status: boolean;
    token: string;
    errorMessage: string;
};

const Login: FC<LoginPropsI> = ({setPageIndex, setUsername}) => {
    const [usr, setUsr] = useState<string>("")
    const [pwd, setPwd] = useState<string>("")

    const validateCredentials = async (e: FormEvent) => {
        e.preventDefault()

        try {
            const headers = new Headers()
            headers.append("Content-Type", "application/json")

            const content = JSON.stringify({
                "username": usr,
                "password": pwd
            })

            const requestOpt = {
                "method": "POST",
                "headers": headers,
                "body": content,
                "redirect": "follow"
            }

            const {data} = await axios.post<CreateUserResponse>("http://localhost:8080/backend/login", requestOpt)

            if(data.status){
                sessionStorage.setItem("jwtToken", data.token)
                sessionStorage.setItem("privilege", data.privilege.toString())
                setUsername(data.username)
                setPageIndex(3)
                return
            }else setPageIndex(1)


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
            <h2>Login</h2>
            <form onSubmit={validateCredentials}>
                <input type="text" placeholder="Username" required onChange={(e) => {setUsr(e.target.value)}}/>
                <input type="password" placeholder="Password" required onChange={(e) => {setPwd(e.target.value)}}/>
                <button className="button" type="submit">Login</button>
            </form>
            <p>Need an accout? <u onClick={() => {setPageIndex(2)}}>Signup!</u></p>
        </div>
        </>
    )
}

export default Login