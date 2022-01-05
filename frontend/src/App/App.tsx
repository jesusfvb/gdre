import {ReactElement, useState} from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Login from "./paginas/Login";
import axios from "axios";
import img_2 from "./img/identidad/img_2.png";
import Principal from "./paginas/Principal";
import E404 from "./paginas/E404";
import Marco from "./componentes/Marco";
import Ubicacion from "./paginas/ubicacion/Ubicacion";
import Edificio from "./paginas/ubicacion/Edificio";
import Apartamento from "./paginas/ubicacion/Apartamento";

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
        axios.defaults.headers.common['Authorization'] = session;
    }
    return (
        <BrowserRouter>
            <Routes>
                {(session === null) ? <Route path="*" element={<Login iniciarSession={iniciarSession}/>}/> :
                    <>
                        <Route element={<Marco cerrarSession={cerrarSession}/>}>
                            <Route path="/" element={<Navigate to="/principal"/>}/>
                            <Route path="/principal" element={<Principal/>}/>
                            <Route element={<Ubicacion/>}>
                                <Route path="/ubicacion" element={<h1>Personas</h1>}/>
                                <Route path="/ubicacion/residencias" element={<Edificio/>}/>
                                <Route path="/ubicacion/residencias/:id/apartamento" element={<Apartamento/>}/>
                            </Route>
                        </Route>
                        <Route path={"*"} element={<E404/>}/>
                    </>
                }
            </Routes>
        </BrowserRouter>
    );
}
