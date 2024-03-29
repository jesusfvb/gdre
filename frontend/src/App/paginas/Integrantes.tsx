import {ChangeEvent, MouseEvent, ReactElement, SyntheticEvent, useContext, useEffect, useState} from "react";
import {
    DataGrid, esES,
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
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField, Tooltip,
    Typography
} from "@mui/material";
import {Add, AddTask, Delete, HowToReg, NavigateBefore, Warning} from "@mui/icons-material";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {IsRole} from "../App";
import {useSnackbar} from "notistack";

export default function Integrantes(): ReactElement {
    const navegate = useNavigate()
    const params = useParams()
    const {enqueueSnackbar} = useSnackbar();
    const {isRolRender, isRolBoolean} = useContext(IsRole)
    const columns: GridColumns = [
        {
            field: "participante",
            headerName: "Nombre",
            type: "string",
            flex: 1,
            renderCell: params => (params.value?.nombre)
        },
        {
            field: "asistencia",
            headerName: "Asistencia",
            type: "singleSelect",
            valueOptions: ["Presente", "Ausente", "Pendiente"],
            flex: 1
        },
        {
            field: "evaluacion",
            headerName: "Evaluación",
            type: "singleSelect",
            valueOptions: ["Bien", "Regular", "Mal", "Pendiente"],
            flex: 1
        },
        {
            field: "advertencia",
            headerName: "Advertencia",
            type: "actions",
            filterable: false,
            flex: 1,
            renderCell: (params) => {
                let disable = false;
                if (params.value === null) {
                    disable = true
                } else if (params.value.trim().length === 0) {
                    disable = true
                }
                return (
                    <>
                        <Button disabled={disable}
                                onClick={handleClickOpenAdvertencia(undefined, params.value)} size={"small"}
                                variant={"contained"}>
                            Ver
                        </Button>
                    </>
                )
            }
        },
        {
            field: "id",
            headerName: "Acciones",
            type: "actions",
            minWidth: 170,
            filterable: false,
            hide: !isRolBoolean(["Profesor", "Instructora", "Administrador", "Vicedecano"]),
            renderCell: (params) => (
                <>
                    <Tooltip title={"Asistencia"}>
                        <IconButton onClick={handleClickOpenAE(params.value, 1)}>
                            <HowToReg/>
                        </IconButton>
                    </Tooltip>
                    <Tooltip title={"Evaluación"}>
                        <IconButton onClick={handleClickOpenAE(params.value, 2)}>
                            <AddTask/>
                        </IconButton>
                    </Tooltip>
                    <Tooltip title={"Advertencia"}>
                        <IconButton onClick={handleClickOpenAdvertencia(params.value, 1)}>
                            <Warning/>
                        </IconButton>
                    </Tooltip>
                    {
                        isRolRender("Administrador",
                            <Tooltip title={"Borrar"}>
                                <IconButton color="error" onClick={handleClickOpenBorrar(params.value)}>
                                    <Delete/>
                                </IconButton>
                            </Tooltip>
                        )
                    }
                </>
            )
        }
    ]
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [openAE, setOpenAE] = useState<{ open: boolean, id: number | undefined, option: number | undefined }>({
        open: false,
        id: undefined,
        option: undefined
    });
    const [value, setValue] = useState<{ participante: any | null }>({
        participante: null
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [option, setOption] = useState('');
    const [advertencia, setAdvertencia] = useState<{ open: boolean, id: number | undefined, advertencia: string }>({
        open: false,
        id: undefined,
        advertencia: ""
    })
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [validate, setValidate] = useState<{ participante: boolean }>({
        participante: true
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
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValue({participante: null})
        setValidate({participante: true})
        setOpen({open: false, id: undefined});
    };

    const handleChangeOption = (event: SelectChangeEvent) => {
        setOption(event.target.value);
    };

    const handleClickOpenAE = (id: number | undefined = undefined, option: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        let integrante = rows.find(row => row.id === id)
        if (openAE.option === 1) {
            setOption(integrante.asistencia)
        } else {
            setOption(integrante.evaluacion)
        }
        setOpenAE({open: true, id: id, option: option});
    };
    const handleCloseAE = () => {
        setOpenAE({open: false, id: undefined, option: undefined});
    };

    const handleClickOpenAdvertencia = (id: number | undefined = undefined, option: number | string) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (typeof option === "number") {
            let integrante = rows.find(row => row.id === id)
            setAdvertencia({
                open: true,
                id: id,
                advertencia: (integrante.advertencia === null) ? "" : integrante.advertencia
            });
        } else {
            setAdvertencia({
                open: true,
                id: id,
                advertencia: option
            })
        }
    };
    const handleCloseAdvertencia = () => {
        setAdvertencia({open: false, id: undefined, advertencia: ""});
    };
    const handleChangeAdvertencia = (event: ChangeEvent<HTMLTextAreaElement>) => {
        event.preventDefault()
        if (advertencia.id !== undefined) {
            setAdvertencia({...advertencia, advertencia: event.target.value})
        }
    }

    const save = () => {
        if (!validate.participante) {
            axios
                .post("/integrante", {
                    idParticipante: value.participante.id,
                    idGuardia: params.id
                })
                .then(response => {
                    setRows([...rows, response.data])
                    handleClose()
                    enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
                })
                .catch((error) => enqueueSnackbar("Error al realizar la Acción"))
        } else {
            enqueueSnackbar("Error al realizar la Acción")
        }
    }
    const borrar = () => {
        axios
            .delete("/integrante", {data: (borrarAlert.id !== undefined) ? [borrarAlert.id] : selected})
            .then(response => {
                let newRows: any = []
                rows.forEach((value) => {
                    if (!response.data.some((value1: number) => value1 === value.id)) {
                        newRows.push(value)
                    }
                })
                setRows(newRows)
                handleCloseBorrar()
                enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }
    const asistenciaEvaluacion = () => {
        axios.put((openAE.option === 1) ? "/integrante/asistencia" : "/integrante/evaluacion",
            (openAE.option === 1) ? {
                id: openAE.id,
                asistencia: option
            } : {
                id: openAE.id,
                evaluacion: option
            })
            .then(response => {
                let newRows = [...rows]
                newRows[rows.findIndex(row => row.id === openAE.id)] = response.data
                setRows(newRows)
                handleCloseAE()
                enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"));
    }
    const advertir = () => {
        axios.put("/integrante/advertencia", {
            id: advertencia.id,
            advertencia: advertencia.advertencia
        })
            .then(response => {
                let newRows = [...rows]
                newRows[rows.findIndex(row => row.id === response.data.id)] = response.data
                setRows(newRows)
                handleCloseAdvertencia()
                enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
            }).catch(error => {
            enqueueSnackbar("Error al realizar la Acción")
        })
    }

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        const handleChange = (event: SyntheticEvent, newValue: AutocompleteValue<any, any, any, any>) => {
            if (newValue !== null) {
                setValidate({...validate, participante: false})
            } else {
                setValidate({...validate, participante: true})
            }
            setValue({participante: newValue})
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
                value={value.participante}
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
                        label="Estudiante"
                        error={validate.participante}
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
                <Tooltip title={"Regresar"}>
                    <IconButton color={"secondary"}
                                onClick={() => navegate(`/guardia`)}>
                        <NavigateBefore/>
                        <Typography variant={"subtitle1"}>Guardia</Typography>
                    </IconButton>
                </Tooltip>
                <GridToolbarFilterButton/>
                <Box sx={{flexGrow: 1}}/>
                {
                    isRolRender(["Vicedecano", "Administrador"],
                        <>
                            <Tooltip title={"Registrar"}>
                                <IconButton color={"success"} onClick={handleClickOpen()}>
                                    <Add/>
                                </IconButton>
                            </Tooltip>
                            <Tooltip title={"Borrar"}>
                                <IconButton color={"error"} onClick={handleClickOpenBorrar()}
                                            disabled={selected.length === 0}>
                                    <Delete/>
                                </IconButton>
                            </Tooltip>
                        </>)
                }

            </GridToolbarContainer>
        )
    }

    useEffect(() => {
        axios
            .get("/integrante/guardia/" + params.id)
            .then((response) => {
                setRows(response.data)
            })
            .catch(error => console.error(error))
    }, [])
    return (
        <div style={{height: "calc(100vh - 60px)"}}>
            <DataGrid columns={columns} rows={rows} components={{Toolbar: MyToolbar}} autoPageSize
                      checkboxSelection={isRolBoolean(["Administrador", "Vicedecano"])}
                      disableSelectionOnClick={!isRolBoolean(["Administrador", "Vicedecano"])}
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}
                      localeText={esES.components.MuiDataGrid.defaultProps.localeText}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Integrantes</DialogTitle>
                <DialogContent>
                    <MyAutocomplete/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={save}>Aceptar</Button>
                    <Button onClick={handleClose} color={"error"}>Cancel</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={openAE.open} onClose={handleCloseAE}>
                <DialogTitle>{(openAE.option === 1) ? "Asistencia" : "Evaluación"}</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth sx={{marginTop: 1}}>
                        <InputLabel
                            id="demo-simple-select-label">{openAE.option === 1 ? "Asistencia" : "Evaluación"}</InputLabel>
                        {
                            openAE.option === undefined ? null : openAE.option === 1 ?
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={option}
                                    label="Asistencia"
                                    onChange={handleChangeOption}
                                >
                                    <MenuItem value={"Pendiente"}>Pendiente</MenuItem>
                                    <MenuItem value={"Presente"}>Presente</MenuItem>
                                    <MenuItem value={"Ausente"}>Ausente</MenuItem>
                                </Select>
                                :
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={option}
                                    label="Evaluación"
                                    onChange={handleChangeOption}
                                >
                                    <MenuItem value={"Pendiente"}>Pendiente</MenuItem>
                                    <MenuItem value={"Bien"}>Bien</MenuItem>
                                    <MenuItem value={"Regular"}>Regular</MenuItem>
                                    <MenuItem value={"Mal"}>Mal</MenuItem>
                                </Select>
                        }
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={asistenciaEvaluacion}>Aceptar</Button>
                    <Button onClick={handleCloseAE} color={"error"}>Cancel</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={advertencia.open} onClose={handleCloseAdvertencia}>
                <DialogTitle>Advertencia</DialogTitle>
                <DialogContent>
                    <TextField
                        sx={{marginTop: 1}}
                        label="Advertencia"
                        multiline
                        rows={4}
                        value={advertencia.advertencia}
                        onChange={handleChangeAdvertencia}
                    />
                </DialogContent>
                <DialogActions>
                    {(advertencia.id !== undefined) ? <Button onClick={advertir}>Aceptar</Button> : null}
                    <Button
                        color={"error"}
                        onClick={handleCloseAdvertencia}>{(advertencia.id !== undefined) ? "Cancelar" : "Salir"}
                    </Button>
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
                    <Button onClick={borrar}>Aceptar</Button>
                    <Button onClick={handleCloseBorrar} color={"error"}> Cancelar </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}