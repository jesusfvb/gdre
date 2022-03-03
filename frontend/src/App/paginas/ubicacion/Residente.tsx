import {MouseEvent, ReactElement, SyntheticEvent, useContext, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {
    DataGrid,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {
    Autocomplete,
    AutocompleteValue,
    Box,
    Button,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import {Add, Delete, NavigateBefore} from "@mui/icons-material";
import axios from "axios";
import {IsRole} from "../../App";
import {useSnackbar} from "notistack";

export default function Residente(): ReactElement {
    const navegate = useNavigate()
    const params = useParams()
    const {isRolRender, isRolBoolean} = useContext(IsRole)
    const {enqueueSnackbar} = useSnackbar();
    const columns: GridColumns = [
        {
            field: "nombre",
            flex: 1,
            type: "string",
            headerName: "Nombre",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 100,
            hide: !isRolBoolean(["Administrador", "Vicedecano"]),
            renderCell: (param) => (
                <IconButton color={"error"} onClick={handleClickOpenBorrar(param.value)}>
                    <Delete/>
                </IconButton>
            )
        }]
    const [value, setValue] = useState<any>()
    const [rows, setRows] = useState<Array<any>>([])
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [open, setOpen] = useState<boolean>(false);
    const [validate, setValidate] = useState<boolean>(true)
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});

    const handleClickOpenBorrar = (id: number | undefined = undefined) => (event: MouseEvent) => {
        event.stopPropagation();
        setBorrar({open: true, id: id});
    };
    const handleCloseBorrar = () => {
        setBorrar({open: false, id: undefined});
    };

    const handleClickOpen = (evento: MouseEvent) => {
        evento.stopPropagation()
        setOpen(true);
    };
    const handleClose = () => {
        setValue(null)
        setValidate(true)
        setOpen(false);
    };

    const getData = () => {
        axios
            .get("/usuario/cuarto", {params: {idCuarto: params.id}})
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const ubicar = () => {
        if (!validate) {
            axios
                .post("/usuario/ubicar", {
                    idCuarto: params.id,
                    idUsuario: value?.id
                })
                .then(response => {
                    setRows([...rows, response.data])
                    handleClose()
                    enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                }).catch(error => enqueueSnackbar("Error al realizar la Acción"))
        } else {
            enqueueSnackbar("Error al realizar la Acción")
        }
    }
    const desubicar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desubicar", (borrarAlert.id === undefined) ? selected : [borrarAlert.id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                handleCloseBorrar()
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChange = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate(false)
            } else {
                setValidate(true)
            }
            setValue(newValue)
        }
        useEffect(() => {
            if (loading) {
                axios
                    .get("/usuario/no_ubicados")
                    .then(response => {
                        setOptions(response.data)
                    })
                    .catch(error => console.error(error))
            }
        }, [loading]);

        return (
            <Autocomplete
                id="nombre"
                sx={{width: 300, paddingTop: 2}}
                open={open}
                value={value}
                onChange={handleChange}
                onOpen={() => {
                    setOpen(true);
                }}
                onClose={() => {
                    setOpen(false);
                }}
                isOptionEqualToValue={(option: any, value: any) => option.id === value.id}
                getOptionLabel={(option: any) => option.nombre}
                options={options}
                loading={loading}
                renderInput={(params: any) => (
                    <TextField
                        {...params}
                        label="Usuarios"
                        error={validate}
                        InputProps={{
                            ...params.InputProps,
                            endAdornment: (
                                <>
                                    {loading ? <CircularProgress color="inherit" size={20}/> : null}
                                    {params.InputProps.endAdornment}
                                </>
                            ),
                        }}
                    />
                )}
            />
        );
    }

    function MyToolbar(): ReactElement {
        return (
            <GridToolbarContainer>
                <IconButton color={"secondary"}
                            onClick={() => navegate(`/ubicacion/residencias/${params.idEdificio}/apartamento/${params.idApartamento}/cuarto`)}>
                    <NavigateBefore/>
                    <Typography variant={"subtitle1"}>Cuarto</Typography>
                </IconButton>
                <GridToolbarFilterButton/>
                <Typography variant={"subtitle1"} sx={{marginLeft: 1}}>Residentes</Typography>
                <Box sx={{flexGrow: 1}}/>
                {isRolRender("Administrador",
                    <>
                        <IconButton onClick={handleClickOpen}>
                            <Add color={"success"}/>
                        </IconButton>
                        <IconButton onClick={handleClickOpenBorrar()} disabled={selected.length === 0} color={"error"}>
                            <Delete/>
                        </IconButton>
                    </>
                )}
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [params.id])
    return (
        <>
            <DataGrid autoPageSize={true} density={"compact"} columns={columns} rows={rows}
                      checkboxSelection={isRolBoolean(["Administrador", "Vicedecano"])}
                      disableSelectionOnClick={!isRolBoolean(["Administrador", "Vicedecano"])}
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}
                      components={{
                          Toolbar: MyToolbar,
                      }}/>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Ubicar</DialogTitle>
                <DialogContent>
                    <MyAutocomplete/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={ubicar}>Aceptar</Button>
                    <Button onClick={handleClose} color={"error"}>Cancel</Button>
                </DialogActions>
            </Dialog>
            <Dialog
                open={borrarAlert.open}
                onClose={handleCloseBorrar}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    Quitar Ubicación
                </DialogTitle>
                <DialogContent>
                    <DialogContent id="alert-dialog-description">
                        Desea Continuar la Acción
                    </DialogContent>
                </DialogContent>
                <DialogActions>
                    <Button onClick={desubicar}>Acepar</Button>
                    <Button onClick={handleCloseBorrar} color={"error"}> Cancelar </Button>
                </DialogActions>
            </Dialog>
        </>
    )
}