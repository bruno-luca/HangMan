import "./keyboard.css"

const KEYS = [
    "q",
    "w",
    "e",
    "r",
    "t",
    "y",
    "u",
    "i",
    "o",
    "p",
    "a",
    "s",
    "d",
    "f",
    "g",
    "h",
    "j",
    "k",
    "l",
    "z",
    "x",
    "c",
    "v",
    "b",
    "n",
    "m"
]

type KeyboardProps = {
    activeLetters: string[],
    inactiveLetters: stirng[], 
    addGuessedLetter: (letter: string) => void,
    disabled: boolean
}

export function Keyboard({activeLetters, inactiveLetters, addGuessedLetter, disabled} : KeyboardProps){
    return <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(75px, 1fr))",
        gap: ".5rem"
    }}>
        {KEYS.map(key => {
            const isActive = activeLetters.includes(key)
            const isInactive = inactiveLetters.includes(key)

            return (
            <button 
                onClick={() => addGuessedLetter(key)}
                key={key}
                className={`
                    ${isActive ? "active" : ""}
                    ${isInactive ? "inactive" : ""}
                `}
                disabled={isInactive || isActive || disabled}

            >
                {key}
            </button>)
        })}

    </div>
}