import {ReactElement, useEffect, useState} from "react";
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
import Cuarto from "./paginas/ubicacion/Cuarto";
import Residente from "./paginas/ubicacion/Residente";
import Personas from "./paginas/ubicacion/Personas";

export default function App(): ReactElement {
    axios.defaults.baseURL = 'http://localhost:8080';
    const [session, setSession] = useState<boolean | null>(null)

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
        setSession(false)
    }

    useEffect(() => {
        const body = document.getElementsByTagName("body")[0]
        let jwt = localStorage.getItem("jwt")
        axios.defaults.headers.common["Content-Type"] = "application/json, text/plain, */*"
        if (jwt !== null) {
            axios
                .put("/login", {token: jwt})
                .then(response => {
                    if (response.data) {
                        body.style.backgroundImage = `url(${img_2})`;
                        body.style.backgroundRepeat = "no-repeat";
                        body.style.backgroundSize = "cover";
                        setSession(false)
                    } else {
                        body.style.background = "none";
                        axios.defaults.headers.common['Authorization'] = jwt + "";
                        setSession(true)
                    }
                })
                .catch(error => console.error(error))
        } else {
            body.style.backgroundImage = `url(${img_2})`;
            body.style.backgroundRepeat = "no-repeat";
            body.style.backgroundSize = "cover";
            setSession(false)
        }
    }, [session])
    return (
        <BrowserRouter>
            <Routes>
                {session === null ? <Route path="*" element={null}/> : !session ?
                    <Route path="*" element={<Login iniciarSession={iniciarSession}/>}/> :
                    <>
                        <Route element={<Marco cerrarSession={cerrarSession}/>}>
                            <Route path="/" element={<Navigate to="/principal"/>}/>
                            <Route path="/principal" element={<Principal/>}/>
                            <Route element={<Ubicacion/>}>
                                <Route path="/ubicacion" element={<Personas/>}/>
                                <Route path="/ubicacion/residencias" element={<Edificio/>}/>
                                <Route path="/ubicacion/residencias/:id/apartamento" element={<Apartamento/>}/>
                                <Route path="/ubicacion/residencias/:idEdificio/apartamento/:id/cuarto"
                                       element={<Cuarto/>}/>
                                <Route
                                    path="/ubicacion/residencias/:idEdificio/apartamento/:idApartamento/cuarto/:id/residente"
                                    element={<Residente/>}/>
                            </Route>
                        </Route>
                        <Route path={"*"} element={<E404/>}/>
                    </>
                }
            </Routes>
        </BrowserRouter>
    );
}
