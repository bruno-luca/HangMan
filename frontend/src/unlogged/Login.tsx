import {FC} from 'react'
import "../assets/css/Login.css"

interface LoginPropsI {
    setPageIndex: (pageIndex: number) => void
}

const Login: FC<LoginPropsI> = ({setPageIndex}) => {
    
    return(  
        <>
            <div className="container">
            <h2>Login</h2>
            <form>
                <input type="text" placeholder="Username"/>
                <input type="password" placeholder="Password"/>
                <a className="button" onClick={() => {setPageIndex(3)}}>Login</a>
            </form>
            <p>Need an accout? <u onClick={() => {setPageIndex(2)}}>Signup!</u></p>
        </div>
        </>
    )
}

export default Login