import {Avatar, Button, Grid, Paper, Typography} from "@mui/material";
import {Outlet, useNavigate} from "react-router-dom"
import BarraDeNavegacion from "./BarraDeNavegacion";

export default function Marco(props: { cerrarSession: Function }) {
    const navigation = useNavigate()
    return (
        <Grid container direction={"column"}>
            <Grid item sx={{marginBottom: 1}}>
                <BarraDeNavegacion cerrarSession={props.cerrarSession}/>
            </Grid>
            <Grid item container direction={"row"}>
                <Grid item container direction={"column"} xl={2} lg={2} md={3} sm={3} xs={5} alignItems={"center"}>
                    <Paper elevation={2} sx={{margin: 2}}>
                        <Grid item container justifyContent={"center"} alignItems={"center"} direction={"column"}
                              paddingTop={2} paddingBottom={2}>
                            <Avatar sx={{width: 90, height: 90}}/>
                            <Typography variant={"h5"}>jesusfvb</Typography>
                            <Grid container item justifyContent={"center"} sx={{marginTop: 2}}>
                                <Typography variant={"overline"}> Dirección </Typography>
                                <Grid container item justifyContent={"space-between"} sx={{padding: "0px 30px"}}>
                                    <Typography>Manzana:</Typography>
                                    <Typography>2</Typography>
                                </Grid>
                                <Grid container item justifyContent={"space-between"} sx={{padding: "0px 30px"}}>
                                    <Typography>Edificio:</Typography>
                                    <Typography>2</Typography>
                                </Grid>
                                <Grid container item justifyContent={"space-between"} sx={{padding: "0px 30px"}}>
                                    <Typography>Apartamento:</Typography>
                                    <Typography>2</Typography>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Paper>
                    <Grid item sx={{width: "100%", marginBottom: 1}}>
                        <Button variant={"contained"} size={"small"}
                                sx={{marginLeft: 1, width: "93%"}}>Principal</Button>
                    </Grid>
                    <Grid item sx={{width: "100%", marginBottom: 1}}>
                        <Button variant={"contained"} size={"small"}
                                sx={{marginLeft: 1, width: "93%"}}>Cuarteleria</Button>
                    </Grid>
                    <Grid item sx={{width: "100%", marginBottom: 1}}>
                        <Button variant={"contained"} size={"small"} sx={{marginLeft: 1, width: "93%"}}>Guardia</Button>
                    </Grid>
                    <Grid item sx={{width: "100%", marginBottom: 1}}>
                        <Button variant={"contained"} size={"small"} onClick={() => navigation("/ubicacion")}
                                sx={{marginLeft: 1, width: "93%"}}>Ubicación</Button>
                    </Grid>
                </Grid>
                <Grid item xl={true} lg={true} md={true} sm={true} xs={true} sx={{borderLeft: "solid 1px"}}>
                    <Outlet/>
                </Grid>
            </Grid>
        </Grid>
    )
}