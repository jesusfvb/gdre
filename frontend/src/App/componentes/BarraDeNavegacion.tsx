import {
    AppBar,
    Box,
    Button,
    CssBaseline,
    Grid,
    IconButton,
    Menu,
    MenuItem,
    Tab,
    Tabs,
    Toolbar,
    Typography
} from "@mui/material";
import {Fragment, MouseEvent, SyntheticEvent, useState} from "react";
import {Outlet} from "react-router";
import AccountCircle from '@mui/icons-material/AccountCircle';

export default function BarraDeNavegacion(props: { cerrarSession: Function }) {
    const menuId = 'primary-search-account-menu';
    const [value, setValue] = useState<number>(0);
    const [anchorEl, setAnchorEl] = useState<null | Element>(null);

    const isMenuOpen = Boolean(anchorEl);

    const handleChange = (event: SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    const handleProfileMenuOpen = (event: MouseEvent) => {
        setAnchorEl(event.currentTarget);
    };
    const handleMenuClose = () => {
        setAnchorEl(null);
    };
    const cerrarSession = (evento: MouseEvent) => {
        evento.preventDefault()
        props.cerrarSession()
    }

    return (
        <Fragment>
            <CssBaseline/>
            <AppBar>
                <Toolbar variant={"dense"}>
                    <Typography variant="h5">
                        GDRE
                    </Typography>
                    <Grid container direction="row" justifyContent={"space-around"} alignItems={"center"}
                          marginLeft={8}>
                        <Button disableElevation variant={"contained"}> Quienes somos </Button>
                        <Button disableElevation variant={"contained"}> Quienes somos </Button>
                        <Button disableElevation variant={"contained"}> Quienes somos </Button>
                    </Grid>
                    <Box sx={{flexGrow: 1}}/>
                    <Grid
                        container
                        direction="row"
                        justifyContent="flex-end"
                        alignItems="center">
                        <Typography>
                            jesusfvb
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
                            <MenuItem onClick={cerrarSession}>Salir</MenuItem>
                        </Menu>
                    </Grid>
                </Toolbar>
                <Box sx={{width: '100%', backgroundColor: 'background.paper'}}>
                    <Tabs value={value} onChange={handleChange}>
                        <Tab label="Inicio"/>
                        <Tab label="Opción 1"/>
                        <Tab label="Opción 2"/>
                    </Tabs>
                </Box>
            </AppBar>
            <Toolbar/>
            <Toolbar variant={"dense"}/>
            <Grid container direction="column">
                <Grid item>
                    <Outlet/>
                </Grid>
            </Grid>
        </Fragment>
    );
}
