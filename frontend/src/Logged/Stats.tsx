import { FC, useEffect, useState } from "react";

import "../assets/css/Games.css"
import axios from "axios";
import Stat from "./Stat";

interface StatsPropsI {
}

const Stats: FC <StatsPropsI> = () => {
    const [stats, setStats] = useState([])

    useEffect(() => {
        const getStats = async () => {
            try {
                const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken");
                // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
                const response = await axios.get(
                    "http://localhost:8080/backend/stats",
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
                    setStats(JSON.parse(response.data.data));
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
        getStats();
    }, [])

    return(
        <>
            <div id="partite" className="sottopagina">
                <div className="titolo-sottopagina">
                    <div><h2>Statistiche Giocatori</h2></div>
            
                </div>
                <div className="contenuto-sottopagina">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Wins</th>
                                <th>Loses</th>
                            </tr>
                        </thead>
                        <tbody>
                            {stats.map(stat => (
                                <Stat
                                    key={stat.id} // Assuming each stat has a unique ID
                                    id={stat.id}
                                    username={stat.username}
                                    wins={stat.wins}
                                    loses={stat.loses}
                                />
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )
}

export default Stats;