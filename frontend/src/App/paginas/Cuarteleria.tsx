import {MouseEvent, ReactElement, SyntheticEvent, useEffect, useState, ChangeEvent, useContext} from "react";
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
import {DatosUser, IsRole} from "../App";
import {useSnackbar} from "notistack";

export default function Cuarteleria(): ReactElement {
    const {id} = useContext(DatosUser)
    const {isRolRender, isRolBoolean} = useContext(IsRole)
    const {enqueueSnackbar} = useSnackbar();
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
            type: "singleSelect",
            valueOptions: ["No Evaluado", "Bien", "Regular", "Mal"],
            flex: 1
        },
        {
            field: "id",
            headerName: "Acciones",
            type: "date",
            minWidth: 130,
            filterable: false,
            hide: !isRolBoolean(["Instructora", "Administrador", "Vicedecano"]),
            renderCell: (params) => (
                <>
                    <IconButton color="primary" onClick={handleClickOpenEvaluacion(params.value)}>
                        <Check/>
                    </IconButton>
                    <IconButton color="primary" onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color="error" onClick={handleClickOpenBorrar(params.value)}>
                        <Delete/>
                    </IconButton>
                </>
            )
        }]
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [value, setValue] = useState<{ usuario: any | null, fecha: string }>({usuario: null, fecha: ""})
    const [evaluacion, setEvaluacion] = useState<{ open: boolean, id: number | undefined, evaluacion: string }>({
        open: false,
        id: undefined,
        evaluacion: ""
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [validate, setValidate] = useState<{ estudiante: boolean, fecha: boolean }>({
        estudiante: true, fecha: true
    })

    const handleClickOpenBorrar = (id: number | undefined = undefined) => (event: MouseEvent) => {
        event.preventDefault();
        event.stopPropagation();
        setBorrar({open: true, id: id});
    };
    const handleCloseBorrar = () => {
        setBorrar({open: false, id: undefined});
    };

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (id !== undefined) {
            setValidate({estudiante: false, fecha: false})
            setValue({...value, fecha: rows.find(row => row.id === id).fecha})
        }
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValidate({estudiante: true, fecha: true})
        setValue({usuario: null, fecha: ""})
        setOpen({open: false, id: undefined});
    };

    const handleClickOpenEvaluacion = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setEvaluacion({open: true, id: id, evaluacion: rows.find(row => row.id === id).evaluacion});
    };
    const handleCloseEvaluacion = () => {
        setEvaluacion({open: false, id: undefined, evaluacion: ""});
    };

    const handleChangeFecha = (evento: ChangeEvent<HTMLInputElement>) => {
        setValidate({...validate, fecha: evento.target.value.length === 0})
        setValue({...value, fecha: evento.target.value});
    }
    const handleChangeEvaluacion = (evento: SelectChangeEvent) => {
        setEvaluacion({...evaluacion, evaluacion: evento.target.value as string})
    }

    const save = () => {
        if (!validate.fecha && !validate.estudiante) {
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
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    })
                    .catch(error => enqueueSnackbar("Error al realizar la Acción"))
            } else {
                axios
                    .post("/cuarteleria", {
                        idUsuario: value.usuario.id,
                        fecha: value.fecha
                    })
                    .then(response => {
                        setRows([...rows, response.data])
                        handleClose()
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    })
                    .catch((error) => enqueueSnackbar("Error al realizar la Acción"))
            }
        } else {
            enqueueSnackbar("Error al realizar la Acción")
        }
    }
    const borrar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/cuarteleria", {data: (borrarAlert.id !== undefined) ? [borrarAlert.id] : selected})
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
    const evaluar = () => {
        axios
            .put("/cuarteleria/evaluar", {id: evaluacion.id, evaluacion: evaluacion.evaluacion})
            .then(response => {
                let newRows = [...rows]
                newRows[rows.findIndex(row => row.id === response.data.id)] = response.data
                setRows(newRows)
                handleCloseEvaluacion()
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
        handleCloseEvaluacion()
    }

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChange = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate({...validate, estudiante: false})
            } else {
                setValidate({...validate, estudiante: true})
            }
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
                        error={validate.estudiante}
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
                {
                    isRolRender(["Instructora", "Administrador", "Vicedecano"],
                        <>
                            <IconButton color={"success"} onClick={handleClickOpen()}>
                                <Add/>
                            </IconButton>
                            <IconButton color={"error"} onClick={handleClickOpenBorrar()}
                                        disabled={selected.length === 0}>
                                <Delete/>
                            </IconButton>
                        </>
                    )
                }

            </GridToolbarContainer>
        )
    }

    useEffect(() => {
        axios
            .get((isRolBoolean("Estudiante") ? "/cuarteleria/" + id : "/cuarteleria"))
            .then((response) => setRows(response.data))
            .catch(error => console.error(error))
    }, [])
    return (
        <div style={{height: "calc(100vh - 60px)"}}>
            <DataGrid columns={columns} rows={rows} components={{Toolbar: MyToolbar}} autoPageSize
                      checkboxSelection={isRolBoolean(["Administrador", "Vicedecano"])}
                      disableSelectionOnClick={!isRolBoolean(["Administrador", "Vicedecano"])}
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Cuarteleria</DialogTitle>
                <DialogContent>
                    {(open.id === undefined) ? <MyAutocomplete/> : null}
                    <TextField type={"date"} label="Fecha" value={value.fecha} error={validate.fecha} variant="outlined"
                               fullWidth focused
                               sx={{marginTop: 2}}
                               onChange={handleChangeFecha}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={save}>Aceptar</Button>
                    <Button onClick={handleClose} color={"error"}>Cancel</Button>
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
                            <MenuItem value={"No Evaluado"}>No Evaluado</MenuItem>
                            <MenuItem value={"Bien"}>Bien</MenuItem>
                            <MenuItem value={"Regular"}>Regular</MenuItem>
                            <MenuItem value={"Mal"}>Mal</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={evaluar}>Aceptar</Button>
                    <Button onClick={handleCloseEvaluacion} color={"error"}>Cancel</Button>
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
                    <Button onClick={borrar}>Acepar</Button>
                    <Button onClick={handleCloseBorrar} color={"error"}> Cancelar </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}