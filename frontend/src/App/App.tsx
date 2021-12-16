import {ReactElement, useState} from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Login from "./paginas/Login";
import axios from "axios";
import img_2 from "./img/identidad/img_2.png";
import BarraDeNavegacion from "./componentes/BarraDeNavegacion";
import Principal from "./paginas/Principal";
import E404 from "./paginas/E404";

export default function App(): ReactElement {
    axios.defaults.baseURL = 'http://localhost:8080';
    const body = document.getElementsByTagName("body")[0]
    const [session, setSession] = useState<string | null>(localStorage.getItem("jwt"))

    const iniciarSession = (usuario: string, contrasenna: string): void => {
        axios
            .post("/login", {
                usuario: usuario,
                contrasenna: contrasenna
            })
            .then(datos => {
                localStorage.setItem("jwt", datos.data)
                setSession(datos.data)
            })
            .catch(error => console.error(error))
    }
    const cerrarSession = () => {
        localStorage.clear()
        setSession(null)
    }

    if (session === null) {
        body.style.backgroundImage = `url(${img_2})`;
        body.style.backgroundRepeat = "no-repeat";
        body.style.backgroundSize = "cover";
    } else {
        body.style.background = "none";
    }
    return (
        <BrowserRouter>
            <Routes>
                {(session === null) ? <Route path="*" element={<Login iniciarSesion={iniciarSession}/>}/> :
                    <>
                        <Route element={<BarraDeNavegacion cerrarSession={cerrarSession}/>}>
                            <Route path="/" element={<Navigate to="/principal"/>}/>
                            <Route path="/principal" element={<Principal/>}/>
                        </Route>
                        <Route path={"*"} element={<E404/>}/>
                    </>
                }
            </Routes>
        </BrowserRouter>
    );
}
