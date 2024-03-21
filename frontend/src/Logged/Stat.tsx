import { FC } from "react"

interface StatPropsI {
    id: number
    username: string
    wins: number
    loses: number 
}

const Stat: FC<StatPropsI> = (StatPropsI) => {
    return (
        <>
            <tr>
                <td>{StatPropsI.id}</td>
                <td>{StatPropsI.username}</td>
                <td>{StatPropsI.wins}</td>
                <td>{StatPropsI.loses}</td>
            </tr>
        </>
    )
}

export default Stat