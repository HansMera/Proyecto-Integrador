package modelo;

public class TiempoConsultoria {
    private Trabajador trabajador;
    private Consultoria consultoria;
    private int horasAsignadas;
    private int horasRestantes;
    private int horasPagadas;

    public TiempoConsultoria(Trabajador trabajador, Consultoria consultoria, int horasAsignadas) {
        this.trabajador = trabajador;
        this.consultoria = consultoria;
        this.horasAsignadas = horasAsignadas;
        this.horasRestantes = horasAsignadas;
        this.horasPagadas = 0;
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