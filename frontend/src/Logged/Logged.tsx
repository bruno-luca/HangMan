import { FC, useEffect, useState } from "react";
import Header from "./Header";
import Games from "./Games";
import Stats from "./Stats";
import NewGame from "../Hangman/NewGame";

interface LoggedProposI{
    setPageIndex: (pageIndex: number) => void
    username: String
}

const Logged:FC <LoggedProposI> = ({setPageIndex, username}) => {
    const [subpageIndex, setSubpageIndex] = useState(0);
    const [playing, setPlaying] = useState(false)
    return(
        <>
        
            {!playing && <Header setPageIndex={setPageIndex} setSubpageIndex={setSubpageIndex}  setPlaying={setPlaying} subPageIndex={subpageIndex} username={username}></Header>}
            {!playing && subpageIndex == 0 && <Games setPlaying={setPlaying}></Games>}
            {!playing && subpageIndex == 2 && <Stats></Stats>}
            {playing  && 
                <div style={{
                    position: "fixed",
                    top: "0",
                    left: "0",
                    width: "100%",
                    height: "100%",
                }}>
                    <div style={{
                        maxWidth: "800px",
                        display: "flex",
                        flexDirection: "column",
                        gap: "2rem",
                        margin: "0 auto",
                        alignItems: "center",
                        color: "white"
                    }}>
                        <NewGame setSubpageIndex={setSubpageIndex} setPlaying={setPlaying}></NewGame>
                    </div>
                    
                </div>
            }
        </>
    )
}

export default Logged;

