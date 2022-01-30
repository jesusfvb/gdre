import {ChangeEvent, MouseEvent, ReactElement, useEffect, useState} from "react";
import {
    DataGrid,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, TextField} from "@mui/material";
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
    const [value, setValue] = useState<{ nombre: string }>({nombre: ""})
    const [selected, setSelected] = useState<GridSelectionModel>([])

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setValue({nombre: rows.find(row => row.id === id).nombre})
        setOpen({open: true, id: id});
    };
    const handleClose = () => {
        setValue({nombre: ""})
        setOpen({open: false, id: undefined});
    };

    const handleChangeNombre = (evento: ChangeEvent<HTMLInputElement>) => {
        setValue({nombre: evento.target.value});
    }

    const save = () => {
        if (open.id !== undefined) {
            axios
                .put("/usuario", {
                    id: open.id,
                    nombre: value.nombre
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
                <DialogContent>
                    <TextField type={"text"} label="Nombre" variant="outlined" fullWidth sx={{marginTop: 2}}
                               value={value.nombre}
                               onChange={handleChangeNombre}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}