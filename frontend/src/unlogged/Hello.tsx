import {FC, useEffect} from 'react'
import "../assets/css/Hello.css"
import Button from './Button';

interface HelloPropsI {
    setPageIndex: (pageIndex: number) => void
}

const Hello: FC<HelloPropsI> = ({setPageIndex}) => {
    useEffect(() => {
        const colors = ["#df0772", "#fe546f", "#ff9e7d", "#ffd080", "#fffdff", "#0bffe6", "#01cbcf", "#0188a5"];
        let i = 0;

        const rainbowUpdate = () => {
            const letters = document.getElementsByClassName("rainbow");

            for (let k = 0; k < letters.length; k++) {
                letters[k].style.color = colors[(i + k) % colors.length];
            }
            i = (i + 1) % colors.length;
        }

        const intervalId = setInterval(rainbowUpdate, 100);

        return () => {
            clearInterval(intervalId);
        };
    }, []);
    
    return(  
        <>
        <div className="container">
            <h1><span style={{color: '#df0772'}}>Welcome</span><span style={{color: '#fe546f'}}> to</span><span style={{color: '#ff9e7d'}}> the</span><span style={{color: '#ffd080'}}> Game</span></h1>
            <p>Get ready for an 

            <span> </span>
            <span className="rainbow">E</span>
            <span className="rainbow">X</span>
            <span className="rainbow">I</span>
            <span className="rainbow">T</span>
            <span className="rainbow">I</span>
            <span className="rainbow">N</span>
            <span className="rainbow">G</span>
            <span> </span>
            
            gameplay!</p>
            <Button buttonText='Login' setPageIndex={setPageIndex}/>
            <Button buttonText='Signup' setPageIndex={setPageIndex}/>

        </div>
        </>
    )
}

export default Hello