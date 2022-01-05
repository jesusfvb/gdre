import {useNavigate, useParams} from "react-router-dom";
import {
    DataGrid,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {MouseEvent, ReactElement, useEffect, useRef, useState} from "react";
import {Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, TextField} from "@mui/material";
import {Add, Delete, NavigateBefore, NavigateNext, Update} from "@mui/icons-material";
import axios from "axios";

export default function Apartamento() {
    const navegate = useNavigate()
    const params = useParams()
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
            minWidth: 130,
            renderCell: (params) => (
                <>
                    <IconButton color={"primary"} onClick={handleClickOpen(params.value)}>
                        <Update/>
                    </IconButton>
                    <IconButton color={"error"} onClick={borrar(params.value)}>
                        <Delete/>
                    </IconButton>
                    <IconButton color={"secondary"} onClick={(event) => {
                        event.stopPropagation()
                        navegate(`/ubicacion/residencias/${params.value}/apartamento`)
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

    const handleClickOpen = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        setOpen({open: true, params: rows.find(row => row.id === id)});
    };
    const handleClose = () => {
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
                })
        }
    }
    const borrar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .delete("/apartamento", {
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
                <IconButton color={"secondary"} onClick={()=>navegate("/ubicacion/residencias/")}>
                    <NavigateBefore/>
                </IconButton>
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

    useEffect(getData, [params.id])
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