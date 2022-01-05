import {Grid} from "@mui/material";
import robot404 from "../img/robot404.jpg";

export default function Error404() {

    return (
        <Grid
            container
            direction="column"
            justifyContent="flex-start"
            alignItems="flex-start"
        >
            <Grid
                item
                container
                direction="row"
                justifyContent="space-around"
                alignItems="center"
            >
                <Grid item>
                    <img src={robot404} width={400} alt="robot404"/>
                </Grid>

                <Grid item>
                    <h1 style={{color: "red"}}>UPS!</h1>
                    <h2 style={{color: "black"}}>Pagina no encontrada</h2>
                    <p>
                        Parece que ha habido un error con la pagina que estabas buscando.
                    </p>
                    <p>
                        Es posible que la entrada haya sido eliminada o pueda que la
                        dirección no exista.
                    </p>
                </Grid>
            </Grid>
        </Grid>
    );
}
