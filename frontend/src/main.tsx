import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from "./App/App";
import {SnackbarProvider} from "notistack";

ReactDOM.render(
    <React.StrictMode>
        <SnackbarProvider maxSnack={2}>
            <App/>
        </SnackbarProvider>
    </React.StrictMode>,
    document.getElementById('root')
)
