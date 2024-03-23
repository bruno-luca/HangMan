import { FC } from "react"

interface GamePropsI {
    gameID: number
    word: string
    win: boolean 
}

const Game: FC<GamePropsI> = (GamePropsI) => {
    return (
        <>
            <tr>
                <td>{GamePropsI.gameID}</td>
                <td>{GamePropsI.word}</td>
                <td>{GamePropsI.win ? "VITTORIA" : "SCONFITTA"}</td>
                <td></td>
            </tr>
        </>
    )
}

export default Game