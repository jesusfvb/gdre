import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid'

export default function Calendario() {

    const eventos = [
        {
            id: "1",
            title: 'Cuarteleria',
            start: '2021-12-10',
            description: 'Evento de Cuarteleria'
        },
        {
            id: "2",
            title: 'Guardia Residencia',
            start: '2021-12-01',
            color: "red",
            description: 'Evento de Guardia en la Residencia'
        },
        {
            id: "3",
            title: 'Guardia Docente',
            start: '2021-12-11',
            color: "grey",
            description: 'Evento de Guardia en el Docente'
        }
    ]


    return (
        <div style={{width: 600, overflow: "hidden"}}>
            <FullCalendar
                locale="es"
                height={350}
                plugins={[dayGridPlugin]}
                headerToolbar={false}
                initialView="dayGridMonth"
                events={eventos}
            />
        </div>
    )
}