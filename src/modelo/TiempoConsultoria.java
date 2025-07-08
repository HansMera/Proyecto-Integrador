package modelo;

import java.util.ArrayList;
import java.util.List;

public class TiempoConsultoria {
    private Trabajador trabajador;
    private Consultoria consultoria;
    private int horasAsignadas;
    private int horasRestantes;
    private int horasPagadas;
    private List<ConsultoriaPago> pagos;

    public TiempoConsultoria(Trabajador trabajador, Consultoria consultoria, int horasAsignadas) {
        this.trabajador = trabajador;
        this.consultoria = consultoria;
        this.horasAsignadas = horasAsignadas;
        this.horasRestantes = horasAsignadas;
        this.horasPagadas = 0;
        this.pagos = new ArrayList<>();
    }
    public void agregarPago(ConsultoriaPago pago) {
        pagos.add(pago);
    }

    public List<ConsultoriaPago> getPagos() {
        return pagos;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public Consultoria getConsultoria() {
        return consultoria;
    }

    public int getHorasAsignadas() {
        return horasAsignadas;
    }

    public int getHorasRestantes() {
        return horasRestantes;
    }

    public int getHorasPagadas() {
        return horasPagadas;
    }

    // Reduce las horas restantes y suma a las pagadas
    public void reducirHoras(int horas) {
        if (horas > 0 && horas <= horasRestantes) {
            horasRestantes -= horas;
            horasPagadas += horas;
        }
    }
}