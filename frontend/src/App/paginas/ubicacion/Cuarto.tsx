import {ChangeEvent, MouseEvent, ReactElement, useContext, useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {
    DataGrid, esES,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField, Tooltip,
    Typography
} from "@mui/material";
import {Add, Delete, NavigateBefore, NavigateNext, Update} from "@mui/icons-material";
import axios from "axios";
import {IsRole} from "../../App";
import {useSnackbar} from "notistack";

export default function Cuarto(): ReactElement {
    const navegate = useNavigate()
    const params = useParams()
    const {enqueueSnackbar} = useSnackbar();
    const {isRolRender, isRolBoolean} = useContext(IsRole)
    const columns: GridColumns = [
        {
            field: "numero",
            flex: 1,
            type: "number",
            headerName: "Numero",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "capacidad",
            flex: 1,
            type: "number",
            headerName: "Capacidad",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 130,
            type: "actions",
            renderCell: (param) => (
                <>
                    {isRolRender(["Administrador", "Vicedecano"],
                        <>
                            <Tooltip title={"Modificar"}>
                                <IconButton color={"primary"} onClick={handleClickOpen(param.value)}>
                                    <Update/>
                                </IconButton>
                            </Tooltip>
                            <Tooltip title={"Borrar"}>
                                <IconButton color={"error"} onClick={handleClickOpenBorrar(param.value)}>
                                    <Delete/>
                                </IconButton>
                            </Tooltip>
                        </>
                    )}
                    <Tooltip title={"Ir"}>
                        <IconButton color={"secondary"} onClick={(event) => {
                            event.stopPropagation()
                            navegate(`/ubicacion/residencias/${params.idEdificio}/apartamento/${params.id}/cuarto/${param.value}/residente`)
                        }}>
                            <NavigateNext/>
                        </IconButton>
                    </Tooltip>
                </>
            )
        }]
    const containerInputs = useRef<HTMLDivElement>()
    const [rows, setRows] = useState<Array<any>>([])
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [open, setOpen] = useState<{ open: boolean, params?: any }>({open: false});
    const [validate, setValidate] = useState<{ numero: boolean, capacidad: boolean }>({numero: true, capacidad: true})
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});

    const handleClickOpenBorrar = (id: number | undefined = undefined) => (event: MouseEvent) => {
        event.stopPropagation();
        setBorrar({open: true, id: id});
    };
    const handleCloseBorrar = () => {
        setBorrar({open: false, id: undefined});
    };

    const handleChangeNumero = (even: ChangeEvent<HTMLInputElement>) => {
        const exp = new RegExp("^[0-9]+$")
        if (even.target.value.length === 0) {
            setValidate({...validate, numero: true})
        } else {
            setValidate({...validate, numero: !exp.test(even.target.value)})
        }
    }
    const handleChangeCapacidad = (even: ChangeEvent<HTMLInputElement>) => {
        const exp = new RegExp("^[0-9]+$")

        if (even.target.value.length === 0) {
            setValidate({...validate, capacidad: true})
        } else if (Number.parseInt(even.target.value) < 1) {
            even.target.value = "1"
            setValidate({...validate, capacidad: false})
        } else {
            setValidate({...validate, capacidad: !exp.test(even.target.value)})
        }
    }

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (id !== undefined) {
            setValidate({numero: false, capacidad: false})
        }
        setOpen({open: true, params: rows.find(row => row.id === id)});
    };
    const handleClose = () => {
        setValidate({numero: true, capacidad: true})
        setOpen({open: false});
    };

    const getData = () => {
        axios
            .get("/cuarto", {params: {id: params.id}})
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const save = () => {
        if (!validate.numero && !validate.capacidad) {
            if (open.params !== undefined) {
                axios
                    .put("/cuarto", {
                        id: open.params.id,
                        idApartamento: params.id,
                        numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value,
                        capacidad: containerInputs.current?.querySelector<HTMLInputElement>("#capacidad")?.value
                    })
                    .then(response => {
                        let newRows = [...rows]
                        newRows[rows.findIndex(row => row.id === open.params.id)] = response.data
                        setRows(newRows)
                        handleClose()
                        enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
                    })
                    .catch(error => enqueueSnackbar("Error al realizar la Acción"))
            } else {
                axios
                    .post("/cuarto", {
                        idApartamento: params.id,
                        numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value,
                        capacidad: containerInputs.current?.querySelector<HTMLInputElement>("#capacidad")?.value
                    })
                    .then(response => {
                        setRows([...rows, response.data])
                        handleClose()
                        enqueueSnackbar("Acción realizada con éxito", {variant: "success"})
                    })
                    .catch(error => enqueueSnackbar("Error al realizar la Acción"))
            }
        } else {
            enqueueSnackbar("Error al realizar la Acción")
        }
    }
    const borrar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/cuarto", {
                data: (borrarAlert.id === undefined) ? selected : [borrarAlert.id],
            })
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


    function MyToolbar(): ReactElement {
        return (
            <GridToolbarContainer>
                <Tooltip title={"Regresar"}>
                    <IconButton color={"secondary"}
                                onClick={() => navegate(`/ubicacion/residencias/${params.idEdificio}/apartamento`)}>
                        <NavigateBefore/>
                        <Typography variant={"subtitle1"}>Apartamento</Typography>
                    </IconButton>
                </Tooltip>
                <GridToolbarFilterButton/>
                <Typography variant={"subtitle1"} sx={{marginLeft: 1}}>Cuarto</Typography>
                <Box sx={{flexGrow: 1}}/>
                {isRolRender("Administrador",
                    <>
                        <Tooltip title={"Registrar"}>
                            <IconButton onClick={handleClickOpen()}>
                                <Add color={"success"}/>
                            </IconButton>
                        </Tooltip>
                        <Tooltip title={"Borrar"}>
                            <IconButton onClick={handleClickOpenBorrar()} disabled={selected.length === 0}
                                        color={"error"}>
                                <Delete/>
                            </IconButton>
                        </Tooltip>
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
                      components={{Toolbar: MyToolbar,}}
                      localeText={esES.components.MuiDataGrid.defaultProps.localeText}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Cuarto</DialogTitle>
                <DialogContent ref={containerInputs}>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="numero"
                        label="Numero"
                        type="number"
                        fullWidth
                        variant="standard"
                        defaultValue={(open.params !== undefined) ? open.params.numero : null}
                        onChange={handleChangeNumero}
                        error={validate.numero}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="capacidad"
                        label="Capacidad"
                        type="number"
                        fullWidth
                        variant="standard"
                        defaultValue={(open.params !== undefined) ? open.params.capacidad : null}
                        onChange={handleChangeCapacidad}
                        error={validate.capacidad}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={save}>Aceptar</Button>
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
        </>
    )
}