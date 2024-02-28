import { FC } from "react";
import "../assets/css/Button.css"

interface ButtonPropsI{
    buttonText: string
    setPageIndex: (pageIndex: number) => void

}

const Button: FC<ButtonPropsI> = ({buttonText, setPageIndex}) => {
    return(
        <a className="button" onClick={() => {setPageIndex(1)}}>{buttonText}</a>
    )
}

export default Button