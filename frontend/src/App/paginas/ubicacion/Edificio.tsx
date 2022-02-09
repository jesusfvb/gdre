import {ChangeEvent, MouseEvent, ReactElement, useContext, useEffect, useRef, useState} from "react";
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
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import {Add, Delete, NavigateNext, Update} from "@mui/icons-material";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {IsRole} from "../../App";

export default function Edificio(): ReactElement {
    const navegate = useNavigate()
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
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 130,
            renderCell: (params) => (
                <>
                    {isRolRender("Administrador",
                        <>
                            <IconButton color={"primary"} onClick={handleClickOpen(params.value)}>
                                <Update/>
                            </IconButton>
                            <IconButton color={"error"} onClick={borrar(params.value)}>
                                <Delete/>
                            </IconButton>
                        </>
                    )}
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
    const [validate, setValidate] = useState<boolean>(true)

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
            .get("/edificio")
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const save = () => {
        if (!validate) {
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
                <Typography variant={"subtitle1"} sx={{marginLeft: 1}}>Edificio</Typography>
                <Box sx={{flexGrow: 1}}/>
                {isRolRender("Administrador",
                    <>
                        <IconButton onClick={handleClickOpen()}>
                            <Add color={"success"}/>
                        </IconButton>
                        <IconButton onClick={borrar()} disabled={selected.length === 0} color={"error"}>
                            <Delete/>
                        </IconButton>
                    </>
                )}
            </GridToolbarContainer>
        )
    }

    useEffect(getData, [])
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
                        error={validate}
                        defaultValue={(open.params !== undefined) ? open.params.numero : null}
                        onChange={handleChange}
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