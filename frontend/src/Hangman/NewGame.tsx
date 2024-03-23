import { FC, useCallback, useEffect, useState } from "react"
import "./HangmanDrawing"
import { HangmanDrawing } from "./HangmanDrawing"
import { HangmanWord } from "./HangmanWord"
import { Keyboard } from "./Keyboard"
import axios from "axios"

interface NewGamePropsI {
  setSubpageIndex: (subpageIndex: number) => void
  setPlaying: (subpageIndex: boolean) => void
}

const NewGame: FC <NewGamePropsI> = ({setSubpageIndex, setPlaying}) => {
  const [wordToGuess, setWordToGuess] = useState("")
  const [guessedLetters, setGuessedLetters] = useState<string[]>([])
  const incorrectLetters = guessedLetters.filter(letter => !(wordToGuess).includes(letter))
  
  const isLoser = incorrectLetters.length >= 6
  const isWinner = wordToGuess.split("").every(letter => guessedLetters.includes(letter))

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

    getWord()
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

  return (
    <>
      <div style={{
        fontSize: "2rem",
        textAlign: "center"
      }}>
        {isWinner && "Winner! - refresh to try again"}
        {isLoser && "Loser! - refresh to try again"}
      </div>

      <HangmanDrawing numberOfGuesses = {incorrectLetters.length}/>
      <HangmanWord 
        guessedLetters = {guessedLetters} 
        wordToGuess = {wordToGuess}
        reveal = {isLoser}
      />
      <div style={{alignSelf: "stretch"}}>
        <Keyboard activeLetters={guessedLetters.filter(letter => 
          wordToGuess.includes(letter))}
          inactiveLetters = {incorrectLetters}x
          addGuessedLetter = {addGuessedLetter}
          disabled={isWinner || isLoser}/>
      </div>
    </>
  )
}

export default NewGame
