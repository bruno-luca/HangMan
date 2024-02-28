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
                <a href="logged_user.html"  className="button">Login</a>
            </form>
            <p>Need an accout? <u onClick={() => {setPageIndex(2)}}>Signup!</u></p>
        </div>
        </>
    )
}

export default Login