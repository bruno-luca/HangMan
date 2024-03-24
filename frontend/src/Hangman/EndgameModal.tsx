import React, { FC, FormEvent } from "react";
import "./../assets/css/EndgameModal.css";
import "./../assets/css/Button.css";
import axios from "axios";

interface EndgameModalProps {
  message: string
  displayValue: string
  wordToGuess: string
  gameResult: number
  username: string
  setSubpageIndex: (subPageIndex: number) => void
  setPlaying: (playing: boolean) => void
}

const EndgameModal: FC<EndgameModalProps> = ({ message, displayValue, setSubpageIndex, setPlaying, wordToGuess, gameResult, username}) => {
  const endGame = async (e: FormEvent) => {
    e.preventDefault()
    try {
        const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken");
        // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
        const content = JSON.stringify({
          token: jwtoken,
          "word": wordToGuess,
          "gameResult": gameResult,
      })
        const {data} = await axios.post("http://localhost:8080/backend/game",
            {
            "method": "POST",
            "headers": {
                Authorization: jwtoken, 
                "Content-Type": "application/json",
                "word": wordToGuess,
                "gameResult": gameResult
            },
            "body": content,
            "redirect": "follow"            
          }
        );
          console.log(data)
        if (data.status) {
          setPlaying(false)
          setSubpageIndex(0)
        } else {
            console.error(JSON.stringify(data, null, 4));
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

  return (
    <div className="endgame-modal-container" style={{display: displayValue}}>
      <div className="endgame-glass-pane"></div>
      <div className="endgame-modal">
        <div className="endgame-content">
          <h1>{message}</h1>
          <form onSubmit={endGame}>
            <button className="button" type="submit">Chiudi</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EndgameModal;
