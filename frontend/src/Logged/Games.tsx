import { FC } from "react";

import "../assets/css/Games.css"
import Game from "./Game";

interface GamesPropsI {
    setPlaying: (pageIndex: boolean) => void
}

const Games: FC <GamesPropsI> = ({setPlaying}) => {
    return(
        <>
            <div id="partite" className="sottopagina">
                <div className="titolo-sottopagina">
                    <div><h2>Elenco Partite</h2></div>
                    <div onClick={() => {setPlaying(true)}}><a className="button">Gioca Nuova Partita</a></div>
                </div>
                <div className="contenuto-sottopagina">
                    <table>
                        <thead>
                            <tr>
                                <th>Game</th>
                                <th>Word</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <Game gameID={1} word={"albero"} win={false} ></Game>
                            <Game gameID={2} word={"scuola"} win={true} ></Game>
                            <Game gameID={3} word={"affascinante"} win={true} ></Game>
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )
}

export default Games;