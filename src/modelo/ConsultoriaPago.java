package modelo;

import java.util.Date;

public class ConsultoriaPago {
    private Date fecha;
    private Consultoria consultoria;
    private int horasPagadas;
    private Trabajador trabajadorPagado;

    public ConsultoriaPago(Date fecha, Consultoria consultoria, int horasPagadas, Trabajador trabajadorPagado) {
        this.fecha = fecha;
        this.consultoria = consultoria;
        this.horasPagadas = horasPagadas;
        this.trabajadorPagado = trabajadorPagado;
    }

    public Date getFecha() {
        return fecha;
    }

    public Consultoria getConsultoria() {
        return consultoria;
    }

    public int getHorasPagadas() {
        return horasPagadas;
    }

    public Trabajador getTrabajadorPagado() {
        return trabajadorPagado;
    }
}