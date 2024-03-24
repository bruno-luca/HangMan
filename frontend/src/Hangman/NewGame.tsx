import { FC, useCallback, useEffect, useState } from "react"
import "./HangmanDrawing"
import { HangmanDrawing } from "./HangmanDrawing"
import { HangmanWord } from "./HangmanWord"
import { Keyboard } from "./Keyboard"
import axios from "axios"
import EndgameModal from "./EndgameModal"

interface NewGamePropsI {
  setSubpageIndex: (subpageIndex: number) => void
  setPlaying: (subpageIndex: boolean) => void
  username: string
}

const NewGame: FC <NewGamePropsI> = ({setSubpageIndex, setPlaying, username}) => {
  const [wordToGuess, setWordToGuess] = useState("")
  const [guessedLetters, setGuessedLetters] = useState<string[]>([])
  const [displayValue, setDisplayvalue] = useState("none")
  const [message, setMessage] = useState("")
  const [gameResult, setGameResult] = useState(-1)
  const incorrectLetters = guessedLetters.filter(letter => !(wordToGuess).includes(letter))
  
  const isLoser = incorrectLetters.length >= 6
  const isWinner = wordToGuess!== "" && wordToGuess.split("").every(letter => guessedLetters.includes(letter))

  const addGuessedLetter = useCallback((letter: string) => {
      if(guessedLetters.includes(letter) || isLoser || isWinner) return
  
      setGuessedLetters(currentLetters => [...currentLetters, letter])
    },
    [guessedLetters, isLoser, isWinner]
  )

  useEffect(()=>{
    const getWord = async () => {
      try {
        const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken");
        // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
        const response = await axios.get( "http://localhost:8080/backend/word",{
              headers: {
                  Authorization: jwtoken, 
                  "Content-Type": "application/json",
              },});
  
        if (response.status) {
          console.log(typeof response.data, response.data)
          console.log("Indovina: ", response.data.word, typeof response.data.word)
          setWordToGuess(response.data.word)
        } else {
          console.error(JSON.stringify(response, null, 4))
        }
      } catch (error) {
        if (axios.isAxiosError(error)) {
            console.log("error message: ", error.message)
            return ""
        } else {
            console.log("unexpected error: ", error)
            return ""
        }
      }
    }
    // testare se viene eseguito solo più una volta
    return () => getWord()
  }, [])

  useEffect(() => {
    const handler = (e: KeyboardEvent) => {
      const key = e.key

      if(!key.match(/^[a-z]$/)) return
      e.preventDefault()

      addGuessedLetter(key)
    }

    document.addEventListener("keypress", handler)

    return () => {
      document.removeEventListener("keypress", handler)
    }
  }, [guessedLetters])

  useEffect(() => {
    if(isLoser){
      setMessage("Hai perso!")
      setGameResult(0)
      setDisplayvalue("")
    }else if(isWinner){
      setMessage("Hai vinto!")
      setGameResult(1)
      setDisplayvalue("")
    }
  }, [guessedLetters])

  return (
    <>
      

      <HangmanDrawing numberOfGuesses = {incorrectLetters.length}/>
      <HangmanWord 
        guessedLetters = {guessedLetters} 
        wordToGuess = {wordToGuess}
        reveal = {isLoser}
      />
      <div style={{alignSelf: "stretch"}}>
        <EndgameModal displayValue={displayValue} message={message} setSubpageIndex={setSubpageIndex} setPlaying={setPlaying} wordToGuess={wordToGuess} gameResult={gameResult} username={username}></EndgameModal>
        <Keyboard activeLetters={guessedLetters.filter(letter => 
          wordToGuess.includes(letter))}
          inactiveLetters = {incorrectLetters}
          addGuessedLetter = {addGuessedLetter}
          disabled={isWinner || isLoser}/>
      </div>
    </>
  )
}

export default NewGame
