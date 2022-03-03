import {ChangeEvent, MouseEvent, ReactElement, useEffect, useState} from "react";
import {
    DataGrid,
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
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField
} from "@mui/material";
import {Add, Delete, Update} from "@mui/icons-material";
import axios from "axios";
import {useSnackbar} from "notistack";

export default function Usuario(): ReactElement {
    const columns: GridColumns = [
        {
            field: "nombre",
            headerName: "Nombre",
            type: "string",
            flex: 1
        },
        {
            field: "solapin",
            headerName: "Solapin",
            type: "string",
            flex: 1
        },
        {
            field: "usuario",
            headerName: "Usuario",
            type: "string",
            flex: 1
        },
        {
            field: "rol",
            headerName: "Rol",
            type: "singleSelect",
            valueOptions: ["Usuario", "Estudiante", "Profesor", "Instructora", "Vicedecano", "Administrador"],
            flex: 1
        },
        {
            field: "id",
            headerName: "Acciones",
            type: "actions",
            minWidth: 130,
            filterable: false,
            renderCell: (params) => (
                <>
                    <IconButton color="primary" onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color="error" onClick={handleClickOpenBorrar(params.value)}>
                        <Delete/>
                    </IconButton>
                </>
            )
        }]
    const {enqueueSnackbar} = useSnackbar();
    const [rows, setRows] = useState<Array<any>>([])
    const [open, setOpen] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [value, setValue] = useState<{ nombre: string, usuario: string, solapin: string, rol: string }>({
        nombre: "",
        usuario: "",
        solapin: "",
        rol: ""
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [borrarAlert, setBorrar] = useState<{ open: boolean, id: number | undefined }>({open: false, id: undefined});
    const [validate, setValidate] = useState<{ nombre: boolean, usuario: boolean, solapin: boolean, rol: boolean }>({
        nombre: true, usuario: true, solapin: true, rol: true
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
            let user = rows.find(row => row.id === id)
            setValidate({
                nombre: false, usuario: false, solapin: false, rol: false
            })
            setValue({nombre: user.nombre, usuario: user.usuario, solapin: user.solapin, rol: user.rol})
        }
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValidate({
            nombre: true, usuario: true, solapin: true, rol: true
        })
        setValue({nombre: "", usuario: "", solapin: "", rol: ""})
        setOpen({open: false, id: undefined});
    };

    const handleChangeNombre = (evento: ChangeEvent<HTMLInputElement>) => {
        setValidate({
            ...validate,
            nombre: evento.target.value.match("^[A-Za-zƒŠŒŽšœžŸÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèé êëìíîïðñòóôõöøùúûüýþÿ]*$") === null
        })
        setValue({...value, nombre: evento.target.value});
    }
    const handleChangeUsuario = (evento: ChangeEvent<HTMLInputElement>) => {
        setValidate({...validate, usuario: evento.target.value.match("^[a-z][a-zA-Z0-9_]{3,10}$") === null})
        setValue({...value, usuario: evento.target.value});
    }
    const handleChangeSolapin = (evento: ChangeEvent<HTMLInputElement>) => {
        setValidate({...validate, solapin: evento.target.value.match("^[E|T][0-9]{6}$") === null})
        setValue({...value, solapin: evento.target.value});
    }
    const handleChangeRol = (evento: SelectChangeEvent) => {
        setValidate({...validate, rol: evento.target.value.length === 0})
        setValue({...value, rol: evento.target.value});
    }

    const save = () => {
        if (!(validate.nombre || validate.rol || validate.usuario || validate.solapin))
            if (open.id !== undefined) {
                axios
                    .put("/usuario", {
                        id: open.id,
                        nombre: value.nombre,
                        username: value.usuario,
                        solapin: value.solapin,
                        rol: value.rol
                    })
                    .then(response => {
                        let newRows = [...rows]
                        newRows[rows.findIndex(row => row.id === open.id)] = response.data
                        setRows(newRows)
                        handleClose()
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    })
                    .catch((error) => {
                        enqueueSnackbar("Error al realizar la Acción")
                        console.error(error)
                    })
            } else {
                axios
                    .post("/usuario", {
                        nombre: value.nombre,
                        username: value.usuario,
                        solapin: value.solapin,
                        rol: value.rol
                    })
                    .then(response => {
                        setRows([...rows, response.data])
                        handleClose()
                        enqueueSnackbar("Acción realizada con exito", {variant: "success"})
                    })
                    .catch((error) => {
                        enqueueSnackbar("Error al realizar la Acción")
                        console.error(error)
                    })
            } else {
            enqueueSnackbar("Error al realizar la Acción")
        }
    }
    const borrar = () => {
        axios
            .delete("/usuario", {data: (borrarAlert.id !== undefined) ? [borrarAlert.id] : selected})
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
            .catch((error) => {
                enqueueSnackbar("Error al realizar la Acción")
                console.error(error)
            })
    }

    function MyToolbar(): ReactElement {
        return (
            <GridToolbarContainer>
                <GridToolbarFilterButton/>
                <Box sx={{flexGrow: 1}}/>
                <IconButton color={"success"} onClick={handleClickOpen()}>
                    <Add/>
                </IconButton>
                <IconButton color={"error"} onClick={handleClickOpenBorrar()} disabled={selected.length === 0}>
                    <Delete/>
                </IconButton>
            </GridToolbarContainer>
        )
    }

    useEffect(() => {
        axios
            .get("/usuario")
            .then((response) => setRows(response.data))
            .catch(error => console.error(error))
    }, [])
    return (
        <div style={{height: "calc(100vh - 60px)"}}>
            <DataGrid columns={columns} rows={rows} components={{Toolbar: MyToolbar}} autoPageSize checkboxSelection
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Usuario</DialogTitle>
                <DialogContent sx={{width: 300}}>
                    <TextField type={"text"} label="Nombre" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.nombre}
                               error={validate.nombre}
                               onChange={handleChangeNombre}/>
                    <TextField type={"text"} label="Usuario" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.usuario}
                               error={validate.usuario}
                               onChange={handleChangeUsuario}/>
                    <TextField type={"text"} label="Solapin" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.solapin}
                               error={validate.solapin}
                               onChange={handleChangeSolapin}/>
                    <FormControl fullWidth sx={{marginTop: 2}}>
                        <InputLabel id="demo-simple-select-label">Rol</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={value.rol}
                            label="Rol"
                            error={validate.rol}
                            onChange={handleChangeRol}
                        >
                            <MenuItem value={"Estudiante"}>Estudiante</MenuItem>
                            <MenuItem value={"Profesor"}>Profesor</MenuItem>
                            <MenuItem value={"Instructora"}>Instructora</MenuItem>
                            <MenuItem value={"Vicedecano"}>Vicedecano</MenuItem>
                        </Select>
                    </FormControl>
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
                    <Button onClick={borrar}>Acepar</Button>
                    <Button onClick={handleCloseBorrar} color={"error"}> Cancelar </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}