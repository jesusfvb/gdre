import {MouseEvent, ReactElement, useEffect, useRef, useState} from "react";
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

export default function Edificio(): ReactElement {
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
            minWidth: 100,
            renderCell: (params) => (
                <>
                    <IconButton color={"primary"} onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color={"error"} onClick={borrar(params.value)}>
                        <Delete/>
                    </IconButton>
                </>
            )
        }]
    const containerInputs = useRef<HTMLDivElement>()
    const [rows, setRows] = useState<Array<any>>([])
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [open, setOpen] = useState<{ open: boolean, params?: any }>({open: false});

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setOpen({open: true, params: rows.find(row => row.id === id)});
    };
    const handleClose = () => {
        setOpen({open: false});
    };

    const getData = () => {
        axios
            .get("/edificio")
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const save = () => {
        if (open.params !== undefined) {
            axios
                .put("/edificio", {
                    id: open.params.id,
                    numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value
                })
                .then(response => {
                    let newRows = [...rows]
                    newRows[rows.findIndex(row => row.id === open.params.id)] = response.data
                    setRows(newRows)
                    handleClose()
                })
        } else {
            axios
                .post("/edificio", {
                    numero: containerInputs.current?.querySelector<HTMLInputElement>("#numero")?.value
                })
                .then(response => {
                    setRows([...rows, response.data])
                    handleClose()
                })
        }
    }
    const borrar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/edificio", {
                data: (id === undefined) ? selected : [id]
            })
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
                <IconButton onClick={handleClickOpen()}>
                    <Add color={"success"}/>
                </IconButton>
                <IconButton onClick={borrar()} disabled={selected.length === 0} color={"error"}>
                    <Delete/>
                </IconButton>
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [])
    return (
        <>
            <DataGrid autoPageSize={true} density={"compact"} columns={columns} rows={rows} checkboxSelection
                      onSelectionModelChange={(selectionModel) => setSelected(selectionModel)}
                      components={{
                          Toolbar: MyToolbar,
                      }}/>
            <Dialog open={open.open} onClose={handleClose}>
                <DialogTitle>Nuevo Edificio</DialogTitle>
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
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={save}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </>
    )
}