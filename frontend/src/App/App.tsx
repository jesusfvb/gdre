import {createContext, ReactElement, useEffect, useState} from "react";
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
import Cuarteleria from "./paginas/Cuarteleria";
import Guardia from "./paginas/Guardia";
import Usuario from "./paginas/Usuario";
import Integrantes from "./paginas/Integrantes";
import isJwtTokenExpired, {decode} from 'jwt-check-expiry';
import {useSnackbar} from "notistack";

interface InterfaceDatosUser {
    id: number,
    nombre: string,
    usuario: string,
    edificio: string,
    apartamento: string,
}

export const DatosUser = createContext<InterfaceDatosUser>({
    id: -1,
    nombre: "",
    usuario: "",
    edificio: "",
    apartamento: "",
})
export const IsRole = createContext<{
    isRolRender: Function,
    isRolBoolean: Function,
}>({
    isRolRender: () => {
    },
    isRolBoolean: () => {
    }
})

export default function App(): ReactElement {
    axios.defaults.baseURL = 'http://localhost:8080';
    const body = document.getElementsByTagName("body")[0]
    const {enqueueSnackbar} = useSnackbar();
    const [session, setSession] = useState<boolean | null>(null)
    const [datosUser, setDatosUser] = useState<InterfaceDatosUser>({
        id: -1,
        nombre: "",
        usuario: "",
        edificio: "",
        apartamento: "",
    })
    const [roles, setRoles] = useState<Array<{ authority: string }>>([])

    const iniciarSession = (usuario: string, contrasenna: string): void => {
        axios
            .post("/login", {
                usuario: usuario,
                contrasenna: contrasenna
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            })
            .then(datos => {
                localStorage.setItem("jwt", datos.data)
                procesarToken(datos.data)
            })
            .catch(() => {
                enqueueSnackbar("Usuario o Contraseña Incorrecta")
            })
    }
    const cerrarSession = () => {
        localStorage.clear()
        sessionFalse()
    }

    const sessionTrue = (jwt: string) => {
        body.style.background = "none";
        axios.defaults.headers.common['Authorization'] = jwt;
        setSession(true)
    }
    const sessionFalse = () => {
        body.style.backgroundImage = `url(${img_2})`;
        body.style.backgroundRepeat = "no-repeat";
        body.style.backgroundSize = "cover";
        setSession(false)
    }

    const procesarToken = (jwt: string) => {
        try {
            let jwtDecode: any = decode(jwt).payload;
            if (!isJwtTokenExpired(jwt)) {
                setDatosUser({
                    id: jwtDecode.id,
                    nombre: jwtDecode.nombre,
                    usuario: jwtDecode.sub,
                    edificio: (jwtDecode.edificio === null) ? "N/A" : jwtDecode.edificio,
                    apartamento: (jwtDecode.apartamento === null) ? "N/A" : jwtDecode.apartament,
                })
                setRoles(jwtDecode.roles)
                sessionTrue(jwt)
            } else {
                sessionFalse()
            }
        } catch (e) {
            sessionFalse()
        }
    }

    const isRolRenderF = (rol: string | Array<string>, render: ReactElement): ReactElement | null => {
        let salida: ReactElement | null = null;
        if (roles.length !== 0) {
            if (typeof rol === "string") {
                if (roles.some(value => value.authority === rol)) {
                    salida = render;
                }
            } else {
                rol.forEach(value => {
                    if (roles.some(value1 => value1.authority === value)) {
                        salida = render;
                        return;
                    }
                });
            }
        }
        return salida;
    }
    const isRolBooleanF = (rol: string | Array<string>): boolean => {
        let salida: boolean = false;
        if (roles.length !== 0) {
            if (typeof rol === "string") {
                if (roles.some(value => value.authority === rol)) {
                    salida = true;
                }
            } else {
                rol.forEach(value => {
                    if (roles.some(value1 => value1.authority === value)) {
                        salida = true;
                        return;
                    }
                });
            }
        }
        return salida;
    }

    useEffect(() => {
        let jwt = localStorage.getItem("jwt")
        axios.defaults.headers.common["Content-Type"] = "application/json, text/plain, */*"
        if (jwt !== null) {
            procesarToken(jwt)
        } else {
            sessionFalse()
        }
    }, [])
    return (
        <DatosUser.Provider value={datosUser}>
            <IsRole.Provider value={{isRolRender: isRolRenderF, isRolBoolean: isRolBooleanF}}>
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
                                    <Route path="/cuarteleria" element={<Cuarteleria/>}/>
                                    <Route path="/guardia" element={<Guardia/>}/>
                                    <Route path="/guardia/:id/integrantes" element={<Integrantes/>}/>
                                    <Route path="/usuario" element={<Usuario/>}/>
                                </Route>
                                <Route path={"*"} element={<E404/>}/>
                            </>
                        }
                    </Routes>
                </BrowserRouter>
            </IsRole.Provider>
        </DatosUser.Provider>
    );
}
