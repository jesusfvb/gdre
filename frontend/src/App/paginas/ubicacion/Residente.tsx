import {MouseEvent, ReactElement, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {
    DataGrid,
    GridColumns,
    GridSelectionModel,
    GridToolbarContainer,
    GridToolbarFilterButton
} from "@mui/x-data-grid";
import {
    Autocomplete,
    Box,
    Button,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import {Add, Delete, NavigateBefore} from "@mui/icons-material";
import axios from "axios";

export default function Residente(): ReactElement {
    const navegate = useNavigate()
    const params = useParams()
    const columns: GridColumns = [
        {
            field: "nombre",
            flex: 1,
            type: "string",
            headerName: "Nombre",
            headerAlign: "left",
            align: "left"
        },
        {
            field: "id",
            filterable: false,
            headerName: "Acción",
            minWidth: 100,
            renderCell: (param) => (
                <IconButton color={"error"} onClick={desubicar(param.value)}>
                    <Delete/>
                </IconButton>
            )
        }]
    const [value, setValue] = useState<any>()
    const [rows, setRows] = useState<Array<any>>([])
    const [selected, setSelected] = useState<GridSelectionModel>([])
    const [open, setOpen] = useState<boolean>(false);

    const handleClickOpen = (evento: MouseEvent) => {
        evento.stopPropagation()
        setOpen(true);
    };
    const handleClose = () => {
        setValue(null)
        setOpen(false);
    };

    const getData = () => {
        axios
            .get("/usuario/cuarto", {params: {idCuarto: params.id}})
            .then(response => setRows(response.data))
            .catch(error => console.error(error))
    }
    const ubicar = () => {
        axios
            .post("/usuario/ubicar", {
                idCuarto: params.id,
                idUsuario: value?.id
            })
            .then(response => {
                setRows([...rows, response.data])
                handleClose()
            })
    }
    const desubicar = (id: number | undefined = undefined) => (evento: MouseEvent) => {
        evento.stopPropagation()
        axios
            .put("/usuario/desubicar", (id === undefined) ? selected : [id])
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

    function MyAutocomplete(): ReactElement {
        const [open, setOpen] = useState(false);
        const [options, setOptions] = useState([]);
        const loading = open && options.length === 0;

        useEffect(() => {
            if (loading) {
                axios
                    .get("/usuario/no_ubicados")
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
                value={value}
                onChange={(event, newValue) => setValue(newValue)}
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
                <IconButton color={"secondary"}
                            onClick={() => navegate(`/ubicacion/residencias/${params.idEdificio}/apartamento/${params.idApartamento}/cuarto`)}>
                    <NavigateBefore/>
                    <Typography variant={"subtitle1"}>Cuarto</Typography>
                </IconButton>
                <GridToolbarFilterButton/>
                <Typography variant={"subtitle1"} sx={{marginLeft: 1}}>Residentes</Typography>
                <Box sx={{flexGrow: 1}}/>
                <IconButton onClick={handleClickOpen}>
                    <Add color={"success"}/>
                </IconButton>
                <IconButton onClick={desubicar()} disabled={selected.length === 0} color={"error"}>
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
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Ubicar</DialogTitle>
                <DialogContent>
                    <MyAutocomplete/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={ubicar}>Aceptar</Button>
                </DialogActions>
            </Dialog>
        </>
    )
}