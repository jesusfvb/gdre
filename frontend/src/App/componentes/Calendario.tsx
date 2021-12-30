import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid'

export default function Calendario(props: { width: number | string, height: number | string } | { height: number | string } | any) {
    const eventos = [
        {
            id: "1",
            title: 'Evento1',
            start: '2021-12-10',
            description: 'Evento de Cuarteleria'
        },
        {
            id: "2",
            title: 'Evento2',
            start: '2021-12-01',
            color: "red",
            description: 'Evento de Guardia en la Residencia'
        },
        {
            id: "3",
            title: 'Evento3',
            start: '2021-12-11',
            color: "grey",
            description: 'Evento de Guardia en el Docente'
        }
    ]
    return (
        <div style={{width: (props.width === undefined) ? "100%" : props?.width}}>
            <FullCalendar
                locale="es"
                height={props.height}
                plugins={[dayGridPlugin]}
                headerToolbar={false}
                initialView="dayGridMonth"
                events={eventos}
            />
        </div>
    )
}