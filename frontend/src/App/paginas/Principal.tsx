import {Avatar, Card, CardActionArea, CardContent, CardMedia, Grid, Paper, Typography} from "@mui/material";
import ubicacion from '../img/ubicacion.jpg'
import guardia from '../img/guardia.jpg'
import cuarteleria from '../img/cuarteleria.jpg'
import Calendario from "../componentes/Calendario";

export default function Principal() {
    const sx = {
        card: {
            maxWidth: 345,
            marginTop: 3,
            marginBottom: 2
        },
        images: {
            width: 300,
            height: 200
        },
        info: {
            marginTop: 2
        }
    }

    return (
        <Grid container direction="column" justifyContent="flex-start" alignItems="flex-start">
            <Grid item container direction="row" justifyContent="space-around" alignItems="center">
                <Grid item>
                    <Card sx={sx.card}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="140"
                                image={ubicacion}
                                alt="ubicación"
                                sx={sx.images}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Residencia
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item>
                    <Card sx={sx.card}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="140"
                                image={guardia}
                                alt="guardia"
                                sx={sx.images}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Guardia
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item>
                    <Card sx={sx.card}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="140"
                                image={cuarteleria}
                                alt="cuarteleria"
                                sx={sx.images}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Cuarteleria
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
            </Grid>
            <Grid item container direction="row" justifyContent="space-around" alignItems="center">
                <Grid item component={Paper} container direction="column" justifyContent="center"
                      alignItems="center" sx={{width: 300, margin: 2, padding: "30px 50px"}}>
                    < Avatar sx={{width: 90, height: 90}}/>
                    <Grid item container direction="row" justifyContent="space-between" alignItems="center"
                          sx={sx.info}>
                        <Typography>Manzana:</Typography>
                        <Typography>4</Typography>
                    </Grid>
                    <Grid item container direction="row" justifyContent="space-between" alignItems="center"
                          sx={sx.info}>
                        <Typography>Edificio:</Typography>
                        <Typography>44</Typography>
                    </Grid>
                    <Grid item container direction="row" justifyContent="space-between" alignItems="center"
                          sx={sx.info}>
                        <Typography>Apartamento:</Typography>
                        <Typography>202</Typography>
                    </Grid>
                </Grid>
                <Calendario/>
            </Grid>
        </Grid>
    )
}