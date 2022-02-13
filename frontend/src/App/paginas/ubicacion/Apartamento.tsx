import {useNavigate, useParams} from "react-router-dom";
import {
    DataGrid,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {ChangeEvent, MouseEvent, ReactElement, useContext, useEffect, useRef, useState} from "react";
import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import {Add, Delete, NavigateBefore, NavigateNext, Update} from "@mui/icons-material";
import axios from "axios";
import {IsRole} from "../../App";
import {useSnackbar} from "notistack";

export default function Apartamento() {
    const navegate = useNavigate()
    const params = useParams()
    const {isRolRender, isRolBoolean} = useContext(IsRole)
    const {enqueueSnackbar} = useSnackbar();
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
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 150,
            renderCell: (param) => (
                <>
                    {isRolRender("Administrador",
                        <>
                            <IconButton key={1} color={"primary"} onClick={handleClickOpen(param.value)}>
                                <Update/>
                            </IconButton>

                            <IconButton key={2} color={"error"} onClick={handleClickOpenBorrar(param.value)}>
                                <Delete/>
                            </IconButton>
                        </>
                    )}
                    <IconButton color={"secondary"} onClick={(event) => {
                        event.stopPropagation()
                        navegate(`/ubicacion/residencias/${params.id}/apartamento/${param.value}/cuarto`)
                    }}>
                        <NavigateNext/>
                    </IconButton>
                </>
            )
        }]
    const containerInputs = useRef<HTMLDivElement>()
    const [rows, setRows] = useState<Array<any>>([])
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [open, setOpen] = useState<{ open: boolean, params?: any }>({open: false});
    const [validate, setValidate] = useState<boolean>(true)
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});

    const handleClickOpenBorrar = (id: number | undefined = undefined) => (event: MouseEvent) => {
        event.stopPropagation();
        setBorrar({open: true, id: id});
    };
    const handleCloseBorrar = () => {
        setBorrar({open: false, id: undefined});
    };

    const handleChange = (even: ChangeEvent<HTMLInputElement>) => {
        const exp = new RegExp("^[0-9]+$")
        if (even.target.value.length === 0) {
            setValidate(true)
        } else {
            setValidate(!exp.test(even.target.value))
        }
    }

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (id !== undefined) {
            setValidate(false)
        }
        setOpen({open: true, params: rows.find(row => row.id === id)});
    };
    const handleClose = () => {
        setValidate(true)
        setOpen({open: false});
    };

    const getData = () => {
        axios
            .get("/apartamento", {
                params: {id: params.id}
            })
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const save = () => {
        if (!validate) {
            if (open.params !== undefined) {
                axios
                    .put("/apartamento", {
                        id: open.params.id,
                        idEdificio: params.id,
                        numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value
                    })
                    .then(response => {
                        let newRows = [...rows]
                        newRows[rows.findIndex(row => row.id === open.params.id)] = response.data
                        setRows(newRows)
                        handleClose()
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    }).catch(error => {
                    enqueueSnackbar("Error al realizar la Acción")
                })
            } else {
                axios
                    .post("/apartamento", {
                        idEdificio: params.id,
                        numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value
                    })
                    .then(response => {
                        setRows([...rows, response.data])
                        handleClose()
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    }).catch(error => {
                    enqueueSnackbar("Error al realizar la Acción")
                }).catch(error => enqueueSnackbar("Error al realizar la Acción"))
            }
        }
    }
    const borrar = (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/apartamento", {
                data: (borrarAlert.id === undefined) ? selected : [borrarAlert.id]
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
                enqueueSnackbar("Acción realizada con exito", {variant: "success"})
            })
            .catch(error => enqueueSnackbar("Error al realizar la Acción"))
    }

    function MyToolbar(): ReactElement {
        return (
            <GridToolbarContainer>
                <IconButton color={"secondary"} onClick={() => navegate("/ubicacion/residencias/")}>
                    <NavigateBefore/>
                    <Typography variant={"subtitle1"}>Edificio</Typography>
                </IconButton>
                <GridToolbarFilterButton/>
                <Typography variant={"subtitle1"} sx={{marginLeft: 1}}>Apartamento</Typography>
                <Box sx={{flexGrow: 1}}/>
                {isRolRender("Administrador",
                    <div>
                        <IconButton onClick={handleClickOpen()}>
                            <Add color={"success"}/>
                        </IconButton>
                        <IconButton disabled={selected.length === 0} color={"error"} onClick={handleClickOpenBorrar()}>
                            <Delete/>
                        </IconButton>
                    </div>
                )}
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [params.id])
    return (
        <>
            <DataGrid autoPageSize={true} density={"compact"} columns={columns} rows={rows}
                      checkboxSelection={isRolBoolean("Administrador")}
                      disableSelectionOnClick={!isRolBoolean("Administrador")}
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}
                      components={{
                          Toolbar: MyToolbar,
                      }}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Nuevo Apartamento</DialogTitle>
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
                        onChange={handleChange}
                        error={validate}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
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
        </>
    )
}