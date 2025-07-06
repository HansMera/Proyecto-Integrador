package modelo;

import java.util.*;

public class Trabajador {
    private static int contadorID = 10010;
    private int codigoID;
    private String nombre;
    private String cargo; // "Administrador" o "Consultor"
    private String especializacion;
    private ArrayList<TiempoConsultoria> tiempoConsultorias;

    public Trabajador(String nombre, String cargo, String especializacion) {
        this.codigoID = contadorID++;
        this.nombre = nombre;
        this.cargo = cargo;
        this.especializacion = especializacion;
        this.tiempoConsultorias = new ArrayList<>();
    }

    public int getCodigoID() { return codigoID; }
    public String getNombre() { return nombre; }
    public String getCargo() { return cargo; }
    public String getEspecializacion() { return especializacion; }
    public ArrayList<TiempoConsultoria> getTiempoConsultorias() { return tiempoConsultorias; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setEspecializacion(String especializacion) { this.especializacion = especializacion; }

    public void asignarTiempoConsultoria(TiempoConsultoria tiempo) {
        tiempoConsultorias.add(tiempo);
    }

    public int getHorasTotalesAsignadas() {
        int total = 0;
        for (TiempoConsultoria t : tiempoConsultorias) {
            total += t.getHorasRestantes();
        }
        return total;
    }

    public boolean puedeAsignarse(int nuevasHoras) {
        return (getHorasTotalesAsignadas() + nuevasHoras) <= 160;
    }
}