import {FC, useState} from 'react'

import Hello from './unlogged/Hello.tsx';
import Login from './unlogged/Login.tsx';
import Signup from './unlogged/Signup.tsx';
import Logged from './Logged/Logged.tsx';

import "./assets/css/App.css"

const App: FC = () => {
  const pages: string[] = ["Hello", "Login", "Singup", "Logged"]
  const [pageIndex, setPageIndex] = useState(0)

  return(
    <>
      {pageIndex == 0 && <Hello setPageIndex={setPageIndex}/>}
      {pageIndex == 1 && <Login setPageIndex={setPageIndex}/>}
      {pageIndex == 2 && <Signup setPageIndex={setPageIndex}/>}
      {pageIndex == 3 && <Logged setPageIndex={setPageIndex}/>}
      {(pageIndex >= pages.length || pageIndex < 0) && <Hello setPageIndex={setPageIndex}/>}
    </>
  )
}


export default App;

