import {ChangeEvent, MouseEvent, ReactElement, SyntheticEvent, useEffect, useState} from "react";
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
    ButtonGroup,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField
} from "@mui/material";
import {Add, Delete, Update} from "@mui/icons-material";
import axios from "axios";

export default function Guardia(): ReactElement {
    const [option, setOption] = useState<number>(1)
    const columns: GridColumns = [
        {
            field: "coordinador",
            headerName: "Coordinador",
            type: "string",
            flex: 1,
            hide: (option === 1),
            filterable: (option !== 1),
            renderCell: params => (params.value?.nombre)
        },
        {
            field: "fecha",
            headerName: "Fecha",
            type: "date",
            flex: 1
        },
        {
            field: "id",
            headerName: "Acciones",
            type: "date",
            minWidth: 130,
            filterable: false,
            renderCell: (params) => (
                <>
                    <IconButton color="primary" onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color="error" onClick={borrar(params.value)}>
                        <Delete/>
                    </IconButton>
                </>
            )
        }
    ]
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [value, setValue] = useState<{ coordinador: any | null, fecha: string  }>({
        coordinador: null,
        fecha: ""
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])


    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (id !== undefined) {
            let guardia = rows.find(row => row.id === id)
            setValue({coordinador: guardia.coordinador, fecha: guardia.fecha})
        }
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValue({coordinador: null, fecha: ""})
        setOpen({open: false, id: undefined});
    };

    const handleChangeFecha = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({...value, fecha: evento.target.value});
    }

    const save = () => {
        if (open.id !== undefined) {
            axios
                .put((option === 1) ? "/guardia/residencia" : "/guardia/docente", (option === 1) ? {
                    id: open.id,
                    fecha: value.fecha,
                } : {
                    id: open.id,
                    idCoordinador: value.coordinador.id,
                    fecha: value.fecha
                })
                .then(response => {
                    let newRows = [...rows]
                    newRows[rows.findIndex(row => row.id === open.id)] = response.data
                    setRows(newRows)
                    handleClose()
                })
                .catch(error => console.error(error))
        } else {
            axios
                .post((option === 1) ? "/guardia/residencia" : "/guardia/docente", (option === 1) ? {
                    fecha: value.fecha,
                } : {
                    idCoordinador: value.coordinador.id,
                    fecha: value.fecha
                })
                .then(response => {
                    setRows([...rows, response.data])
                    handleClose()
                })
                .catch((error) => console.error(error))
        }
    }
    const borrar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/guardia", {data: (id !== undefined) ? [id] : selected})
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

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChange = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            setValue({...value, coordinador: newValue})
        }
        useEffect(() => {
            if (loading) {
                axios
                    .get("/usuario/ubicados")
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
                value={value.coordinador}
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
                        label="Coordinador"
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
                    <Button variant={(option === 1) ? "contained" : "outlined"} onClick={() => setOption(1)}>
                        Residencia
                    </Button>
                    <Button variant={(option === 2) ? "contained" : "outlined"} onClick={() => setOption(2)}>
                        Docente
                    </Button>
                </ButtonGroup>
                <Box sx={{flexGrow: 1}}/>
                <IconButton color={"success"} onClick={handleClickOpen()}>
                    <Add/>
                </IconButton>
                <IconButton color={"error"} onClick={borrar()} disabled={selected.length === 0}>
                    <Delete/>
                </IconButton>
            </GridToolbarContainer>
        )
    }

    useEffect(() => {
        axios
            .get((option === 1) ? "/guardia/residencia" : "/guardia/docente")
            .then((response) => setRows(response.data))
            .catch(error => console.error(error))
    }, [option])
    return (
        <div style={{height: "calc(100vh - 60px)"}}>
            <DataGrid columns={columns} rows={rows} components={{Toolbar: MyToolbar}} autoPageSize checkboxSelection
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Guardia</DialogTitle>
                <DialogContent>
                    {(option === 2) ? <MyAutocomplete/> : null}
                    <TextField type={"date"} label="Fecha" variant="outlined" fullWidth focused sx={{marginTop: 2}}
                               value={value.fecha} onChange={handleChangeFecha}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}