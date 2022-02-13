import {MouseEvent, ReactElement, SyntheticEvent, useContext, useEffect, useState} from "react";
import {DataGrid, GridColumns, GridToolbarContainer, GridToolbarFilterButton} from "@mui/x-data-grid";
import {
    Autocomplete,
    AutocompleteValue,
    Button,
    ButtonGroup,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField
} from "@mui/material";
import {AddLocation, Check, Close, Delete} from "@mui/icons-material";
import axios from "axios";
import {IsRole} from "../../App";
import {useSnackbar} from "notistack";

export default function Personas(): ReactElement {
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
            field: "edificio",
            flex: 1,
            type: "number",
            headerName: "Edificio",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "apartamento",
            flex: 1,
            type: "number",
            headerName: "Apartamento",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "cuarto",
            flex: 1,
            type: "number",
            headerName: "Cuarto",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 100,
            hide: !isRolBoolean("Administrador"),
            renderCell: (param) => {
                switch (option) {
                    case 1:
                        return (
                            <IconButton color={"error"} onClick={handleClickOpenBorrar(param.value)}>
                                <Delete/>
                            </IconButton>
                        )
                    case 2:
                        return (
                            <>
                                <IconButton color={"error"} onClick={handleClickOpen(param.value)}>
                                    <AddLocation/>
                                </IconButton>
                                <IconButton color={"secondary"} onClick={handleClickOpenBorrar(param.value)}>
                                    <Close/>
                                </IconButton>
                            </>
                        )
                    case 3:
                        return (
                            <IconButton color={"primary"} onClick={confirmar(param.value)}>
                                <Check/>
                            </IconButton>
                        )
                }
            }
        }]
    const [id, setId] = useState<number | null>(null)
    const [valueEdificio, setValueEdificio] = useState<any | null>(null)
    const [valueApartamento, setValueApartamento] = useState<any | null>(null)
    const [valueCuarto, setValueCuarto] = useState<any | null>(null)
    const [option, setOption] = useState<1 | 2 | 3>(1)
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<boolean>(false);
    const [validate, setValidate] = useState<{ edificio: boolean, apartamento: boolean, cuarto: boolean }>({
        edificio: true,
        apartamento: true,
        cuarto: true
    })
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});

    const handleClickOpenBorrar = (id: number | undefined = undefined) => (event: MouseEvent) => {
        event.stopPropagation();
        setBorrar({open: true, id: id});
    };
    const handleCloseBorrar = () => {
        setBorrar({open: false, id: undefined});
    };

    const handleClickOpen = (id: number) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setId(id)
        setOpen(true);
    };
    const handleClose = () => {
        setId(null)
        setValueEdificio(null)
        setValueApartamento(null)
        setValueCuarto(null)
        setValidate({
            edificio: true,
            apartamento: true,
            cuarto: true
        })
        setOpen(false);
    };

    const getData = () => {
        let url = ""
        switch (option) {
            case 1:
                url = "/usuario/ubicados"
                break
            case 2:
                url = "/usuario/no_ubicados"
                break
            case 3:
                url = "/usuario/por_confirmar"
                break
        }
        axios
            .get(url)
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const ubicar = () => {
        if (!validate.apartamento && !validate.edificio && !validate.cuarto) {
            axios
                .post("/usuario/ubicar", {
                    idCuarto: valueCuarto.id,
                    idUsuario: id
                })
                .then(response => {
                    let newRows = [...rows]
                    newRows.splice(newRows.findIndex((value) => value.id === response.data.id), 1)
                    setRows(newRows)
                    handleClose()
                    enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                }).catch(error => enqueueSnackbar("Error al realizar la Acción"))

        }
    }
    const desubicar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desubicar", [borrarAlert.id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
                handleCloseBorrar()
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }
    const confirmar = (id: number) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/confirmar", [id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }
    const desconfirmar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desconfirmar", [borrarAlert.id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
                handleCloseBorrar()
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }

    function MyAutocompleteEdificio(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChangeEdificio = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate({...validate, edificio: false})
                setValueApartamento(null)
                setValueCuarto(null)
            } else {
                setValidate({...validate, edificio: true})
            }
            setValueEdificio(newValue)
        }

        useEffect(() => {
            if (loading) {
                axios
                    .get("/edificio")
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
                value={valueEdificio}
                onChange={handleChangeEdificio}
                onOpen={() => {
                    setOpen(true);
                }}
                onClose={() => {
                    setOpen(false);
                }}
                isOptionEqualToValue={(option: any, value: any) => option.id === value.id}
                getOptionLabel={(option: any) => option.numero + ""}
                options={options}
                loading={loading}
                renderInput={(params: any) => (
                    <TextField
                        {...params}
                        label="Edificio"
                        error={validate.edificio}
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

    function MyAutocompleteApartamento(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChangeApartamento = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate({...validate, apartamento: false})
                setValueCuarto(null)
            } else {
                setValidate({...validate, apartamento: true})
            }
            setValueApartamento(newValue)
        }

        useEffect(() => {
            if (loading) {
                axios
                    .get("/apartamento", {
                        params: {
                            id: valueEdificio.id
                        }
                    })
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
                value={valueApartamento}
                onChange={handleChangeApartamento}
                onOpen={() => {
                    setOpen(true);
                }}
                onClose={() => {
                    setOpen(false);
                }}
                isOptionEqualToValue={(option: any, value: any) => option.id === value.id}
                getOptionLabel={(option: any) => option.numero + ""}
                options={options}
                loading={loading}
                renderInput={(params: any) => (
                    <TextField
                        {...params}
                        label="Apartamento"
                        error={validate.apartamento}
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

    function MyAutocompleteCuarto(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChangCuarto = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate({...validate, cuarto: false})
            } else {
                setValidate({...validate, cuarto: true})
            }
            setValueCuarto(newValue)
        }

        useEffect(() => {
            if (loading) {
                axios
                    .get("/cuarto", {
                        params: {
                            id: valueApartamento.id
                        }
                    })
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
                value={valueCuarto}
                onChange={handleChangCuarto}
                onOpen={() => {
                    setOpen(true);
                }}
                onClose={() => {
                    setOpen(false);
                }}
                isOptionEqualToValue={(option: any, value: any) => option.id === value.id}
                getOptionLabel={(option: any) => option.numero + ""}
                options={options}
                loading={loading}
                renderInput={(params: any) => (
                    <TextField
                        {...params}
                        label="Cuarto"
                        error={validate.cuarto}
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
                <GridToolbarFilterButton/>
                {
                    isRolRender("Administrador",
                        <ButtonGroup sx={{marginLeft: 1}} size={"small"}>
                            <Button variant={(option === 1) ? "contained" : "outlined"}
                                    onClick={() => setOption(1)}>Ubicados</Button>
                            <Button variant={(option === 2) ? "contained" : "outlined"} onClick={() => setOption(2)}>No
                                Ubicados</Button>
                            <Button variant={(option === 3) ? "contained" : "outlined"} onClick={() => setOption(3)}>Por
                                Confirmar</Button>
                        </ButtonGroup>
                    )
                }
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [option])
    return (
        <>
            <DataGrid autoPageSize={true} density={"compact"} columns={columns} rows={rows}
                      components={{
                          Toolbar: MyToolbar,
                      }}/>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Ubicar</DialogTitle>
                <DialogContent>
                    <MyAutocompleteEdificio/>
                    {(valueEdificio !== null) ? <MyAutocompleteApartamento/> : null}
                    {(valueApartamento !== null && valueEdificio !== null) ? <MyAutocompleteCuarto/> : null}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={ubicar}>Aceptar</Button>
                </DialogActions>
            </Dialog>
            <Dialog
                open={borrarAlert.open}
                onClose={handleCloseBorrar}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    Borrar
                </DialogTitle>
                <DialogContent>
                    <DialogContent id="alert-dialog-description">
                        Desea Continuar la Acción
                    </DialogContent>
                </DialogContent>
                <DialogActions>
                    {(option === 2) ?
                        <Button onClick={desconfirmar}>Acepar</Button> :
                        <Button onClick={desubicar}>Acepar</Button>}
                    <Button onClick={handleCloseBorrar} color={"error"}> Cancelar </Button>
                </DialogActions>
            </Dialog>
        </>
    )
}