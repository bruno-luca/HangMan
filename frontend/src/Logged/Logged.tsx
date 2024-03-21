import { FC, useEffect, useState } from "react";
import Header from "./Header";
import Games from "./Games";
import Stats from "./Stats";

interface LoggedProposI{
    setPageIndex: (pageIndex: number) => void
    username: String
}

const Logged:FC <LoggedProposI> = ({setPageIndex, username}) => {
    const [subpageIndex, setSubpageIndex] = useState(0);
    return(
        <>
            <Header setPageIndex={setPageIndex} setSubpageIndex={setSubpageIndex} subPageIndex={subpageIndex} username={username}></Header>
            {subpageIndex == 0 && <Games setSubpageIndex={setSubpageIndex}></Games>}
            {/* {subpageIndex == 1 && <Games setSubpageIndex={setSubpageIndex}></Games>} */}
            {subpageIndex == 2 && <Stats setSubpageIndex={setSubpageIndex}></Stats>}
        </>
    )
}

export default Logged;

