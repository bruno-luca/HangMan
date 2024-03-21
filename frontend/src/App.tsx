import {FC, useEffect, useState} from 'react'

import Hello from './unlogged/Hello.tsx';
import Login from './unlogged/Login.tsx';
import Signup from './unlogged/Signup.tsx';
import Logged from './Logged/Logged.tsx';

import "./assets/css/App.css"

const App: FC = () => {
  const pages: string[] = ["Hello", "Login", "Singup", "Logged"]
  const [pageIndex, setPageIndex] = useState(0)
  const [username, setUsername] = useState("")

  const token = sessionStorage.getItem("jwtToken")
  const admin = sessionStorage.getItem("privilege")

  if(!token && pageIndex == 0) return(<>{<Hello setPageIndex={setPageIndex}/>}</>)
  else if (!token && pageIndex == 1) return(<>{<Login setPageIndex={setPageIndex} setUsername={setUsername}/>}</>)
  else if (!token && pageIndex == 2) return(<>{<Signup setPageIndex={setPageIndex}/>}</>)
  else if (token && pageIndex == 3) return(<>{<Logged setPageIndex={setPageIndex} username={username}/>}</>)
  else if (token && pageIndex == 4) return(<>{}</>)
  else if (token) return(<>{<Logged setPageIndex={setPageIndex}/>}</>)
  else return(<>{<Hello setPageIndex={setPageIndex}/>}</>)
}


export default App;

