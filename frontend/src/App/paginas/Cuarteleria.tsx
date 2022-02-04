import {MouseEvent, ReactElement, SyntheticEvent, useEffect, useState, ChangeEvent} from "react";
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
    Button, CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, FormControl,
    IconButton, InputLabel, MenuItem, Select, SelectChangeEvent,
    TextField
} from "@mui/material";
import {Add, Check, Delete, Update} from "@mui/icons-material";
import axios from "axios";

export default function Cuarteleria(): ReactElement {
    const columns: GridColumns = [
        {
            field: "nombre",
            headerName: "Nombre",
            type: "string",
            flex: 1
        },
        {
            field: "fecha",
            headerName: "Fecha",
            type: "date",
            flex: 1
        },
        {
            field: "evaluacion",
            headerName: "Evaluación",
            type: "date",
            flex: 1
        },
        {
            field: "id",
            headerName: "Acciones",
            type: "date",
            minWidth: 130,
            renderCell: (params) => (
                <>
                    <IconButton color="primary" onClick={handleClickOpenEvaluacion(params.value)}>
                        <Check/>
                    </IconButton>
                    <IconButton color="primary" onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color="error" onClick={borrar(params.value)}>
                        <Delete/>
                    </IconButton>
                </>
            )
        }]
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [value, setValue] = useState<{ usuario: any | null, fecha: string | null }>({usuario: null, fecha: null})
    const [evaluacion, setEvaluacion] = useState<{ open: boolean, id: number | undefined, evaluacion: string }>({
        open: false,
        id: undefined,
        evaluacion: ""
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValue({usuario: null, fecha: null})
        setOpen({open: false, id: undefined});
    };

    const handleClickOpenEvaluacion = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setEvaluacion({open: true, id: id, evaluacion: ""});
    };
    const handleCloseEvaluacion = () => {
        setEvaluacion({open: false, id: undefined, evaluacion: ""});
    };

    const handleChangeFecha = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({...value, fecha: evento.target.value});
    }
    const handleChangeEvaluacion = (evento: SelectChangeEvent) => {
        setEvaluacion({...evaluacion, evaluacion: evento.target.value as string})
    }

    const save = () => {
        if (open.id !== undefined) {
            axios
                .put("/cuarteleria", {
                    id: open.id,
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
                .post("/cuarteleria", {
                    idUsuario: value.usuario.id,
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
            .delete("/cuarteleria", {data: (id !== undefined) ? [id] : selected})
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
    const evaluar = () => {
        axios
            .put("/cuarteleria/evaluar", {id: evaluacion.id, evaluacion: evaluacion.evaluacion})
            .then(response => {
                let newRows = [...rows]
                newRows[rows.findIndex(row => row.id === response.data.id)] = response.data
                setRows(newRows)
                handleCloseEvaluacion()
            })
            .catch(error => console.error(error))
        handleCloseEvaluacion()
    }

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChange = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            setValue({...value, usuario: newValue})
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
                value={value.usuario}
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
            .get("/cuarteleria")
            .then((response) => setRows(response.data))
            .catch(error => console.error(error))
    }, [])
    return (
        <div style={{height: "calc(100vh - 60px)"}}>
            <DataGrid columns={columns} rows={rows} components={{Toolbar: MyToolbar}} autoPageSize checkboxSelection
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Cuarteleria</DialogTitle>
                <DialogContent>
                    {(open.id === undefined) ? <MyAutocomplete/> : null}
                    <TextField type={"date"} label="Fecha" variant="outlined" fullWidth focused sx={{marginTop: 2}}
                               onChange={handleChangeFecha}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={evaluacion.open} onClose={handleCloseEvaluacion}>
                <DialogTitle>Evaluar</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth sx={{marginTop: 2}}>
                        <InputLabel id="demo-simple-select-label">Evaluación</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={evaluacion.evaluacion}
                            label="Evaluacion"
                            onChange={handleChangeEvaluacion}
                        >
                            <MenuItem value={"Bien"}>Bien</MenuItem>
                            <MenuItem value={"Regular"}>Regular</MenuItem>
                            <MenuItem value={"Mal"}>Mal</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseEvaluacion}>Cancel</Button>
                    <Button onClick={evaluar}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}