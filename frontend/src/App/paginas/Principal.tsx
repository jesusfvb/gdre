import {Card, CardActionArea, CardContent, CardMedia, Grid, Typography} from "@mui/material";
import ubicacion from '../img/ubicacion.jpg'
import guardia from '../img/guardia.jpg'
import cuarteleria from '../img/cuarteleria.jpg'

export default function Principal() {
    const sx = {
        card: {
            maxWidth: 345,
            marginTop: 7
        },
        images: {
            width: 300,
            height: 200
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
            <Grid item>
               Calendario
            </Grid>
        </Grid>
    )
}