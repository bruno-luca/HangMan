import { FC, useEffect, useState } from "react";

import "../assets/css/Games.css"
import Game from "./Game";
import axios from "axios";

interface GamesPropsI {
    setPlaying: (pageIndex: boolean) => void 
}

const Games: FC <GamesPropsI> = ({setPlaying}) => {
    const [games, setGames] = useState([])

    useEffect(() => {
        const getGames = async () => {
            try {
                const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken");
                // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
                const response = await axios.get(
                    "http://localhost:8080/backend/game",
                    {
                    headers: {
                        Authorization: jwtoken, // Sostituisci con il tuo token effettivo
                        "Content-Type": "application/json",
                    },
                    }
                );
                if (response.status) {
                    console.log(typeof response.data, response.data)
                    console.log(JSON.parse(response.data.data))
                    setGames(JSON.parse(response.data.data));
                } else {
                    console.error(JSON.stringify(response, null, 4));
                }
            } catch (error) {
                if (axios.isAxiosError(error)) {
                    console.log("error message: ", error.message);
                    return error.message;
                } else {
                    console.log("unexpected error: ", error);
                    return "An unexpected error occurred";
                }
            }
        };
        getGames();
    }, [])

    
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
                        <tbody style={{
                            height: "50px",
                            overflow: "scroll"
                        }}>
                            {games.map(game => (
                                <Game
                                    key={game.id} // Assuming each game has a unique ID
                                    gameID={game.id}
                                    word={game.text}
                                    win={(game.winner)}
                                />
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )
}

export default Games;