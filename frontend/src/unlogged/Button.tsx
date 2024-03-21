import { FC } from "react";
import "../assets/css/Button.css"

interface ButtonPropsI{
    buttonText: string
    setPageIndex: (pageIndex: number) => void
    pageIndex: number

}

const Button: FC<ButtonPropsI> = ({buttonText, setPageIndex, pageIndex}) => {
    return(
        <a className="button" onClick={() => {setPageIndex(pageIndex)}}>{buttonText}</a>
    )
}

export default Button