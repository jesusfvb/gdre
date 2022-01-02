import {Tab, Tabs} from "@mui/material";
import {ReactElement, SyntheticEvent, useState} from "react";
import {useLocation, useNavigate, Outlet} from "react-router-dom"

export default function Ubicacion(): ReactElement {
    const location = useLocation()
    const navegate = useNavigate()
    const [valueTabs, setValue] = useState(location.pathname);
    const handleChangeTabs = (event: SyntheticEvent, newValue: string) => {
        setValue(newValue);
    };
    return (
        <div style={{height: "calc(100vh - 105px)"}}>
            <Tabs value={valueTabs} onChange={handleChangeTabs} aria-label="lab API tabs example">
                <Tab label="Personas" value="/ubicacion" onClick={() => navegate("/ubicacion")}/>
                <Tab label="Residencias" value="/ubicacion/residencias"
                     onClick={() => navegate("/ubicacion/residencias")}/>
            </Tabs>
            <Outlet/>
        </div>
    )
}