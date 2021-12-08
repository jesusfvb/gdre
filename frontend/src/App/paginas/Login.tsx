import {
    Box, Button, Checkbox,
    Container,
    createTheme,
    CssBaseline, FormControlLabel,
    Grid, Link, Paper,
    TextField,
    ThemeProvider,
    Typography
} from "@mui/material";
import logo from '../img/logo.jpg'
import img_2 from '../img/identidad/img_2.png'
import {FormEvent} from "react";

export default function Login(props: { iniciarSesion: Function<void> }) {
    const theme = createTheme();

    const iniciarSesion = (evento: FormEvent<HTMLFormElement>) => {
        evento.preventDefault()
        let inputs = evento.currentTarget.getElementsByTagName("input")
        props.iniciarSesion(inputs[0].value, inputs[1].value)
    }

    function Copyright(props) {
        return (
            <Typography
                variant="body2"
                color="text.secondary"
                align="center"
                {...props}
            >
                {"Copyright © "}
                <Link color="inherit">
                    Your Website
                </Link>{" "}
                {new Date().getFullYear()}
                {"."}
            </Typography>
        );
    }

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <Paper elevation={10} sx={{padding: 3, marginTop: 10, borderRadius: 10}}>
                    <CssBaseline/>
                    <Box sx={{
                        display: "flex", flexDirection: "column", alignItems: "center",
                    }}>
                        <img src={logo} width={150} alt="logo"/>
                        <Box component="form" noValidate sx={{mt: 3}} onSubmit={iniciarSesion}>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField required fullWidth id="email" label="Usuario" name="email"/>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField required fullWidth name="password" label="Contraseña" type="password"
                                               id="password" autoComplete="new-password"/>
                                </Grid>
                                <Grid item xs={12}>
                                    <FormControlLabel control={<Checkbox color="primary"/>}
                                                      label="Recordar Contraseña"/>
                                </Grid>
                            </Grid>
                            <Button type="submit" fullWidth variant="contained" sx={{mt: 3, mb: 2}}>
                                Entrar
                            </Button>
                        </Box>
                    </Box>
                </Paper>
                <Copyright sx={{mt: "20vh"}}/>
            </Container>
        </ThemeProvider>
    );
}
