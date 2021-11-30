import {ReactElement, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./paginas/Login";
import axios from "axios";

export default function App(): ReactElement {
    axios.defaults.baseURL = 'http://localhost:8080';

    const [autenticado, setAutenticado] = useState<string>()

    const iniciarSesion = (usuario: string, contrasenna: string): void => {
        const cuerpo = new FormData()
        cuerpo.append("usuario", usuario)
        cuerpo.append("usuario", usuario)
        axios
            .post("/login", cuerpo)
            .then(datos => console.log(datos))
            .catch(error => console.error(error))
    }

    return (
        <BrowserRouter>
            <Routes>
                <Route path="*" element={<Login iniciarSesion={iniciarSesion}/>}/>
            </Routes>
        </BrowserRouter>
    );
}