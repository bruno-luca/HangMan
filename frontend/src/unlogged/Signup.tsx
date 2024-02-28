import {FC} from 'react'
import "../assets/css/Login.css"

interface SignupPropsI {
    setPageIndex: (pageIndex: number) => void
}

const Signup: FC<SignupPropsI> = ({setPageIndex}) => {
    
    return(  
        <>
        <div className="container">
            <h2>Sign Up</h2>
            <form>
                <input type="text" placeholder="Username"/>
                <input type="email" placeholder="Email"/>
                <input type="password" placeholder="Password"/>
                <button type="submit" className="button">Sign Up</button>
            </form>
        </div>
        </>
    )
}

export default Signup