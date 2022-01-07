import {MouseEvent, ReactElement, useEffect, useState} from "react";
import {DataGrid, GridColumns, GridToolbarContainer, GridToolbarFilterButton} from "@mui/x-data-grid";
import {
    Autocomplete,
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
import {Check, Close, Delete, AddLocation} from "@mui/icons-material";
import axios from "axios";

export default function Personas(): ReactElement {
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
            renderCell: (param) => {
                switch (option) {
                    case 1:
                        return (
                            <IconButton color={"error"} onClick={desubicar(param.value)}>
                                <Delete/>
                            </IconButton>
                        )
                    case 2:
                        return (
                            <>
                                <IconButton color={"error"} onClick={handleClickOpen(param.value)}>
                                    <AddLocation/>
                                </IconButton>
                                <IconButton color={"secondary"} onClick={desconfirmar(param.value)}>
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
            })
    }
    const desubicar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desubicar", [id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
            })
            .catch(error => console.error(error))
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
            })
            .catch(error => console.error(error))
    }
    const desconfirmar = (id: number) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desconfirmar", [id])
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
            })
            .catch(error => console.error(error))
    }

    function MyAutocompleteEdificio(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

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
                onChange={(event, newValue) => setValueEdificio(newValue)}
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
                onChange={(event, newValue) => setValueApartamento(newValue)}
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
                onChange={(event, newValue) => setValueCuarto(newValue)}
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
                <ButtonGroup sx={{marginLeft: 1}} size={"small"}>
                    <Button variant={(option === 1) ? "contained" : "outlined"}
                            onClick={() => setOption(1)}>Ubicados</Button>
                    <Button variant={(option === 2) ? "contained" : "outlined"} onClick={() => setOption(2)}>No
                        Ubicados</Button>
                    <Button variant={(option === 3) ? "contained" : "outlined"} onClick={() => setOption(3)}>Por
                        Confirmar</Button>
                </ButtonGroup>
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [option])
    return (
        <>
            <DataGrid autoPageSize={true} density={"compact"} columns={columns} rows={rows} checkboxSelection
                      components={{
                          Toolbar: MyToolbar,
                      }}/>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Ubicar</DialogTitle>
                <DialogContent>
                    <MyAutocompleteEdificio/>
                    {(valueEdificio !== null) ? <MyAutocompleteApartamento/> : null}
                    {(valueApartamento !== null) ? <MyAutocompleteCuarto/> : null}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={ubicar}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </>
    )
}