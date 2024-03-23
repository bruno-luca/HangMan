import { FC } from "react"
import "../assets/css/Header.css"

interface HeaderPropsI{
    setPageIndex: (pageIndex: number) => void
    setSubpageIndex: (pageIndex: number) => void
    setPlaying: (pageIndex: boolean) => void
    username: String
    subPageIndex: number
}

const Header: FC<HeaderPropsI> = ({setPageIndex, setSubpageIndex, setPlaying, username, subPageIndex}) => {
    return (
        <>
            <header>
                <h1><span style={{color: "#0188a5"}}>Welcome back, </span> <span style={{color: "#0bffe6"}}>{(username !== "") ? username: "Gamer"}</span>!</h1>
                <nav>
                    <ul id="navbar">
                        <li><a className={(subPageIndex == 0) ?  "nav-link selected" : "nav-link"}  id="partite" onClick={() => {setSubpageIndex(0)}}>Partite</a></li>
                        <li><a className={(subPageIndex == 1) ?  "nav-link selected" : "nav-link"}  id="bacheca" onClick={() => {setSubpageIndex(1)}}>Bacheca</a></li>
                        {sessionStorage.getItem("privilege") == "1" && <li><a className={(subPageIndex == 2) ?  "nav-link selected" : "nav-link"}  id="stats" onClick={() => {setSubpageIndex(2)}}>Stats</a></li>}
                        <li><a className="nav-link" onClick={() => {
                                sessionStorage.removeItem("jwtToken")
                                setPageIndex(0)
                                window.location.reload()
                            }}>Logout</a></li>
                    </ul>
                </nav>
            </header>
        </>
    )
}

export default Header