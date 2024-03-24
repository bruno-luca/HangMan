import { FC, useEffect, useState } from "react"

import "../assets/css/Posts.css"
import axios from "axios"
import Post from "./Post"

const Posts:FC = () => {
    const [posts, setPosts] = useState([])

    useEffect(() => {
        const getStats = async () => {
            try {
                const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken");
                // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
                const response = await axios.get(
                    "http://localhost:8080/backend/pinboard",
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
                    setPosts(JSON.parse(response.data.data));
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

    return (
        <div id="partite" className="sottopagina">
            <div className="titolo-sottopagina">
                <div><h2>Recent updates</h2></div>
                {sessionStorage.getItem("privilege") == "1" && <div onClick={() => {setPlaying(true)}}><a className="button">Nuovo Post</a></div>}
            </div>
            <div className="post-container">
                {posts.map(post => (
                    <Post 
                        author={post.author}
                        timestamp={post.timestamp}
                        content={post.content}/>
                ))}
            </div>
        </div>
    )
}

export default Posts