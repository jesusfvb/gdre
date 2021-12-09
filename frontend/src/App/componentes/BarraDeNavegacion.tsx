import {
    AppBar,
    Box,
    Button,
    ButtonGroup,
    CssBaseline,
    Grid,
    IconButton,
    Menu,
    MenuItem,
    Toolbar,
    Typography
} from "@mui/material";
import {Fragment, MouseEvent, useRef, useState} from "react";
import {Outlet} from "react-router";
import AccountCircle from '@mui/icons-material/AccountCircle';

export default function BarraDeNavegacion(props: { cerrarSesion: Function }) {
    const menuId = 'primary-search-account-menu';
    const [anchorEl, setAnchorEl] = useState<null | Element>(null);

    const isMenuOpen = Boolean(anchorEl);

    const handleProfileMenuOpen = (event: MouseEvent) => {
        setAnchorEl(event.currentTarget);
    };
    const handleMenuClose = () => {
        setAnchorEl(null);
    };
    const cerrarSesion = (evento: MouseEvent) => {
        props.cerrarSesion()
    }

    return (
        <Fragment>
            <CssBaseline/>
            <AppBar>
                <Toolbar>
                    <Typography variant="h4">
                        GDRE
                    </Typography>
                    <ButtonGroup disableElevation variant="contained" sx={{marginLeft: 5}}>
                        <Button>Residencia</Button>
                        <Button>Guardia</Button>
                        <Button>Cuarteleria</Button>
                    </ButtonGroup>
                    <Box sx={{flexGrow: 1}}/>
                    <Grid
                        container
                        direction="row"
                        justifyContent="flex-end"
                        alignItems="center">
                        <Typography>
                            juliocbr
                        </Typography>
                        <IconButton
                            size="large"
                            edge="end"
                            aria-label="account of current user"
                            aria-controls={menuId}
                            aria-haspopup="true"
                            onClick={handleProfileMenuOpen}
                            color="inherit"
                        >
                            <AccountCircle/>
                        </IconButton>
                        <Menu anchorEl={anchorEl} id={menuId} keepMounted open={isMenuOpen}
                              onClose={handleMenuClose}
                              transformOrigin={{
                                  vertical: 'top',
                                  horizontal: 'right',
                              }}
                              anchorOrigin={{
                                  vertical: 'top',
                                  horizontal: 'right'
                              }}
                        >
                            <MenuItem onClick={cerrarSesion}>Salir</MenuItem>
                        </Menu>
                    </Grid>
                </Toolbar>
            </AppBar>
            <Toolbar/>
            <Grid container direction="column">
                <Grid item>
                    <Outlet/>
                </Grid>
            </Grid>
        </Fragment>
    );
}
