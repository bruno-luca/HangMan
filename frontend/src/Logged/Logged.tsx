import { FC, useEffect, useState } from "react";
import Header from "./Header";
import Games from "./Games";

interface LoggedProposI{
    setPageIndex: (pageIndex: number) => void
}

const Logged:FC <LoggedProposI> = ({setPageIndex}) => {
    const [subpageIndex, setSubpageIndex] = useState(0);
    return(
        <>
            <Header setPageIndex={setPageIndex} setSubpageIndex={setSubpageIndex} subPageIndex={subpageIndex}></Header>
            {subpageIndex == 0 && <Games setSubpageIndex={setSubpageIndex}></Games>}
        </>
    )
}

export default Logged;

