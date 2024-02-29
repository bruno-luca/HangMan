import { FC } from "react"
import "../assets/css/Header.css"

interface HeaderPropsI{
    setPageIndex: (pageIndex: number) => void
    setSubpageIndex: (pageIndex: number) => void
    subPageIndex: number
}

const Header: FC<HeaderPropsI> = ({setPageIndex, setSubpageIndex, subPageIndex}) => {
    return (
        <>
            <header>
                <h1><span style={{color: "#0188a5"}}>Welcome back, </span> <span style={{color: "#0bffe6"}}>Username</span>!</h1>
                <nav>
                    <ul id="navbar">
                        <li><a className={(subPageIndex == 0) ?  "nav-link selected" : "nav-link"}  id="partite" onClick={() => {setSubpageIndex(0)}}>Partite</a></li>
                        <li><a className={(subPageIndex == 1) ?  "nav-link selected" : "nav-link"}  id="bacheca" onClick={() => {setSubpageIndex(1)}}>Bacheca</a></li>
                        <li><a className={(subPageIndex == 2) ?  "nav-link selected" : "nav-link"}  id="workinprogress" onClick={() => {setSubpageIndex(2)}}>Work in Progress</a></li>
                        <li><a className="nav-link" onClick={() => {setPageIndex(0)}}>Logout</a></li>
                    </ul>
                </nav>
            </header>
        </>
    )
}

export default Header