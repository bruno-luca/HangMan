import React, { FC } from "react";
import "./../assets/css/EndgameModal.css";
import "./../assets/css/Button.css";

interface EndgameModalProps {
  message: string
  displayValue: string
  setSubpageIndex: (subPageIndex: number) => void
  setPlaying: (playing: boolean) => void
}

const EndgameModal: FC<EndgameModalProps> = ({ message, displayValue, setSubpageIndex, setPlaying }) => {
  const returnToMenu = async () => {

    
    setPlaying(false)
    setSubpageIndex(0)
  }

  return (
    <div className="endgame-modal-container" style={{display: displayValue}}>
      <div className="endgame-glass-pane" onClick={returnToMenu}></div>
      <div className="endgame-modal">
        <div className="endgame-content">
          <h1>{message}</h1>
          <a className="button" onClick={returnToMenu}>Chiudi</a>
        </div>
      </div>
    </div>
  );
};

export default EndgameModal;
