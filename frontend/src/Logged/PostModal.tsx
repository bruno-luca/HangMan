import { FC, FormEvent, useState } from "react"

import "./../assets/css/PostModal.css"
import axios from "axios"

interface PostModalPropsI{
    displayValue: string
    setDisplayValue: (value: string) => void
    getPosts: () => void
}


const PostModal: FC<PostModalPropsI> = ({displayValue, setDisplayValue, getPosts}) => {
    const [content, setContent] = useState("")
    const addPost = async (e: FormEvent) => {
        e.preventDefault()
        try {
            const jwtoken = "Bearer " + "$" + sessionStorage.getItem("jwtToken")
            // Effettua la tua autenticazione qui, ad esempio con una chiamata fetch
            const body = JSON.stringify({
                token: jwtoken,
                "content": content
            })
            const {data} = await axios.post("http://localhost:8080/backend/pinboard",
                {
                    "method": "POST",
                    "headers": {
                        Authorization: jwtoken, 
                        "Content-Type": "application/json"
                    },
                    "body": body,
                    "redirect": "follow"            
                }
            )
            console.log(data)
            if (data.status) {
                setDisplayValue("none")
            } else {
                console.error(JSON.stringify(data, null, 4))
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log("error message: ", error.message)
                return error.message
            } else {
                console.log("unexpected error: ", error)
                return "An unexpected error occurred"
            }
        }
    }

    const closeModal = (e: FormEvent) => {
        e.preventDefault()
        setDisplayValue("none")
    }

    return(
        <div className="post-modal-container" style={{display: displayValue}}>
            <div className="post-glass-pane" onClick={closeModal}></div>
            <div className="post-modal">
                <div className="post-content">
                    <h1>New Post</h1>
                    <form onSubmit={addPost}>
                        <textarea maxLength={1000} className="post-input" onChange={(e) => {setContent(e.target.value)}}/>
                        <div className="commands">
                            <button className="button" onClick={closeModal}>Close</button>
                            <button className="button" type="submit">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default PostModal