import {ReactElement, useState} from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Login from "./paginas/Login";
import axios from "axios";
import img_2 from "./img/identidad/img_2.png";
import BarraDeNavegacion from "./componentes/BarraDeNavegacion";
import Principal from "./paginas/Principal";

export default function App(): ReactElement {
    axios.defaults.baseURL = 'http://localhost:8080';
    const body = document.getElementsByTagName("body")[0]
    const [sesion, setSesion] = useState<string | null>(localStorage.getItem("jwt"))

    const iniciarSesion = (usuario: string, contrasenna: string): void => {
        axios
            .post("/login", {
                usuario: usuario,
                contrasenna: contrasenna
            })
            .then(datos => {
                localStorage.setItem("jwt", datos.data)
                setSesion(datos.data)
            })
            .catch(error => console.error(error))
    }
    const cerrarSesion = () => {
        localStorage.clear()
        setSesion(null)
    }

    if (sesion === null) {
        body.style.backgroundImage = `url(${img_2})`;
        body.style.backgroundRepeat = "no-repeat";
        body.style.backgroundSize = "cover";
    } else {
        body.style.background = "none";
    }
    return (
        <BrowserRouter>
            <Routes>
                {(sesion === null) ? <Route path="*" element={<Login iniciarSesion={iniciarSesion}/>}/> :
                    <Route element={<BarraDeNavegacion cerrarSesion={cerrarSesion}/>}>
                        <Route path="/" element={<Navigate to="/principal"/>}/>
                        <Route path="/principal" element={<Principal/>}/>
                    </Route>
                }
            </Routes>
        </BrowserRouter>
    );
}