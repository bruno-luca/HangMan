import { FC } from "react"

interface PostPropsI{
    author: string,
    timestamp: string
    content: string
}

const Post: FC<PostPropsI> = ({author, timestamp, content}) => {
    return (
        <div className="post">
            <div className="title">
                <div className="author">
                    {author}
                </div>
                <div className="timestamp">
                    {timestamp}
                </div>
            </div>
            <div className="content">
                {content}
            </div>
        </div>
    )
}

export default Post