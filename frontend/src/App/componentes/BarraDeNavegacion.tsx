import {AppBar, Box, Button, CssBaseline, Toolbar, Typography} from "@mui/material";
import {Fragment} from "react";

export default function BarraDeNavegacion(props: { cerrarSession: Function }) {
    return (
        <Fragment>
            <CssBaseline/>
            <AppBar>
                <Toolbar variant={"dense"}>
                    <Typography variant="h5">
                        GDRE
                    </Typography>
                    <Box sx={{flexGrow: 1}}/>
                    <Button disableElevation variant={"contained"}> Quienes somos </Button>
                    <Button onClick={() => props.cerrarSession()} variant={"contained"} color={"error"}>Salir</Button>
                </Toolbar>
            </AppBar>
            <Toolbar variant={"dense"}/>
        </Fragment>
    );
}
