const HEAD = (
    <div style={{
        width: "50px",
        height: "50px",
        borderRadius: "100%",
        border: "10px solid black",
        position: "absolute",
        top: "20px",
        right: "-30px"

    }}></div>
)

const BODY = (
    <div style={{
        width: "10px",
        height: "100px",
        backgroundColor: "black",
        position: "absolute",
        top: "90px",
        right: "0px"

    }}></div>
)

const RIGHT_ARM = (
    <div style={{
        width: "100px",
        height: "10px",
        backgroundColor: "black",
        position: "absolute",
        top: "120px",
        right: "-100px",
        rotate: "-30deg",
        transformOrigin: "left bottom"

    }}></div>
)

const LEFT_ARM = (
    <div style={{
        width: "100px",
        height: "10px",
        backgroundColor: "black",
        position: "absolute",
        top: "120px",
        right: "10px",
        rotate: "30deg",
        transformOrigin: "right bottom"

    }}></div>
)

const RIGHT_LEG = (
    <div style={{
        width: "100px",
        height: "10px",
        backgroundColor: "black",
        position: "absolute",
        top: "180px",
        right: "-90px",
        rotate: "60deg",
        transformOrigin: "left bottom"

    }}></div>
)

const LEFT_LEG = (
    <div style={{
        width: "100px",
        height: "10px",
        backgroundColor: "black",
        position: "absolute",
        top: "180px",
        right: "0",
        rotate: "-60deg",
        transformOrigin: "right bottom"

    }}></div>
)

type HangmanDrawingProps = {
    numberOfGuesses: number
}

const BODY_PARTS = [HEAD, BODY, RIGHT_ARM, LEFT_ARM, RIGHT_LEG, LEFT_LEG]

export function HangmanDrawing({numberOfGuesses} : HangmanDrawingProps){
    return <div style={{
        position: "relative"
    }}>
        {BODY_PARTS.slice(0, numberOfGuesses)}  
        <div style={{height: "20px", width: "10px", backgroundColor: "black", marginLeft: "120px", position: "absolute", top: 0, right: 0}}></div>
        <div style={{height: "10px", width: "200px", backgroundColor: "black", marginLeft: "120px"}}></div>
        <div style={{height: "300px", width: "10px", backgroundColor: "black", marginLeft: "120px"}}></div>
        <div style={{height: "10px", width: "250px", backgroundColor: "black"}}></div>

    </div>
}