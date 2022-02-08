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
    IconButton, InputLabel, MenuItem, Select, SelectChangeEvent,
    TextField
} from "@mui/material";
import {Add, Delete, Update} from "@mui/icons-material";
import axios from "axios";

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
            type: "date",
            flex: 1
        },
        {
            field: "rol",
            headerName: "Rol",
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
    const [value, setValue] = useState<{ nombre: string, usuario: string, solapin: string, rol: string }>({
        nombre: "",
        usuario: "",
        solapin: "",
        rol: ""
    })
    const [selected, setSelected] = useState<GridSelectionModel>([])

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        if (id !== undefined) {
            let user = rows.find(row => row.id === id)
            setValue({nombre: user.nombre, usuario: user.usuario, solapin: user.solapin, rol: user.rol})
        }
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValue({nombre: "", usuario: "", solapin: "", rol: ""})
        setOpen({open: false, id: undefined});
    };

    const handleChangeNombre = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({...value, nombre: evento.target.value});
    }
    const handleChangeUsuario = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({...value, usuario: evento.target.value});
    }
    const handleChangeSolapin = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({...value, solapin: evento.target.value});
    }
    const handleChangeRol = (evento: SelectChangeEvent) => {
        setValue({...value, rol: evento.target.value});
    }

    const save = () => {
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
                })
                .catch(error => console.error(error))
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
                })
                .catch((error) => console.error(error))
        }
    }
    const borrar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/usuario", {data: (id !== undefined) ? [id] : selected})
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
                               onChange={handleChangeNombre}/>
                    <TextField type={"text"} label="Usuario" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.usuario}
                               onChange={handleChangeUsuario}/>
                    <TextField type={"text"} label="Solapin" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.solapin}
                               onChange={handleChangeSolapin}/>
                    <FormControl fullWidth sx={{marginTop: 2}}>
                        <InputLabel id="demo-simple-select-label">Rol</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={value.rol}
                            label="Rol"
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
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}